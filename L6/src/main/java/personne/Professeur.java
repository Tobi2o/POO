package personne;
import java.util.*;
import cours.Lecon;
public class Professeur extends Personne{
    //region private attributes
    private String abreviation;
    private ArrayList<Lecon> lecons;

    //endregion private attributes

    //region constructor
    public Professeur(String nom, String prenom, String abreviation){
        super(nom, prenom);
        this.abreviation = abreviation.toUpperCase();
    }
    //endregion constructor

    //region accessors

    public void setLecons(ArrayList<Lecon> lecons) {
        this.lecons = lecons;
    }

    //endregion accessors

    //region methods

    @Override
    public String toString() {
        return super.toString() + " " + this.abreviation;
    }
    //endregion methods
}
