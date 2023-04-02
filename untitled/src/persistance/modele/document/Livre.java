package persistance.modele.document;

import persistance.bdd.Documents;
import persistance.modele.utilisateur.Abonne;
import java.time.LocalDate;

public class Livre extends ADocument {
    private String auteur;

    public Livre(String titre, LocalDate date, String auteur, EtatDocument etat) {
        super(titre, date, etat);
        this.auteur = auteur;
    }

    public Livre(int numero,String titre, LocalDate date, String auteur, EtatDocument etat) {
        super(numero,titre, date, etat);
        this.auteur = auteur;
    }

    public String getAuteur() {
        return this.auteur;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" écrit par ");
        sb.append(this.getAuteur());
        return sb.toString();
    }

    @Override
    public int numero() {
        return super.getNumero();
    }

    @Override
    public Abonne emprunteur() {
        return (Abonne) new Documents().getEmprunteur(this);
    }

    @Override
    public Abonne reserveur() {
        return (Abonne) new Documents().getReserveur(this);
    }
}
