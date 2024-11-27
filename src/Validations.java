import java.util.*;
public class Validations {

    public static final Scanner scanner = new Scanner(System.in);

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

    public static String validerStringMod(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public static String corrigerMessage(String message) {
        message = message.trim();
        String nomCorrige = "";
        String nomValide = "";
        boolean check = false;

        // Première boucle pour supprimer les caractères non alphabétiques sauf les espaces
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (Character.isLetter(c) || Character.isSpaceChar(c)) {
                nomCorrige += c; // Concatène uniquement les caractères valides
            }
        }

        // Deuxième boucle pour supprimer les espaces supplémentaires
        for (int i = 0; i < nomCorrige.length(); i++) {
            char c = nomCorrige.charAt(i);
            if (c != ' ') {
                nomValide += c; // Concatène le caractère s'il n'est pas un espace
                check = false;
            } else if (!check) {
                nomValide += c; // Concatène un seul espace si le dernier caractère n'était pas un espace
                check = true;
            }
        }

        return nomValide.trim();
    }
}
