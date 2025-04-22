package cours;

import personne.Etudiant;
import java.util.*;
public class Groupe {
    //region private attributes
    private int numero;
    private String orientation;
    private int trimestre;
    private ArrayList<Etudiant> etudiants;
    private ArrayList<Lecon> lecons;
    //endregion private attributes

    //region constructor
    public Groupe(int numero, String orientation, int trimestre){
        this.numero = numero;
        this.orientation = orientation;
        this.trimestre = trimestre;
    }

    //endregion constructor

    //region accessors


    //endregion accessors

    //region methods

    public String horaire() {
        return "";
    }
    public void ajouterLecon(Lecon lecon) {
        lecons.add(lecon);
    }
    public void ajouterEtudiant(Etudiant etudiant) {
        etudiants.add(etudiant);
        etudiant.setGroupe(this);
    }
    public void definirLecons(){

    }

    public int nombreEtudiants(){
        return etudiants.size();
    }

    public String nom(){
        return orientation + "-" + numero;
    }

    //endregion methods
}
