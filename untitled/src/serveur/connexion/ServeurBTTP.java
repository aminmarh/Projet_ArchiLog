package serveur.connexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurBTTP implements Runnable {
    int connexions;
    private int port;
    ServerSocket socketServeur;

    public ServeurBTTP(int port) {
        this.connexions = 0;
        this.port = port;
        System.out.println("Le serveur d�marre sur le port : " + this.port);
        try {
            socketServeur = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket serveur = socketServeur.accept();
                System.out.println("Connexion " + ++connexions + " ouverte par " + serveur.getInetAddress());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void finalize() {
        try {
            socketServeur.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
