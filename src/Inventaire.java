import java.io.*;
import java.util.*;

public class Inventaire {
    private static Scanner scanner = new Scanner(System.in);
    private String succursale;
    private Article[] articles;
    private int numFacture;

    public Inventaire(String succursale, String nomFichier, int numFacture) {
        this.succursale = succursale;
        this.numFacture = numFacture;
        this.articles = chargerInventaire(nomFichier);
    }

    public void ajouterArticle() {
        int id;
        boolean check = false;

        if (articles.length == 0) {
            id = Validations.validerEntierPositif("Entrez l'ID du premier article : ");
        } else {
            int dernierId = articles[articles.length - 1].getId();
            System.out.print("Entrez l'ID : ");
            id = scanner.nextInt();
            check = idExiste(id);
            scanner.nextLine();
            if (!check && id != dernierId + 1) {
                id = dernierId + 1;
                System.out.println(Message.Id.SEQUENTIEL + id);
            }
        }

        if (check) {
            Article articleExistant = articleExiste(id);
            if (articleExistant != null) {
                System.out.println("l'ID "+ articleExistant.getId() + " correspond à l'article '" + articleExistant.getDescription()+"'");
                int quantiteAdditionnelle = Validations.validerEntierPositif("Entrez la quantité supplémentaire : ");
                articleExistant.setQuantite(articleExistant.getQuantite() + quantiteAdditionnelle);
                System.out.println(Message.Quantite.MAJ);
            }
        } else {
            Article nouvelArticle = saisirInformationsArticle(id);
            articles = ajoutTableauTemp(articles, nouvelArticle);
            System.out.println(Message.Article.AJOUTE_SUCCES);
        }
    }

    public void afficherArticle() {
        int id = Validations.validerEntierPositif("Entrez l'ID de l'article à afficher : ");
        Article article = articleExiste(id);

        if (article != null) {
            System.out.println("\nInformations de l'article :");
            System.out.println("ID : " + article.getId());
            System.out.println("Catégorie : " + article.getCategorie());
            System.out.println("Description : " + article.getDescription());
            System.out.println("Quantité : " + article.getQuantite());
            System.out.println("Prix : " + article.getPrix() + " $");
        } else {
            System.out.println(Message.Article.NON_TROUVE);
        }
    }

    public void afficherTousArticles() {
        if (articles.length == 0) {
            System.out.println(Message.Article.VIDE);
        } else {
            boolean risqueTrouve = false;
            System.out.println("\nListe de tous les articles :");
            for (Article article : articles) {
                System.out.print(article.toString());
                if (article.getQuantite() <= 5) {
                    System.out.print("      ⚠");
                    risqueTrouve = true;
                }
                System.out.println();
            }
            if (risqueTrouve) {
                System.out.println(Message.Article.RISQUE);
            }
        }
    }

    public void supprimerArticle() {
        if (articles.length == 0) {
            System.out.println(Message.Article.VIDE);
        }else {
            int id = Validations.validerEntierPositif("Entrez l'ID de l'article à supprimer : ");
            Article article = articleExiste(id);

            if (article != null) {
                System.out.println("\nArticle trouvé :");
                System.out.println("Catégorie : " + article.getCategorie());
                System.out.println("Description : " + article.getDescription());

                System.out.print("Voulez-vous vraiment supprimer cet article ? (o/O pour confirmer) : ");
                String confirmation = scanner.nextLine().toLowerCase();

                if (confirmation.equals("o")) {
                    supprimerArticleDuTableau(id);
                    System.out.println(Message.Article.SUPPRIME_SUCCES);
                } else {
                    System.out.println("Suppression annulée.");
                }
            } else {
                System.out.println(Message.Article.NON_TROUVE);
            }
        }
    }

    public void afficherArticlesARisque() {
        if (articles.length == 0) {
            System.out.println(Message.Article.VIDE);
        } else {
            System.out.println("\nArticles à risque (quantité <= 5) :");
            boolean risqueTrouve = articlesARisque();
            if (!risqueTrouve) {
                System.out.println("Aucun article à risque.");
            }
        }
    }

    public void modifierArticle() {
        int id = Validations.validerEntierPositif("Entrez l'ID de l'article à modifier : ");
        Article article = articleExiste(id);
        if (article != null) {
            System.out.println("\nArticle trouvé :");
            System.out.println("ID : " + article.getId());
            System.out.println("Catégorie : " + article.getCategorie());
            System.out.println("Description : " + article.getDescription());
            System.out.println("Quantité : " + article.getQuantite());
            System.out.println("Prix : " + article.getPrix() + " $");

            saisirInformationsArticleMod(article);
            System.out.println("Article modifié avec succès !");
        } else {
            System.out.println(Message.Article.NON_TROUVE);
        }
    }

    public void facturer() {
        System.out.println("\nFacture #" + numFacture);
        Article[] articlesFactures = genererFacture();
        Facture facture = new Facture(numFacture, articlesFactures);
        facture.afficherFacture();
        facture.enregistrerFacture();
    }

    public void sauvegarderArticles() {
        String nomFichier = "Sortie.txt";
        try (FileWriter writer = new FileWriter(nomFichier)) {
            if (articles.length == 0) {
                writer.write(Message.Article.VIDE+"\n");
            } else {
                for (Article article : articles) {
                    writer.write(article.toString() + "\n");
                }
            }
            System.out.println("Inventaire sauvegardé avec succès dans le fichier : " + nomFichier);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde de l'inventaire : " + e.getMessage());
        }
    }

    /**
     * Saisir les informations d'un nouvel article
     * @param id L'ID de l'article à créer.
     * @return Un objet Article contenant les informations saisies.
     */
    private Article saisirInformationsArticle(int id) {
        String categorie = Validations.corrigerMessage(Validations.validerStringNonVide("Entrez la catégorie de l'article : "));
        String description = Validations.validerStringNonVide("Entrez la description de l'article : ");
        int quantite = Validations.validerEntierPositif("Entrez la quantité de l'article : ");
        double prix = Validations.validerDoublePositif("Entrez le prix de l'article : ");

        return new Article(id, categorie, description, quantite, prix);
    }

    /**
     * Saisir les informations avec les instructions de modification
     * @param articleExistant L'article existant à modifier.
     * @return L'article modifié avec les nouvelles informations.
     */
    private Article saisirInformationsArticleMod(Article articleExistant) {
        String categorie = Validations.corrigerMessage(Validations.validerStringMod("Nouvelle catégorie (laissez vide pour conserver) : "));
        String description = Validations.validerStringMod("Nouvelle description (laissez vide pour conserver) : ");
        int quantite = Validations.validerEntierPositif("Nouvelle quantité (0 pour conserver) : ");
        double prix = Validations.validerDoublePositif("Nouveau prix (0 pour conserver) : ");

        // Mettre à jour l'article existant avec les nouvelles informations
        if (!categorie.isEmpty()) articleExistant.setCategorie(categorie);
        if (!description.isEmpty()) articleExistant.setDescription(description);
        if (quantite > 0) articleExistant.setQuantite(quantite);
        if (prix > 0) articleExistant.setPrix(prix);

        return articleExistant;
    }

    /**
     * Vérifie si un article avec l'ID spécifié existe dans la liste des articles.
     * @param id ID de l'article à rechercher.
     * @return L'article correspondant si trouvé, sinon `null`.
     */
    private Article articleExiste(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    /**
     * Gère le processus de vente en permettant au caissier de sélectionner des articles
     * et de définir les quantités à acheter. Réduit les quantités en stock et génère une
     * liste des articles facturés.
     * @return Un tableau des articles achetés pour la facture.
     */
    private Article[] genererFacture() {
        Article[] articlesFactures = new Article[0];

        while (true) {
            int id = Validations.validerEntierPositif("Entrez le code de produit (ID) ou 0 pour terminer : ");
            if (id == 0) break;

            Article article = articleExiste(id);
            if (article == null) {
                System.out.println(Message.Article.NON_TROUVE);
                continue;
            }

            System.out.println("Quantité disponible : " + article.getQuantite());
            int quantiteAchetee = Validations.validerEntierPositif("Quantité achetée : ");

            if (quantiteAchetee <= 0 || quantiteAchetee > article.getQuantite()) {
                System.out.println(Message.Quantite.OUTOFBOUND);
                continue;
            }
            Article articleFacture = new Article(article.getId(), article.getCategorie(), article.getDescription(), quantiteAchetee, article.getPrix());
            article.setQuantite(article.getQuantite() - quantiteAchetee);
            articlesFactures = ajoutTableauTemp(articlesFactures, articleFacture);
            System.out.println(Message.Article.FACTURE);
        }

        return articlesFactures;
    }

    /**
     * Ajoute un nouvel article à un tableau existant d'articles.
     * @param tableauActuel Tableau existant d'articles.
     * @param nouvelArticle Article à ajouter au tableau.
     * @return Un nouveau tableau contenant tous les articles, y compris le nouvel article.
     */
    private Article[] ajoutTableauTemp(Article[] tableauActuel, Article nouvelArticle) {
        Article[] tableauTemp = new Article[tableauActuel.length + 1];
        for (int i = 0; i < tableauActuel.length; i++) {
            tableauTemp[i] = tableauActuel[i];
        }
        tableauTemp[tableauActuel.length] = nouvelArticle;

        return tableauTemp;
    }

    /**
     * Supprime un article du tableau des articles en fonction de son ID.
     * Si l'article n'est pas trouvé, aucun changement n'est effectué.
     * @param id ID de l'article à supprimer.
     */
    private void supprimerArticleDuTableau(int id) {
        Article[] nouveauTableau = new Article[articles.length - 1];
        int index = 0;

        for (Article article : articles) {
            if (article.getId() != id) {
                if (index < nouveauTableau.length) {
                    nouveauTableau[index++] = article;
                }
            }
        }

        if (index == nouveauTableau.length) {
            articles = nouveauTableau;
        } else {
            System.out.println(Message.Article.NON_TROUVE);
        }
    }

    /**
     * Vérifie que l'ID existe en retournant un boolean pour la validation
     * @param id
     * @return
     */
    private boolean idExiste(int id) {
        for (int i = 0; i < articles.length; i++) {
            if (articles[i].getId() == id) {
                return true;
            }
        }
        return false;
    }

    private boolean articlesARisque() {
        boolean risqueTrouve = false;
        for (Article article : articles) {
            if (article.getQuantite() <= 5) {
                System.out.println("ID : " + article.getId() +
                        ", Catégorie : " + article.getCategorie() +
                        ", Description : " + article.getDescription() +
                        ", Quantité : " + article.getQuantite() +
                        ", Prix : " + article.getPrix() + " $");
                risqueTrouve = true;
            }
        }
        return risqueTrouve;
    }

    private Article[] chargerInventaire(String nomFichier) {
        Article[] articlesCharges = new Article[0]; // Tableau initial vide
        String fichier = "src/inventaire.txt";

              try {
                  FileReader FichierLu = new FileReader(fichier);
                 BufferedReader curseur = new BufferedReader(FichierLu);
            String ligne;
            while ((ligne = curseur.readLine()) != null) {

                if (ligne.trim().isEmpty()) continue;

                String[] details = ligne.split(";"); // Change to use `;` as delimiter
                if (details.length == 5) {
                    try {
                        int id = Integer.parseInt(details[0].trim());
                        String categorie = details[1].trim();
                        String description = details[2].trim();
                        int quantite = Integer.parseInt(details[3].trim());
                        double prix = Double.parseDouble(details[4].trim());

                        // Crée un nouvel article
                        Article nouvelArticle = new Article(id, categorie, description, quantite, prix);

                        // Ajoute l'article au tableau
                        articlesCharges = ajoutTableauTemp(articlesCharges, nouvelArticle);
                    } catch (NumberFormatException e) {
                        System.out.println("Erreur de format sur la ligne: " + ligne);
                    }
                } else {
                    System.out.println("Ligne mal formatée ignorée: " + ligne);
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

        return articlesCharges;
    }


}
