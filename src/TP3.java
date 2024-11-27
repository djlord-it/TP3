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
    private static final String MENU = "Menu principal\n" +
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
    private static final String MSG_INVLD = "Option invalide. Veuillez réessayer.";

    public static void main(String[] args) {
        Inventaire inventaire = new Inventaire("Canadian Tire", new Article[0], 100);
        char choix = 0;

        do {
            System.out.println(MENU);
            System.out.print("Votre choix : ");
            String entree = sc.nextLine().trim();
            if (entree.isEmpty()) {
                System.out.println(MSG_INVLD);
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
                default: System.out.println(MSG_INVLD); break;
            }

            if (choix != 'Q') {
                System.out.println("\nAppuyez sur Entrée pour continuer...");
                sc.nextLine();
            }

        } while (choix != 'Q');

        sc.close();
    }
}
