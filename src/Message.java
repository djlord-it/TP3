public class Message {
    //MENU
    public static final String MENU = "Menu principal\n" +
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
    public static final String MSG_INVLD = "Option invalide. Veuillez réessayer.";
    //ID
    public static final String ID_ENTREE = "";
    public static final String ID_SEQUENTIEL = "ID non séquentiel !\nVoici l'ID séquentiel : ";
    public static final String ID_EXISTANT = " correspond à l'article ";
    //QUANTITE
    public static final String QUANTITE_SUPPLEMENTAIRE = "Entrez la quantité supplémentaire : ";
    public static final String QUANTITE_MAJ = "La quantité de l'article existant a été mise à jour.";
    public static final String QUANTITE_OUTOFBOUND = "Quantité invalide. Article non ajouté.";
    //ARTICLE
    public static final String ARTICLE_AJOUTE_SUCCES = "Nouvel article ajouté avec succès.";
    public static final String ARTICLE_NON_TROUVE = "Article non trouvé.";
    public static final String ARTICLE_AFFICHE = "\nInformations de l'article :";
    public static final String ARTICLE_TOUS = "\nListe de tous les articles :";
    public static final String ARTICLE_RISQUE = "⚠: Article à risque trouvé dans la liste.\nVeuillez vous rendre au menu L pour les regrouper.";
    public static final String ARTICLE_SUPPRIME_SUCCES = "Article supprimé avec succès !";
    public static final String ARTICLE_VIDE = "La liste est vide";
    public static final String ARTICLE_FACTURE = "Article ajouté à la facture.";
    public static final String SAUVEGARDE_SUCCES = "Inventaire sauvegardé avec succès dans le fichier : ";
    //FACTURE
    public static final String FACTURE_VIDE = "Facture vide. Aucun achat effectué.";
    public static final String FACTURE_SAUVEGARDEE = "Facture enregistrée avec succès dans le fichier : ";
    //ERREUR
    public static final String ERREUR_SAUVEGARDE = "Erreur lors de la sauvegarde de l'inventaire : ";
    public static final String ERREUR_INVLD = "Erreur : Veuillez entrer une valeur valide.";
    public static final String ERREUR_PSTV = "Erreur : La valeur doit être positive.";
    public static final String ERREUR_VIDE = "Erreur : La valeur ne peut pas être vide.";

}
