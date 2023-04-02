package persistance.modele.utilisateur;

import mediatheque.Utilisateur;

public class FactoryUtilisateur {
    public static Utilisateur creerUtilisateur(boolean bib,int id, String login, String password) {
        return bib?new Bibliothequaire(id,login, password):new Abonne(id,login, password);
    }
}