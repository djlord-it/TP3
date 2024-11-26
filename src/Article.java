public class Article {
    private int id;
    private String categorie;
    private String description;
    private int quantite;
    private double prix;

    public Article(int id,String categorie ,String description, int quantite, double prix) {
        this.id = id;
        this.categorie = categorie;
        this.description = description;
        this.quantite = quantite;
        this.prix = prix;
    }
    //Getters
    public int getId() {
        return id;
    }
    public String getCategorie() {
        return categorie;
    }
    public String getDescription() {
        return description;
    }
    public int getQuantite() {
        return quantite;
    }
    public double getPrix() {
        return prix;
    }
    //Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String toString() {
        return id + ";" + categorie + ";" + description + ";" + quantite + ";" + prix;
    }
}
