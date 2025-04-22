import cours.*;
import personne.*;
public class Ecole
{
    public static void main( String[] args )
    {
        Professeur pdo = new Professeur("Donini", "Pier", "PDO");
        Professeur dre = new Professeur("Rossier", "Daniel", "DRE");

        Lecon poo1 = new Lecon("POO", 3, 13, 1, "H02", pdo);
        Lecon poo2 = new Lecon("POO", 4, 13, 1, "H02", pdo);
        Lecon poo3 = new Lecon("POO", 5, 14, 1, "H02", pdo);
        Lecon sye  = new Lecon("SYE", 1, 8, 1, "G01", dre);
        Lecon tic  = new Lecon("TIC", 2, 16, 1, "F06"); // No professor for TIC

        Etudiant john = new Etudiant("Lennon", "John", 1234);
        Etudiant paul = new Etudiant("McCartney", "Paul", 2341);
        Etudiant ringo = new Etudiant("Starr", "Ringo", 3241);
        Etudiant george = new Etudiant("Harrison", "George", 4321);
        Etudiant roger = new Etudiant("Waters", "Roger", 1324);
        Etudiant david = new Etudiant("Gilmour", "David", 4312);

        Groupe il61 = new Groupe(1, "IL6", 1);
        il61.ajouterEtudiant(john);
        il61.ajouterEtudiant(paul);
        il61.ajouterEtudiant(ringo);
        il61.ajouterEtudiant(george);
        il61.ajouterLecon(poo1);
        il61.ajouterLecon(poo2);
        il61.ajouterLecon(poo3);
        il61.ajouterLecon(sye);
        il61.ajouterLecon(tic);

        Groupe si61 = new Groupe(1, "SI6", 1);
        si61.ajouterEtudiant(roger);
        si61.ajouterEtudiant(david);
        si61.ajouterLecon(poo1);
        si61.ajouterLecon(poo2);
        si61.ajouterLecon(poo3);

    }
}
