package serveur.service;

import java.io.IOException;
import java.net.ServerSocket;

public class ServeurReservation {
    private ServerSocket listen_socket;

    // Cree un serveur TCP - objet de la classe ServerSocket
    public ServeurReservation(int port) throws IOException {
        listen_socket = new ServerSocket(port);
    }

    // Le serveur ecoute et accepte les connexions.
    // pour chaque connexion, il cree un ServiceInversion,
    // qui va la traiter, et le lance
    public void run() {
        try {
            System.err.println("Lancement du serveur au port " + this.listen_socket.getLocalPort());
            while (true)
                new Thread(new ServiceReservation().start();
        } catch (IOException e) {
            try {
                this.listen_socket.close();
            } catch (IOException e1) {
            }
            System.err.println("Arr�t du serveur au port " + this.listen_socket.getLocalPort());
        }
    }

    // restituer les ressources --> finalize
    protected void finalize() throws Throwable {
        try {
            this.listen_socket.close();
        } catch (IOException e1) {
        }
    }
}
