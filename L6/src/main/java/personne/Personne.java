package personne;

public class Personne {
    //region private attributes
    private String nom;
    private String prenom;

    //endregion private attributes

    //region constructor
    public Personne(String nom, String prenom){
        this.nom = nom;
        this.prenom = prenom;
    }
    //endregion constructor

    //region methods
    @Override
    public String toString() {
        return this.prenom + " " + this.nom;
    }

    //endregion methods
}
