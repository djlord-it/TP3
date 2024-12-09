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

    /**
     * Ajoute un article à l'inventaire, soit en l'ajoutant comme nouveau, soit en mettant à jour
     * la quantité d'un article existant.
     */
    public void ajouterArticle() {
        int id;
        boolean check = false;

        if (Validations.verifierTableauVide(articles)) {
            id = Validations.validerEntierPositif("Entrez l'ID du premier article : ");
        } else {
            int dernierId = articles[articles.length - 1].getId();
            id = Validations.validerEntierPositif("Entrez l'ID : ");
            check = idExiste(id);
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

    /**
     * Affiche les informations d'un article spécifique à partir de son ID.
     */
    public void afficherArticle() {
        int id = Validations.validerEntierPositif("Entrez l'ID de l'article à afficher : ");
        Article article = articleExiste(id);

        if (article != null) {
            System.out.println("\nInformations de l'article :");
            System.out.println("ID : " + article.getId());
            System.out.println("Catégorie : " + article.getCategorie());
            System.out.println("Description : " + article.getDescription());
            System.out.println("Quantité : " + article.getQuantite());
            System.out.println("Prix : " + article.getPrix() + " CAD");
        } else {
            System.out.println(Message.Article.NON_TROUVE);
        }
    }

    /**
     * Affiche la liste complète des articles dans l'inventaire.
     */
    public void afficherTousArticles() {
        if (Validations.verifierTableauVide(articles)) {
            System.out.println(Message.Article.VIDE);
        } else {
            boolean risqueTrouve = false;
            System.out.println("\nListe de tous les articles :");
            for (int i = 0; i < articles.length; i++) {
                System.out.print(articles[i].toString());
                if (articles[i].getQuantite() <= 10) {
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

    /**
     * Supprime un article de l'inventaire en fonction de son ID après confirmation de l'utilisateur.
     */
    public void supprimerArticle() {
        if (Validations.verifierTableauVide(articles)) {
            System.out.println(Message.Article.VIDE);
        }else {
            int id = Validations.validerEntierPositif("Entrez l'ID de l'article à supprimer : ");
            Article article = articleExiste(id);

            if (article != null) {
                System.out.println("\nArticle trouvé :");
                System.out.println("Catégorie : " + article.getCategorie());
                System.out.println("Description : " + article.getDescription());

                char confirmation = Character.toLowerCase(Validations.validerStringNonVide("Voulez-vous vraiment supprimer cet article ? (o/O pour confirmer) : ").charAt(0));

                if (confirmation=='o') {
                    supprimerArticleDuTableau(id);
                    System.out.println(Message.Article.SUPPRIME_SUCCES);
                } else {
                    System.out.println(Message.Article.SUPPANNUL);
                }
            } else {
                System.out.println(Message.Article.NON_TROUVE);
            }
        }
    }

    /**
     * Affiche la liste des articles dont la quantité est inférieure ou égale à 10, considérés comme
     * étant à risque.
     */
    public void afficherArticlesARisque() {
        if (Validations.verifierTableauVide(articles)) {
            System.out.println(Message.Article.VIDE);
        } else {
            System.out.println("\nArticles à risque (quantité <= 10) :");
            boolean risqueTrouve = articlesARisque();
            if (!risqueTrouve) {
                System.out.println("Aucun article à risque.");
            }
        }
    }

    /**
     * Permet de modifier les informations d'un article existant, en fonction de son ID.
     */
    public void modifierArticle() {
        int id = Validations.validerEntierPositif("Entrez l'ID de l'article à modifier : ");
        Article article = articleExiste(id);
        if (article != null) {
            System.out.println("\nArticle trouvé :");
            System.out.println("ID : " + article.getId());
            System.out.println("Catégorie : " + article.getCategorie());
            System.out.println("Description : " + article.getDescription());
            System.out.println("Quantité : " + article.getQuantite());
            System.out.println("Prix : " + article.getPrix() + " CAD");

            saisirInformationsArticleMod(article);
            System.out.println(Message.Article.MOD);
        } else {
            System.out.println(Message.Article.NON_TROUVE);
        }
    }

    /**
     * Gère le processus de facturation, en générant une facture avec les articles sélectionnés par le caissier.
     */
    public void facturer() {
        Article[] articlesFactures = genererFacture();
        if(articlesFactures!=null && articlesFactures.length>0) {
            Facture facture = new Facture(numFacture, articlesFactures);
            numFacture = facture.verifiernomfichier(numFacture);
            System.out.println("Facture #" + numFacture);
            facture.afficherFacture();
            facture.enregistrerFacture();
        }else{
            System.out.println("Aucune facture: "+Message.Article.VIDE);
        }
    }

    /**
     * Sauvegarde l'inventaire actuel dans un fichier texte.
     */
    public void sauvegarderArticles() {
        String nomFichier = "Sortie.txt";
        try (FileWriter writer = new FileWriter(nomFichier)) {
            if (Validations.verifierTableauVide(articles)) {
                writer.write(Message.Article.VIDE+"\n");
            } else {
                for (int i = 0; i < articles.length; i++) {
                    writer.write(articles[i].toString() + "\n");
                }
            }
            System.out.println("Inventaire sauvegardé avec succès dans le fichier : " + nomFichier);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde de l'inventaire : " + e.getMessage());
        }
    }




    /**
     * Charge l'inventaire depuis un fichier externe.
     * @param nomFichier Le nom du fichier à charger.
     * @return Un tableau d'articles chargés depuis le fichier.
     */
    private Article[] chargerInventaire(String nomFichier) {
        Article[] articlesCharges = new Article[0];
        String fichier = "src/inventaire.txt";

        try {
            FileReader FichierLu = new FileReader(fichier);
            BufferedReader curseur = new BufferedReader(FichierLu);
            String ligne;
            while ((ligne = curseur.readLine()) != null) {
                if (!ligne.trim().isEmpty()) {
                    String[] details = ligne.split(";");
                    if (details.length == 5) {
                        try {
                            int id = Integer.parseInt(details[0].trim());
                            String categorie = details[1].trim();
                            String description = details[2].trim();

                            int quantite = Integer.parseInt(details[3].trim());
                            double prix = Double.parseDouble(details[4].trim());


                            Article nouvelArticle = new Article(id, categorie, description, quantite, prix);


                            articlesCharges = ajoutTableauTemp(articlesCharges, nouvelArticle);
                        } catch (NumberFormatException e) {
                            System.out.println("Erreur de format sur la ligne: " + ligne);
                        }
                    } else {
                        System.out.println("Ligne mal formatée ignorée: " + ligne);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

        return articlesCharges;
    }

    /**
     * Saisir les informations d'un nouvel article
     * @param id L'ID de l'article à créer.
     * @return Un objet Article contenant les informations saisies.
     */
    private Article saisirInformationsArticle(int id) {
        String categorie = "";
        boolean check = false;
        do {
            try {
                categorie = Validations.corrigerMessage(Validations.validerStringNonVide("Entrez la catégorie de l'article : "));
                check = false;
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur: " + e.getMessage());
                check = true;
            }
        }while(check);
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
        String categorie = "";
        boolean check = false;
        do {
            try {
                categorie = Validations.corrigerMessage(Validations.validerStringMod("Entrez la catégorie de l'article (laissez vide pour conserver) : "));
                check = false;
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur: " + e.getMessage());
                check = true;
            }
        }while(check);
        String description = Validations.validerStringMod("Nouvelle description (laissez vide pour conserver) : ");
        int quantite = Validations.validerEntierPositif("Nouvelle quantité (0 pour conserver) : ");
        double prix = Validations.validerDoublePositif("Nouveau prix (0 pour conserver) : ");


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
        for (int i = 0; i < articles.length; i++) {
            if (articles[i].getId() == id) {
                return articles[i];
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
        boolean check = true;

        while (check) {
            int id = Validations.validerEntierPositif("Entrez le code de produit (ID) ou 0 pour terminer : ");
            if (id == 0) {
                check = false;
            } else {
                Article article = articleExiste(id);
                if (article == null) {
                    System.out.println(Message.Article.NON_TROUVE);
                } else {
                    System.out.println("Quantité disponible : " + article.getQuantite());
                    int quantiteAchetee = Validations.validerEntierPositif("Quantité achetée : ");

                    if (quantiteAchetee <= 0 || quantiteAchetee > article.getQuantite()) {
                        System.out.println(Message.Quantite.OUTOFBOUND);
                    } else {
                        Article articleFacture = new Article(article.getId(), article.getCategorie(), article.getDescription(), quantiteAchetee, article.getPrix());
                        article.setQuantite(article.getQuantite() - quantiteAchetee);
                        articlesFactures = ajoutTableauTemp(articlesFactures, articleFacture);
                        System.out.println(Message.Article.FACTURE);
                    }
                }
            }
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

    /**
     * Vérifie si des articles dans l'inventaire sont à risque (quantité <= 10).
     * @return true si des articles à risque sont trouvés, false sinon.
     */
    private boolean articlesARisque() {
        boolean risqueTrouve = false;
        for (int i = 0; i < articles.length; i++) {
            if (articles[i].getQuantite() <= 10) {
                System.out.println("ID : " + articles[i].getId() +
                        ", Catégorie : " + articles[i].getCategorie() +
                        ", Description : " + articles[i].getDescription() +
                        ", Quantité : " + articles[i].getQuantite() +
                        ", Prix : " + articles[i].getPrix() + " CAD");
                risqueTrouve = true;
            }
        }
        return risqueTrouve;
    }



}
