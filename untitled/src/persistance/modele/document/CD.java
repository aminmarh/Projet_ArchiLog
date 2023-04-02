package persistance.modele.document;

import persistance.bdd.Documents;
import persistance.modele.utilisateur.Abonne;
import java.time.LocalDate;

public class CD extends ADocument {
    private String genre;
    private String artiste;

    public CD(String titre, LocalDate date, String genre, String artiste,EtatDocument etat) {
        super(titre, date, etat);
        this.genre = genre;
        this.artiste = artiste;
    }

    public CD(int numero,String titre, LocalDate date, String genre, String artiste, EtatDocument etat) {
        super(numero,titre, date, etat);
        this.genre = genre;
        this.artiste = artiste;
    }

    public String getGenre() {
        return genre;
    }

    public String getArtiste() {
        return artiste;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" chanté par ");
        sb.append(this.getArtiste());
        sb.append(" (");
        sb.append(this.getGenre());
        sb.append(")");

        return sb.toString();
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
