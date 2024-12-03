import java.io.*;

public class Facture {
    private int numFacture;
    private Article[] articlesFactures;

    public Facture(int numFacture, Article[] articlesFactures) {
        this.numFacture = numFacture;
        this.articlesFactures = articlesFactures;
    }

    /**
     * Construit le contenu textuel de la facture avec les détails des articles.
     * @return Un StringBuilder contenant la facture formatée.
     **/
    public StringBuilder construireFacture() {
        StringBuilder factureBuilder = new StringBuilder();
        if (Validations.verifierTableauVide(articlesFactures)) {
            factureBuilder.append(Message.Facture.VIDE);
        } else {
            double total = 0.0;
            numFacture = verifiernomfichier(numFacture);
            factureBuilder.append("FACTURE ").append(numFacture).append("\n")
                    .append("ITEM    DESCRIPTION                                    QTE     TOTAL\n");
            for (int i = 0; i < articlesFactures.length; i++) {
                double montantArticle = articlesFactures[i].getQuantite() * articlesFactures[i].getPrix();

                factureBuilder.append(String.format("%-11d", articlesFactures[i].getId()))
                        .append(String.format("%-40s", articlesFactures[i].getDescription()))
                        .append(String.format("%5d", articlesFactures[i].getQuantite()))
                        .append(String.format("%10.2f CAD", montantArticle))
                        .append("\n");

                total += montantArticle;
            }
            double tps = total * 0.05;
            double tvq = total * 0.0995;
            double totalAvecTaxes = total + tps + tvq;
            factureBuilder.append("\n")
                    .append(String.format("%42s Sous-total : %10.2f CAD%n", "", total))
                    .append(String.format("%42s TPS (5%%)   : %10.2f CAD%n", "", tps))
                    .append(String.format("%42s TVQ (9.95%%): %10.2f CAD%n", "", tvq))
                    .append(String.format("%42s Total      : %10.2f CAD%n", "", totalAvecTaxes));
        }
        return factureBuilder;
    }

    /**
     * Affiche la facture générée dans la console. Si aucun article n'est facturé, un
     * message indiquant une facture vide est affiché.
     */
    public void afficherFacture() {
        if (Validations.verifierTableauVide(articlesFactures)) {
            System.out.println(Message.Facture.VIDE);
        } else {
            System.out.println(construireFacture().toString());
        }
    }

    /**
     * Enregistre la facture générée dans un fichier texte.
     */
    public void enregistrerFacture() {
        String nomFichierBase = "facture";
        String extension = ".txt";

        File fichier;
        String nomFichier;
        do {
            nomFichier = nomFichierBase + numFacture + extension;
            fichier = new File(nomFichier);
            if (fichier.exists()) {
                numFacture++;
            }
        } while (fichier.exists());
        StringBuilder contenuFacture = construireFacture();

        try (FileWriter writer = new FileWriter(fichier)) {
            writer.write(contenuFacture.toString());
            System.out.println("Facture enregistrée avec succès dans le fichier : " + fichier.getName());
        } catch (IOException e) {
            System.out.println("Erreur lors de l'enregistrement de la facture : " + e.getMessage());
        }
    }

    /**
     * Vérifie la numérotation du fichier de la facture pour éviter les conflits de nom
     * avec les fichiers existants. Si le fichier existe déjà, le numéro de la facture
     * est incrémenté pour garantir un nom de fichier unique.
     * @param numFacture Le numéro de la facture à vérifier.
     * @return Le numéro de la facture, mis à jour si nécessaire.
     */
    public int verifiernomfichier(int numFacture) {
        File dossierTP3 = new File(".");
        File[] fichiers = dossierTP3.listFiles();
        if (fichiers != null) {
            for (int i = 0; i < fichiers.length; i++) {
                String nom = fichiers[i].getName();
                if (nom.startsWith("facture")) {
                    try {
                        String numeroStr = nom.substring(7, nom.indexOf('.'));
                        int numero = Integer.parseInt(numeroStr);

                        if (numero >= numFacture) numFacture = numero + 1;

                    }
                    catch (NumberFormatException e) {
                        System.out.println("Erreur: " + e.getMessage());
                    }
                    catch (StringIndexOutOfBoundsException e){
                        System.out.println("Erreur: " + e.getMessage());
                    }
                }
            }
        }

        return numFacture;
    }

}
