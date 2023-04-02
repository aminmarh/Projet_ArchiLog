package persistance.modele.utilisateur;

import mediatheque.Utilisateur;

public abstract class AUtilisateur implements Utilisateur {
    private int id;
    private String login;
    private String password;



    public AUtilisateur(int id, String login, String password) {
        super();
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public AUtilisateur(String login, String pseudo) {
        super();
        this.login = login;
        this.password = pseudo;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }



}