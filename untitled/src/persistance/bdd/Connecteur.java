package persistance.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connecteur {
    /**
     * Connexion � la base de donn�e au chargement de la classes
     */
    static {
        Connecteur.connexionDB();
    }

    /**
     * Connexion � la pase de donn�e
     */
    private static Connection c;

    /**
     * Chargement du driver MySQL et connexion � la base
     */
    private static void connexionDB() {
        // Chargement du connecteur
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.err.println(e.getMessage() + "\n Erreur de chargement du driver JDBC");
        }

        // Initialisation de la connexion
        try {
            c = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/base",
                    "root", "my-secret-pw");
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "\n Erreur de connexion � la base");
        }

    }

    /**
     * Getter pour la connexion � la base de donn�e
     *
     * @return la connexion � la base de donn�e
     */
    public static Connection getConnexion() {
        return c;
    }
}
