package personne;

import cours.Groupe;

public class Etudiant extends Personne{
    //region private attributes
    private int matricule;
    private Groupe groupe;

    //endregion private attributes

    //region constructor
    public Etudiant(String nom, String prenom, int matricule){
        super(nom, prenom);
        this.matricule = matricule;
    }
    //endregion constructor

    //region accessors

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    //endregion accessors

    //region methods


    @Override
    public String toString() {
        return "Etud." + super.toString() +
                " (#" + this.matricule + ") - " + groupe.nom();
    }
    //endregion methods
}
