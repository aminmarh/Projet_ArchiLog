package persistance.modele.document;

import mediatheque.Document;
import mediatheque.EmpruntException;
import persistance.modele.utilisateur.Abonne;

public interface EtatDocument {
    EtatDocument emprunter(Abonne u, Document d);

    EtatDocument retour(Document d);

    EtatDocument reserver(Abonne u, Document d);

    boolean isEmprunte();

    boolean isReserve();

}