import java.util.Scanner;

public class TP3 {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Inventaire inventaire = new Inventaire("Canadian Tire", "inventaire.txt", 100);
        char choix = 0;

        do {
            System.out.println(Message.Menu.PRINCIPAL);
            choix = Character.toUpperCase(Validations.validerStringNonVide("Votre choix : ").charAt(0));
            switch (choix) {
                case 'A': inventaire.ajouterArticle(); break;
                case 'I': inventaire.afficherArticle(); break;
                case 'T': inventaire.afficherTousArticles(); break;
                case 'S': inventaire.supprimerArticle(); break;
                case 'L': inventaire.afficherArticlesARisque(); break;
                case 'M': inventaire.modifierArticle(); break;
                case 'F': inventaire.facturer(); break;
                case 'Q': inventaire.sauvegarderArticles(); break;
                default: System.out.println(Message.Menu.OPTION_INVALIDE); break;
            }

        } while (choix != 'Q');

        sc.close();
    }
}
