import java.io.*;

public class Facture {
    private int numFacture;
    private Article[] articlesFactures;

    public Facture(int numFacture, Article[] articlesFactures) {
        this.numFacture = numFacture;
        this.articlesFactures = articlesFactures;
    }

    /**
     * Construit une facture détaillée sous forme de chaîne à partir des articles facturés.
     * Inclut les calculs des sous-totaux, TPS, TVQ et total général.
     * @return Un objet StringBuilder contenant le texte de la facture.
     */
    public StringBuilder construireFacture() {
        StringBuilder factureBuilder = new StringBuilder();
        if (articlesFactures.length == 0) {
            factureBuilder.append(Message.FACTURE_VIDE);
        } else {
            double total = 0.0;
            factureBuilder.append("FACTURE ").append(numFacture).append("\n")
                    .append("ITEM    DESCRIPTION                                    QTE     TOTAL\n");
            for (Article article : articlesFactures) {
                double montantArticle = article.getQuantite() * article.getPrix();

                factureBuilder.append(String.format("%-11d", article.getId()))
                        .append(String.format("%-40s", article.getDescription()))
                        .append(String.format("%5d", article.getQuantite()))
                        .append(String.format("%10.2f $", montantArticle))
                        .append("\n");

                total += montantArticle;
            }
            double tps = total * 0.05;
            double tvq = total * 0.0995;
            double totalAvecTaxes = total + tps + tvq;
            factureBuilder.append("\n")
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
     */
    public void afficherFacture() {
        if (articlesFactures.length == 0) {
            System.out.println(Message.FACTURE_VIDE);
        } else {
            System.out.println(construireFacture().toString());
        }
    }

    /**
     * Enregistre la facture générée dans un fichier texte. Gère la numérotation des fichiers
     * pour éviter les conflits.
     */
    public void enregistrerFacture() {
        StringBuilder contenuFacture = construireFacture();
        String nomFichierBase = "facture";
        String extension = ".txt";

        File fichier;
        do {
            String nomFichier = nomFichierBase + numFacture + extension;
            fichier = new File(nomFichier);
            numFacture++;
        } while (fichier.exists());

        try (FileWriter writer = new FileWriter(fichier)) {
            writer.write(contenuFacture.toString());
            System.out.println("Facture enregistrée avec succès dans le fichier : " + fichier.getName());
        } catch (IOException e) {
            System.out.println("Erreur lors de l'enregistrement de la facture : " + e.getMessage());
        }
    }
}
