package mediatheque;

import persistance.modele.utilisateur.Abonne;

public interface Document {
    int numero();

    Abonne emprunteur() ; // Abonné qui a emprunté ce document

    Abonne reserveur() ; // Abonné qui a réservé ce document

    void reservationPour(Abonne ab);

    void empruntPar(Abonne ab);

    void retour();
}
