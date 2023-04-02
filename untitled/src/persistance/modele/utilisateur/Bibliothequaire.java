package persistance.modele.utilisateur;

public class Bibliothequaire extends AUtilisateur {

    public Bibliothequaire(int id, String login, String password) {
        super(id, login, password);
    }

    public Bibliothequaire(String login, String password) {
        super(login, password);
    }

    @Override
    public boolean isBibliothecaire() {
        return true;
    }

    @Override
    public String toString() {
        return this.getLogin();
    }

}