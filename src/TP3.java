/**
 * @ Jessee Lord DUSHIME
 * @ Franck Enrico NDJEGNIA
 * code permanent: DUSJ72280204
 * code permanent: FRAN20298605
 * courriel: dushime.jessee_lord@courrier.uqam.ca
 * courriel: franck_enrico.ndjegnia@courrier.uqam.ca
 * Groupe 040 A24
 */

import java.util.Scanner;

public class TP3 {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        Inventaire inventaire = new Inventaire("Canadian Tire", "inventaire.txt", 100);
        char choix = 0;

        do {
            System.out.println(Message.MENU);
            System.out.print("Votre choix : ");
            String entree = sc.nextLine().trim();
            if (entree.isEmpty()) {
                System.out.println(Message.MSG_INVLD);
                continue;
            }

            choix = Character.toUpperCase(entree.charAt(0));

            switch (choix) {
                case 'A': inventaire.ajouterArticle(); break;
                case 'I': inventaire.afficherArticle(); break;
                case 'T': inventaire.afficherTousArticles(); break;
                case 'S': inventaire.supprimerArticle(); break;
                case 'L': inventaire.afficherArticlesARisque(); break;
                case 'M': inventaire.modifierArticle(); break;
                case 'F': inventaire.facturer(); break;
                case 'Q': inventaire.sauvegarderArticles(); break;
                default: System.out.println(Message.MSG_INVLD); break;
            }

            if (choix != 'Q') {
                System.out.println("\nAppuyez sur Entrée pour continuer...");
                sc.nextLine();
            }

        } while (choix != 'Q');

        sc.close();
    }
}
