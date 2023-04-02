package serveur.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


import java.sql.*;

public class ServiceRetour implements Runnable {

    private int portRetour = 0;
    private Connection conn = null;
    private final Statement stmt;
    private final PreparedStatement selectDVD, updateDVD, selectAbonne;

    public ServiceRetour() throws SQLException {
        this.portRetour = portRetour;
        this.conn = conn;
        this.stmt = conn.createStatement();

        // Préparation des requêtes SQL précompilées
        this.selectDVD = conn.prepareStatement("SELECT * FROM dvd WHERE num_dvd = ?");
        this.updateDVD = conn.prepareStatement("UPDATE dvd SET disponible = true, num_abonne = null WHERE num_dvd = ?");
        this.selectAbonne = conn.prepareStatement("SELECT * FROM abonne WHERE num_abonne = ?");
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(portRetour)) {
            System.out.println("Service Retour démarré sur le port " + portRetour);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Récupération du numéro de DVD envoyé par le client
                            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            int numDVD = Integer.parseInt(in.readLine());

                            // Vérification si le DVD existe et est emprunté
                            ResultSet dvdResult = selectDVD.executeQuery();
                            if (!dvdResult.next()) {
                                sendRefus(clientSocket, "DVD inexistant");
                                return;
                            } else if (dvdResult.getBoolean("disponible")) {
                                sendRefus(clientSocket, "DVD déjà retourné");
                                return;
                            }

                            // Récupération des informations de l'abonné qui a emprunté le DVD
                            int numAbonne = dvdResult.getInt("num_abonne");
                            ResultSet abonneResult = selectAbonne.executeQuery();
                            if (!abonneResult.next()) {
                                sendRefus(clientSocket, "Abonné inexistant");
                                return;
                            }

                            // Mise à jour du DVD dans la base de données
                            updateDVD.setInt(1, numDVD);
                            updateDVD.executeUpdate();

                            // Envoi de la confirmation de retour au client
                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                            out.println("DVD retourné avec succès");

                            System.out.println("DVD " + numDVD + " retourné par l'abonné " + abonneResult.getString("nom"));
                        } catch (IOException | SQLException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                clientSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendRefus(Socket clientSocket, String message) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println("Refus : " + message);
    }
}
