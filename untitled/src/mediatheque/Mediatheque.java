package mediatheque;

import java.util.Collections;
import java.util.List;
import persistance.bdd.Utilisateurs;
import persistance.bdd.Documents;

public class Mediatheque {
    private List<Utilisateur> utilisateurs;
    private List<Document> catalogue;

    public Mediatheque(){
        this.utilisateurs = Collections.synchronizedList(Utilisateurs.getAll());
        this.catalogue = Collections.synchronizedList(Documents.getAll());
    }
}
