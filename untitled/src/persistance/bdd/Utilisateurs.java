package persistance.bdd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import mediatheque.Utilisateur;
import persistance.modele.utilisateur.AUtilisateur;
import persistance.modele.utilisateur.FactoryUtilisateur;

public class Utilisateurs extends DAO<Utilisateur> {

    public Utilisateurs() {
        super();
    }

    @Override
    public void insert(Utilisateur tuple) {
        AUtilisateur u = (AUtilisateur) tuple;
        PreparedStatement requete = null;
        try {
            requete = this.getConnexion()
                    .prepareStatement("INSERT INTO utilisateur(login,password,isBib) VALUES(?,?,?)");
            requete.setString(1, u.getLogin());
            requete.setString(2, u.getPassword());
            requete.setBoolean(3, u.isBibliothecaire());
            requete.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (requete != null)
                    requete.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Utilisateur> getAll() {

        // Cr�ation de la liste qui contient les utilisateurs
        List<Utilisateur> utilisateurs = new LinkedList<Utilisateur>();

        ResultSet resultat = null;
        Statement requete = null;

        try {
            // Initialisation de la requ�te SQL
            requete = this.getConnexion().createStatement();

            // Ex�cution de la requ�te SQL
            resultat = requete.executeQuery("SELECT * FROM utilisateur");

            // R�cup�ration des donn�es
            while (resultat.next()) {
                Utilisateur u = FactoryUtilisateur.creerUtilisateur(resultat.getBoolean("isBib"),
                        resultat.getInt("IdUtilisateur"), resultat.getString("login"), resultat.getString("password"));
                utilisateurs.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultat != null)
                    resultat.close();
                if (requete != null)
                    requete.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return utilisateurs;
    }

    @Override
    public Utilisateur getFromId(int id) {

        Utilisateur u = null;

        ResultSet resultat = null;
        PreparedStatement requete = null;

        try {
            // Initialisation de la requ�te SQL
            requete = this.getConnexion().prepareStatement("SELECT * FROM utilisateur WHERE IdUtilisateur=?");
            requete.setInt(1, id);
            // Ex�cution de la requ�te SQL
            resultat = requete.executeQuery();

            // R�cup�ration des donn�es
            if (resultat.next()) {
                u = FactoryUtilisateur.creerUtilisateur(resultat.getBoolean("isBib"), resultat.getInt("IdUtilisateur"),
                        resultat.getString("login"), resultat.getString("password"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (resultat != null)
                    resultat.close();
                if (requete != null)
                    requete.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return u;
    }

    @Override
    public void delete(int id) {
        PreparedStatement requete = null;
        try {
            // Initialisation de la requ�te SQL
            requete = this.getConnexion().prepareStatement("DELETE FROM utilisateur WHERE IdUtilisateur=?");
            requete.setInt(1, id);
            // Ex�cution de la requ�te SQL
            requete.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (requete != null)
                    requete.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Utilisateur connect(String login, String plainPassword){
        ResultSet resultat = null;
        PreparedStatement requete = null;
        boolean pwdCheck = false;
        Utilisateur u = null;

        try {
            requete = this.getConnexion().prepareStatement("SELECT * FROM utilisateur WHERE login=?");
            requete.setString(1, login);
            resultat = requete.executeQuery();

            if (resultat.next()) {
                String passwordHashDB = resultat.getString("password");
                if (passwordHashDB == plainPassword) {
                    pwdCheck = true;
                }

                if (pwdCheck) {
                    System.out.println("Mot de passe valide");

                    u = FactoryUtilisateur.creerUtilisateur(resultat.getBoolean("isBib"),
                            resultat.getInt("IdUtilisateur"), resultat.getString("login"),
                            resultat.getString("password"));

                } else {
                    System.out.println("Mot de passe invalide");
                }

            } else {
                System.out.println("Login invalide");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultat != null)
                    resultat.close();
                if (requete != null)
                    requete.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return u;
    }

    public int getId(Utilisateur u) {
        ResultSet resultat = null;
        PreparedStatement requete = null;
        AUtilisateur tuple = (AUtilisateur) u;
        int id = 0;
        try {
            // Initialisation de la requ�te SQL
            requete = this.getConnexion().prepareStatement("SELECT * FROM projet WHERE login=?");
            requete.setString(1, tuple.getLogin());
            // Ex�cution de la requ�te SQL
            resultat = requete.executeQuery();

            // R�cup�ration des donn�es
            if (resultat.next()) {
                id = resultat.getInt("IdUtilisateur");
            }

        } catch (Exception e) {
        } finally {
            try {
                if (resultat != null)
                    resultat.close();
                if (requete != null)
                    requete.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }



}