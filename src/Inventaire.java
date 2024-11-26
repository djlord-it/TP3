import java.io.*;
import java.util.*;

public class Inventaire {
    private static Scanner scanner = new Scanner(System.in);
    private String succursale;
    private Article[] articles;
    private int numFacture;

    public Inventaire(String succursale, Article[] articles, int numFacture) {
        this.succursale = succursale;
        this.articles = articles != null ? articles : new Article[0];
        this.numFacture = numFacture;
    }


    /**
     * Ajoute un nouvel article à l'inventaire.
     * l'utilisateur peut définir l'ID initial.
     * Pour la suite, l'utilisateur peut ajouter un ID pourvu qu'il soit séquentiel
     * sinon ce dernier sera généré automatiquement si nécessaire.
     * Méthodes liées :
     * - `validerEntierPositif(String message)`
     * - `validerStringNonVide(String message)`
     * - `validerDoublePositif(String message)`
     */
    public void ajouterArticle() {
        int id;
        boolean check = false;

        if (articles.length == 0) {
            // Aucun article dans le tableau, l'utilisateur peut définir l'ID initial
            id = validerEntierPositif("Entrez l'ID du premier article : ");
        } else {
            // Les articles suivants doivent être séquentiels
            int dernierId = articles[articles.length - 1].getId();
            System.out.print("Entrez l'ID : ");
            id = scanner.nextInt();
            check = idExiste(id); // Vérifie si l'ID existe déjà
            scanner.nextLine();
            if (!check && id != dernierId + 1) {
                id = dernierId + 1; // Générer le prochain ID séquentiel
                System.out.println("Le nouvel ID généré est : " + id);
                scanner.nextLine();
            }
        }

        if (check) {
            // Si l'article existe, ajuster la quantité
            Article articleExistant = ArticleExiste(id);
            if (articleExistant != null) {
                System.out.println("l'ID "+ articleExistant.getId() + " correspond à l'article '" + articleExistant.getDescription()+"'");
                int quantiteAdditionnelle = validerEntierPositif(
                        "Entrez la quantité supplémentaire : ");
                articleExistant.setQuantite(articleExistant.getQuantite() + quantiteAdditionnelle);
                System.out.println("La quantité de l'article existant a été mise à jour.");
            }
        } else {
            // Reste des informations sur l'article
            String categorie = validerStringNonVide("Entrez la catégorie de l'article : ");
            String description = validerStringNonVide("Entrez la description de l'article : ");
            int quantite = validerEntierPositif("Entrez la quantité de l'article : ");
            double prix = validerDoublePositif("Entrez le prix de l'article : ");

            // Création de l'article
            Article nouvelArticle = new Article(id, categorie, description, quantite, prix);
            Article[] nouveauTableau = new Article[articles.length + 1];
            for (int i = 0; i < articles.length; i++) {
                nouveauTableau[i] = articles[i];
            }
            nouveauTableau[articles.length] = nouvelArticle;
            articles = nouveauTableau;
            System.out.println("Nouvel article ajouté avec succès.");
        }
    }

    public void afficherArticle() {
        int id = validerEntierPositif("Entrez l'ID de l'article à afficher : ");
        Article article = ArticleExiste(id);

        if (article != null) {
            System.out.println("\nInformations de l'article :");
            System.out.println("ID : " + article.getId());
            System.out.println("Catégorie : " + article.getCategorie());
            System.out.println("Description : " + article.getDescription());
            System.out.println("Quantité : " + article.getQuantite());
            System.out.println("Prix : " + article.getPrix() + " $");
        } else {
            System.out.println("Article non trouvé.");
        }
    }

    public void afficherTousArticles() {
        if (articles.length == 0) {
            System.out.println("Aucun article dans l'inventaire.");
            return;
        }

        System.out.println("\nListe de tous les articles :");
        for (Article article : articles) {
            System.out.println(article.toString());
        }
    }

    public void supprimerArticle() {
        if (articles.length == 0) {
            System.out.println("Aucun article à supprimer.");
            return;
        }

        int id = validerEntierPositif("Entrez l'ID de l'article à supprimer : ");
        Article article = ArticleExiste(id);

        if (article != null) {
            System.out.println("\nArticle trouvé :");
            System.out.println("Catégorie : " + article.getCategorie());
            System.out.println("Description : " + article.getDescription());

            System.out.print("Voulez-vous vraiment supprimer cet article ? (o/O pour confirmer) : ");
            String confirmation = scanner.nextLine().toLowerCase();

            if (confirmation.equals("o")) {
                supprimerArticleDuTableau(id);
                System.out.println("Article supprimé avec succès !");
            } else {
                System.out.println("Suppression annulée.");
            }
        } else {
            System.out.println("Article non trouvé.");
        }
    }

    public void afficherArticlesARisque() {
        if (articles.length == 0) {
            System.out.println("Aucun article dans l'inventaire.");
            return;
        }

        System.out.println("\nArticles à risque (quantité <= 5) :");
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
        if (!risqueTrouve) {
            System.out.println("Aucun article à risque.");
        }
    }

    public void modifierArticle() {
        int id = validerEntierPositif("Entrez l'ID de l'article à modifier : ");
        Article article = ArticleExiste(id);

        if (article != null) {
            System.out.println("\nArticle trouvé :");
            System.out.println("ID : " + article.getId());
            System.out.println("Catégorie : " + article.getCategorie());
            System.out.println("Description : " + article.getDescription());
            System.out.println("Quantité : " + article.getQuantite());
            System.out.println("Prix : " + article.getPrix() + " $");

            String categorie = validerStringMod("Nouvelle catégorie (laissez vide pour conserver) : ");
            String description = validerStringMod("Nouvelle description (laissez vide pour conserver) : ");
            int quantite = validerEntierPositif("Nouvelle quantité (0 pour conserver) : ");
            double prix = validerDoublePositif("Nouveau prix (0 pour conserver) : ");

            if (!categorie.isEmpty()) article.setCategorie(categorie);
            if (!description.isEmpty()) article.setDescription(description);
            if (quantite > 0) article.setQuantite(quantite);
            if (prix > 0) article.setPrix(prix);

            System.out.println("Article modifié avec succès !");
        } else {
            System.out.println("Article non trouvé.");
        }
    }

    public void facturer() {
        numFacture++;
        System.out.println("\nFacture #" + numFacture);
        Article[] articlesFactures = genererFacture();
        enregistrerFactureDansFichier(articlesFactures);
        afficherFacture(articlesFactures);
    }

    public void sauvegarderArticles() {
        String nomFichier = "Sortie.txt";
        try (FileWriter writer = new FileWriter(nomFichier)) {
            if (articles.length == 0) {
                writer.write("Aucun article dans l'inventaire.\n");
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
     * Vérifie si un article avec l'ID spécifié existe dans la liste des articles.
     * @param id ID de l'article à rechercher.
     * @return L'article correspondant si trouvé, sinon `null`.
     */
    private Article ArticleExiste(int id) {
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
            int id = validerEntierPositif("Entrez le code de produit (ID) ou 0 pour terminer : ");
            if (id == 0) break;

            Article article = ArticleExiste(id);
            if (article == null) {
                System.out.println("Article non trouvé.");
                continue;
            }

            System.out.println("Quantité disponible : " + article.getQuantite());
            int quantiteAchetee = validerEntierPositif("Quantité achetée : ");

            if (quantiteAchetee <= 0 || quantiteAchetee > article.getQuantite()) {
                System.out.println("Quantité invalide. Article non ajouté.");
                continue;
            }

            // Créer un nouvel article pour la facture
            Article articleFacture = new Article(
                    article.getId(),
                    article.getCategorie(),
                    article.getDescription(),
                    quantiteAchetee,
                    article.getPrix()
            );

            // Réduire la quantité dans l'inventaire
            article.setQuantite(article.getQuantite() - quantiteAchetee);

            // Ajouter l'article à la facture
            articlesFactures = ajouterAuTableau(articlesFactures, articleFacture);

            System.out.println("Article ajouté à la facture.");
        }

        return articlesFactures;
    }

    /**
     * Construit une facture détaillée sous forme de chaîne à partir des articles facturés.
     * Inclut les calculs des sous-totaux, TPS, TVQ et total général.
     * @param articlesFactures Tableau des articles achetés.
     * @return Un objet StringBuilder contenant le texte de la facture.
     */
    private StringBuilder construireFacture(Article[] articlesFactures) {
        StringBuilder factureBuilder = new StringBuilder();
        if (articlesFactures.length == 0) {
            System.out.println("Facture vide. Aucun achat effectué.");
        } else {
            // Calcul du total
            double total = 0.0;

            factureBuilder.append("FACTURE ").append(numFacture).append("\n")
                    .append("ITEM    DESCRIPTION                                    QTE     TOTAL\n")
                    .append("------------------------------------------------------------------------\n");

            for (int i = 0; i < articlesFactures.length; i++) {
                Article article = articlesFactures[i];
                double montantArticle = article.getQuantite() * article.getPrix();

                factureBuilder.append(String.format("%-11d", article.getId()))
                        .append(String.format("%-40s", article.getDescription()))
                        .append(String.format("%5d", article.getQuantite()))
                        .append(String.format("%10.2f $", montantArticle))
                        .append("\n");

                total += montantArticle;
            }

            // Calcul des taxes
            double tps = total * 0.05;
            double tvq = total * 0.0995;
            double totalAvecTaxes = total + tps + tvq;

            // Ajouter le récapitulatif
            factureBuilder.append("------------------------------------------------------------------------\n")
                    .append(String.format("%42s Sous-total : %10.2f $%n", "", total))
                    .append(String.format("%42s TPS (5%%)   : %10.2f $%n", "", tps))
                    .append(String.format("%42s TVQ (9.95%%): %10.2f $%n", "", tvq))
                    .append(String.format("%42s Total      : %10.2f $%n", "", totalAvecTaxes));
        }


        return factureBuilder;
    }

    /**
     * Affiche la facture générée dans la console. Si aucun article n'est facturé, un
     * message indiquant une facture vide est affiché.
     * @param articlesFactures Tableau des articles achetés.
     */
    private void afficherFacture(Article[] articlesFactures) {
        if (articlesFactures.length == 0) {
            System.out.println("Facture vide. Aucun achat effectué.");
            return;
        }

        StringBuilder facture = construireFacture(articlesFactures);
        System.out.println(facture.toString());
    }

    /**
     * Enregistre la facture générée dans un fichier texte. Gère la numérotation des fichiers
     * pour éviter les conflits.
     * @param articlesFactures Tableau des articles achetés.
     */
    private void enregistrerFactureDansFichier(Article[] articlesFactures) {
        StringBuilder contenuFacture = construireFacture(articlesFactures);

        // Générer le nom du fichier
        String nomFichierBase = "facture";
        String extension = ".txt";
        int numero = 101;

        // Vérifier si un fichier existe et incrémenter jusqu'à trouver un nom libre
        File fichier;
        do {
            String nomFichier = nomFichierBase + numero + extension;
            fichier = new File(nomFichier);
            numero++;
        } while (fichier.exists());

        // Écrire dans le fichier trouvé
        try (FileWriter writer = new FileWriter(fichier)) {
            writer.write(contenuFacture.toString());
            System.out.println("Facture enregistrée avec succès dans le fichier : " + fichier.getName());
        } catch (IOException e) {
            System.out.println("Erreur lors de l'enregistrement de la facture : " + e.getMessage());
        }
    }

    /**
     * Ajoute un nouvel article à un tableau existant d'articles.
     * @param tableauActuel Tableau existant d'articles.
     * @param nouvelArticle Article à ajouter au tableau.
     * @return Un nouveau tableau contenant tous les articles, y compris le nouvel article.
     */
    private Article[] ajouterAuTableau(Article[] tableauActuel, Article nouvelArticle) {
        Article[] nouveauTableau = new Article[tableauActuel.length + 1];

        // Copier les articles existants
        for (int i = 0; i < tableauActuel.length; i++) {
            nouveauTableau[i] = tableauActuel[i];
        }

        // Ajouter le nouvel article
        nouveauTableau[tableauActuel.length] = nouvelArticle;

        return nouveauTableau;
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
            System.out.println("Erreur : Article non trouvé. Aucun changement effectué.");
        }
    }

    /**
     * Valide l'entrée d'un nouvel ID pour un article en s'assurant qu'il est unique.
     * Demande à l'utilisateur de réessayer en cas de conflit.
     * @return Un ID unique validé.
     */
    private int validerNouvelId() {
        int id;
        do {
            id = validerEntierPositif("Entrez l'ID de l'article : ");
            if (ArticleExiste(id) != null) {
                System.out.println("Erreur : Un article avec cet ID existe déjà.");
            }
        } while (ArticleExiste(id) != null);
        return id;
    }

    /**
     * Demande à l'utilisateur d'entrer une chaîne non vide.
     * Répète la saisie tant qu'une chaîne valide n'est pas fournie.
     * @param message Message d'invite affiché à l'utilisateur.
     * @return Une chaîne non vide validée.
     */
    private String validerStringNonVide(String message) {
        String valeur;
        do {
            System.out.print(message);
            valeur = scanner.nextLine().trim();
            if (valeur.isEmpty()) {
                System.out.println("Erreur : La valeur ne peut pas être vide.");
            }
        } while (valeur.isEmpty());
        return valeur;
    }

    /**
     * Valide l'entrée d'un entier positif. Demande une nouvelle saisie en cas
     * de valeur négative ou d'entrée invalide.
     * @param message Message d'invite affiché à l'utilisateur.
     * @return Un entier positif validé.
     */
    private int validerEntierPositif(String message) {
        int valeur = -1;
        do {
            try {
                System.out.print(message);
                valeur = scanner.nextInt();
                scanner.nextLine(); // Consommer la nouvelle ligne
                if (valeur < 0) {
                    System.out.println("Erreur : La valeur doit être positive.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erreur : Veuillez entrer un entier valide.");
                scanner.nextLine(); // Consommer la mauvaise entrée
            }
        } while (valeur < 0);
        return valeur;
    }

    /**
     * Valide l'entrée d'un nombre décimal positif. Répète la saisie en cas de valeur
     * négative ou d'entrée invalide.
     * @param message Message d'invite affiché à l'utilisateur.
     * @return Un nombre décimal positif validé.
     */
    private double validerDoublePositif(String message) {
        double valeur = -1;
        do {
            try {
                System.out.print(message);
                valeur = scanner.nextDouble();
                scanner.nextLine(); // Consommer la nouvelle ligne
                if (valeur < 0) {
                    System.out.println("Erreur : La valeur doit être positive.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erreur : Veuillez entrer un nombre valide.");
                scanner.nextLine(); // Consommer la mauvaise entrée
            }
        } while (valeur < 0);
        return valeur;
    }

    /**
     * Demande à l'utilisateur d'entrer une chaîne, qui peut être vide.
     * Nécessaire pour la méthode de la modification qui accepte un String vide
     * @param message Message d'invite affiché à l'utilisateur.
     * @return Une chaîne saisie par l'utilisateur.
     */
    private String validerStringMod(String message) {
        System.out.print(message);
        return scanner.nextLine().trim(); // Retourne même une chaîne vide si aucune entrée n'est fournie
    }

    private boolean idExiste(int id) {
        for (int i = 0; i < articles.length; i++) {
            if (articles[i].getId() == id) {
                return true;
            }
        }
        return false;
    }

}
