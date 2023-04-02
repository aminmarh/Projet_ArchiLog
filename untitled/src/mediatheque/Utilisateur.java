package mediatheque;

public interface Utilisateur {
    public int getId();
    public String getLogin();
    public String getPassword();
    public boolean isBibliothecaire();
}
