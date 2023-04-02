package persistance.modele.utilisateur;

public class Abonne extends AUtilisateur {


    public Abonne(int id, String login, String password) {
        super(id, login, password);
    }

    public Abonne(String login, String password) {
        super(login, password);
    }

    @Override
    public boolean isBibliothecaire() {
        return false;
    }

    @Override
    public String toString() {
        return this.getLogin();
    }



}