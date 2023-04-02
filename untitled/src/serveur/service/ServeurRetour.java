package serveur.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

public class ServeurRetour {
    private ServerSocket listen_socket;

    // Cree un serveur TCP - objet de la classe ServerSocket
    public ServeurRetour(int port) throws IOException {
        listen_socket = new ServerSocket(port);
    }

    // Le serveur ecoute et accepte les connexions.
    // pour chaque connexion, il cree un ServiceInversion,
    // qui va la traiter, et le lance
    public void run() {
        try {
            System.err.println("Lancement du serveur au port " + this.listen_socket.getLocalPort());
            while (true)
                new Thread(new ServiceRetour().start();
        } catch (IOException e) {
            try {
                this.listen_socket.close();
            } catch (IOException e1) {
            }
            System.err.println("Arr�t du serveur au port " + this.listen_socket.getLocalPort());
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
