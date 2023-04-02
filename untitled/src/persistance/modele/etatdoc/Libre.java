package persistance.modele.etatdoc;

import mediatheque.Document;
import persistance.bdd.Documents;
import persistance.modele.document.EtatDocument;
import persistance.modele.utilisateur.Abonne;

public class Libre implements EtatDocument {

    @Override
    public EtatDocument emprunter(Abonne u, Document d) {
        new Documents().emprunter(d,u);
        return new Emprunte();
    }

    @Override
    public EtatDocument retour(Document d) {
        return new Libre();
    }

    @Override
    public EtatDocument reserver(Abonne u, Document d) {
        synchronized(this){
            new Documents().reserver(d,u);
        }
        return new Reserve();
    }

    @Override
    public boolean isEmprunte() {
        return false;
    }

    @Override
    public boolean isReserve() {
        return false;
    }
}
