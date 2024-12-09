import java.util.*;
public class Validations {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Vérifie si le tableau d'articles est vide ou nul.
     * @param articles Le tableau d'articles à vérifier.
     * @return true si le tableau est vide ou nul, sinon false.
     */
    public static boolean verifierTableauVide(Article[] articles) {
        return articles == null || articles.length == 0;
    }

    /**
     * Demande à l'utilisateur une chaîne de caractères non vide.
     * Si l'utilisateur entre une chaîne vide, un message d'erreur est affiché.
     * @param message Le message à afficher avant de demander la saisie.
     * @return La chaîne saisie par l'utilisateur.
     */
    public static String validerStringNonVide(String message) {
        String valeur;
        do {
            System.out.print(message);
            valeur = scanner.nextLine().trim();
            if (valeur.isEmpty()) {
                System.out.println(Message.Erreur.VIDE);
            }
        } while (valeur.isEmpty());
        return valeur;
    }

    /**
     * Demande à l'utilisateur de saisir un entier positif.
     * Si l'utilisateur entre un entier négatif ou une valeur invalide, un message d'erreur est affiché.
     * @param message Le message à afficher avant de demander la saisie.
     * @return L'entier positif saisi par l'utilisateur.
     */
    public static int validerEntierPositif(String message) {
        int valeur = -1;
        do {
            try {
                System.out.print(message);
                valeur = scanner.nextInt();
                scanner.nextLine();
                if (valeur < 0) {
                    System.out.println(Message.Erreur.POSITIVE);
                }
            } catch (InputMismatchException e) {
                System.out.println(Message.Erreur.INVALIDE);
                scanner.nextLine();
            }
        } while (valeur < 0);
        return valeur;
    }

    /**
     * Demande à l'utilisateur de saisir un nombre à virgule flottante positif.
     * Si l'utilisateur entre un nombre négatif ou une valeur invalide, un message d'erreur est affiché.
     * @param message Le message à afficher avant de demander la saisie.
     * @return Le nombre à virgule flottante positif saisi par l'utilisateur.
     */
    public static double validerDoublePositif(String message) {
        double valeur = -1;
        do {
            try {
                System.out.print(message);
                valeur = scanner.nextDouble();
                scanner.nextLine();
                if (valeur < 0) {
                    System.out.println(Message.Erreur.POSITIVE);
                }
            } catch (InputMismatchException e) {
                System.out.println(Message.Erreur.INVALIDE);
                scanner.nextLine();
            }
        } while (valeur < 0);
        return valeur;
    }

    /**
     * Demande à l'utilisateur de saisir une chaîne de caractères.
     * Methode peu semblable à validerStringNonVide, mais celui-ci accepte une chaine vide qui est necessaire.
     * @param message Le message à afficher avant de demander la saisie.
     * @return La chaîne saisie par l'utilisateur.
     */
    public static String validerStringMod(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    /**
     * Corrige et valide un message.
     * le message ne peut pas être constitué que des chiffres seulement ou avoir des chiffres au milieu.
     * la méthode corrige le message si et seulement si les chiffres sont placés aux extremités
     * @param message Le message à valider et corriger.
     * @return Le message corrigé.
     * @throws IllegalArgumentException Si le message contient des erreurs de format.
     */
    public static String corrigerMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            return message.trim();
        }

        message = message.trim();

        boolean contientChiffresSeulement = true;
        for (int i = 0; i < message.length(); i++) {
            if (!Character.isDigit(message.charAt(i))) {
                contientChiffresSeulement = false;
            }
        }

        if (contientChiffresSeulement) {
            throw new IllegalArgumentException("La categorie ne peut pas être constitué de chiffres seulement");
        }

        for (int i = 1; i < message.length() - 1; i++) {
            if (Character.isDigit(message.charAt(i)) &&
                    Character.isLetter(message.charAt(i-1)) &&
                    Character.isLetter(message.charAt(i+1))) {
                throw new IllegalArgumentException("La categorie ne peut pas contenir des chiffres au milieu");
            }
        }

        StringBuilder nomCorrige = new StringBuilder();
        boolean check = false;

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (Character.isLetter(c) || Character.isSpaceChar(c) || c == '-' || c == '_' || c == '\'') {
                nomCorrige.append(c);
                check = false;
            }
        }

        return nomCorrige.toString().trim();
    }

}
