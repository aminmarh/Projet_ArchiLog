package persistance.modele.etatdoc;

import mediatheque.Document;
import mediatheque.EmpruntException;
import mediatheque.ReserverException;
import persistance.bdd.Documents;
import persistance.modele.document.EtatDocument;
import persistance.modele.utilisateur.Abonne;

public class Reserve implements EtatDocument {

    @Override
    public EtatDocument emprunter(Abonne u, Document d) {
        try {
            throw new EmpruntException("Document deja emprunte");
        } catch (EmpruntException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EtatDocument retour(Document d) {
        new Documents().retourner(d);
        return new Libre();
    }

    @Override
    public EtatDocument reserver(Abonne u, Document d) {
        try {
            throw new ReserverException("Document deja reserver");
        } catch (ReserverException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isEmprunte() {
        return true;
    }

    @Override
    public boolean isReserve() {
        return true;
    }
}
