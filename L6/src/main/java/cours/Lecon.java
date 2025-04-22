package cours;
import personne.Professeur;
public class Lecon {
    //region private attributes
    private String matiere;
    private int jourSemaine;
    private int periodeDebut;
    private int duree;

    private Professeur responsable;
    private String salle;

    //endregion private attributes

    //region constructor
    public Lecon(String matiere, int jourSemaine, int periodeDebut, int duree, String salle){
        this.matiere = matiere;
        this.jourSemaine = jourSemaine;
        this.periodeDebut = periodeDebut;
        this.duree = duree;
        this.salle = salle;
    }
    public Lecon(String matiere, int jourSemaine, int periodeDebut, int duree, String salle, Professeur responsable){
        this.matiere = matiere;
        this.jourSemaine = jourSemaine;
        this.periodeDebut = periodeDebut;
        this.duree = duree;
        this.salle = salle;
    }

    //endregion constructor

    //region accessors
    public void setResponsable(Professeur responsable) {
        this.responsable = responsable;
    }

    public int getJourSemaine() {
        return jourSemaine;
    }

    public int getPeriodeDebut() {
        return periodeDebut;
    }

    public int getDuree() {
        return duree;
    }

    public String getSalle() {
        return salle;
    }

    public String getMatiere() {
        return matiere;
    }
    //endregion accessors

    //region methods
    public String horaire(){
        return "Jour de la semaine: " + jourSemaine + " Période de début: " + periodeDebut + " Durée: " + duree;
    }
    //endregion methods
}
