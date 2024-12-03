public class Message {
    // MENU
    public static class Menu {
        public static final String PRINCIPAL = "Menu principal\n" +
                "**************************************************\n" +
                "*      Bienvenue chez Le Meilleur                *\n" +
                "*        Choisissez une option :                 *\n" +
                "**************************************************\n" +
                "*    A      Ajouter un article                   *\n" +
                "*    I      Afficher les détails d'un article    *\n" +
                "*    T      Afficher tous les articles           *\n" +
                "*    S      Supprimer un article                 *\n" +
                "*    L      Afficher les articles à risque       *\n" +
                "*    M      Modifier un article                  *\n" +
                "*    F      Facturation                          *\n" +
                "*    Q      Quitter                              *\n" +
                "**************************************************\n";
        public static final String OPTION_INVALIDE = "Option invalide. Veuillez réessayer.";
    }

    // ID
    public static class Id {
        public static final String ENTREE = "";
        public static final String SEQUENTIEL = "ID non séquentiel !\nVoici l'ID séquentiel : ";
        public static final String EXISTANT = " correspond à l'article ";
    }

    // QUANTITE
    public static class Quantite {
        public static final String SUPPLEMENTAIRE = "Entrez la quantité supplémentaire : ";
        public static final String MAJ = "La quantité de l'article existant a été mise à jour.";
        public static final String OUTOFBOUND = "Quantité invalide. Article non ajouté.";
    }

    // ARTICLE
    public static class Article {
        public static final String AJOUTE_SUCCES = "Nouvel article ajouté avec succès.";
        public static final String NON_TROUVE = "Article non trouvé.";
        public static final String MOD = "Article modifié avec succès !";
        public static final String RISQUE = "⚠: Article à risque trouvé dans la liste.\nVeuillez vous rendre au menu L pour les regrouper.";
        public static final String SUPPRIME_SUCCES = "Article supprimé avec succès !";
        public static final String VIDE = "La liste est vide";
        public static final String FACTURE = "Article ajouté à la facture.";
        public static final String SUPPANNUL = "Suppression annulée.";
    }

    // FACTURE
    public static class Facture {
        public static final String VIDE = "Facture vide. Aucun achat effectué.";
    }

    // ERREUR
    public static class Erreur {
        public static final String INVALIDE = "Erreur : Veuillez entrer une valeur valide.";
        public static final String POSITIVE = "Erreur : La valeur doit être positive.";
        public static final String VIDE = "Erreur : La valeur ne peut pas être vide.";
    }
}
