package persistance.bdd;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import mediatheque.Document;
import mediatheque.Utilisateur;
import persistance.modele.document.ADocument;
import persistance.modele.document.CD;
import persistance.modele.document.DVD;
import persistance.modele.document.EtatDocument;
import persistance.modele.document.Livre;
import persistance.modele.etatdoc.Emprunte;
import persistance.modele.etatdoc.Libre;
import persistance.modele.utilisateur.AUtilisateur;

public class Documents extends DAO<Document> {

    @Override
    public void insert(Document tuple) {
        PreparedStatement requete = null;
        ResultSet resultat = null;
        ADocument doc = (ADocument) tuple;
        try {
            this.getConnexion().setAutoCommit(false);
            requete = this.getConnexion().prepareStatement(
                    "INSERT INTO document(titreDocument,dateDocument,emprunte,type,idEmprunteur) VALUES(?,?,?,?,null)",
                    Statement.RETURN_GENERATED_KEYS);
            requete.setString(1, doc.getTitre());
            requete.setDate(2, Date.valueOf(doc.getDate()));
            requete.setBoolean(3, doc.isEmprunte());
            requete.setString(4, doc.getClass().getSimpleName());

            requete.executeUpdate();

            resultat = requete.getGeneratedKeys();
            resultat.next();
            int id = (int) resultat.getLong(1);

            if (tuple instanceof Livre) {
                Livre l = (Livre) doc;

                requete = this.getConnexion().prepareStatement("INSERT INTO livre(idLivre,	auteur) VALUES(?,?)");
                requete.setInt(1, id);
                requete.setString(2, l.getAuteur());
            } else if (tuple instanceof DVD) {
                DVD d = (DVD) doc;
                requete = this.getConnexion()
                        .prepareStatement("INSERT INTO DVD(idDVD, realisateur, qualite) VALUES(?,?,?)");
                requete.setInt(1, id);
                requete.setString(2, d.getRealisateur());
                requete.setString(3, d.getQualite().toString());
            } else if (tuple instanceof CD) {
                CD c = (CD) doc;

                requete = this.getConnexion().prepareStatement("INSERT INTO CD(idCD, genre,artiste) VALUES(?,?,?)");
                requete.setInt(1, id);
                requete.setString(2, c.getGenre());
                requete.setString(3, c.getArtiste());
            }

            requete.execute();
            this.getConnexion().commit();
            this.getConnexion().setAutoCommit(true);
        } catch (Exception e) {
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
    }

    @Override
    public List<Document> getAll() {
        List<Document> documents = new LinkedList<Document>();

        ResultSet resultat = null;
        Statement requete = null;

        try {
            requete = this.getConnexion().createStatement();
            resultat = requete.executeQuery("SELECT * FROM document d INNER JOIN livre c ON d.IdDocument=c.IdLivre");

            while (resultat.next()) {
                int idDocument = resultat.getInt("idDocument");
                String titreDocument = resultat.getString("titreDocument");
                LocalDate dateDocument = resultat.getDate("dateDocument").toLocalDate();
                boolean emprunte = resultat.getBoolean("emprunte");
                EtatDocument etat = (emprunte ? new Emprunte() : new Libre());

                String auteur = resultat.getString("auteur");

                Livre l = new Livre(idDocument, titreDocument, dateDocument, auteur, etat);
                documents.add(l);
            }

            requete = this.getConnexion().createStatement();
            resultat = requete.executeQuery("SELECT * FROM document d INNER JOIN DVD c ON d.IdDocument=c.IdDVD");

            while (resultat.next()) {
                int idDocument = resultat.getInt("idDocument");
                String titreDocument = resultat.getString("titreDocument");
                LocalDate dateDocument = resultat.getDate("dateDocument").toLocalDate();
                boolean emprunte = resultat.getBoolean("emprunte");
                EtatDocument etat = (emprunte ? new Emprunte() : new Libre());

                String realisateur = resultat.getString("realisateur");
                String qualite = resultat.getString("qualite");

                DVD d = new DVD(idDocument, titreDocument, dateDocument, realisateur, qualite, etat);
                documents.add(d);
            }

            requete = this.getConnexion().createStatement();
            resultat = requete.executeQuery("SELECT * FROM document d INNER JOIN CD c ON d.IdDocument=c.IdCD");

            while (resultat.next()) {
                int idDocument = resultat.getInt("idDocument");
                String titreDocument = resultat.getString("titreDocument");
                LocalDate dateDocument = resultat.getDate("dateDocument").toLocalDate();
                boolean emprunte = resultat.getBoolean("emprunte");
                EtatDocument etat = (emprunte ? new Emprunte() : new Libre());

                String artiste = resultat.getString("artiste");
                String genre = resultat.getString("genre");

                CD c = new CD(idDocument, titreDocument, dateDocument, genre, artiste, etat);
                documents.add(c);
            }

        } catch (Exception e) {
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

        return documents;
    }

    @Override
    public Document getFromId(int id) {
        ResultSet resultat = null;
        PreparedStatement requete = null;

        try {

            requete = this.getConnexion().prepareStatement("SELECT * FROM document WHERE idDocument=?");
            requete.setInt(1, id);
            resultat = requete.executeQuery();

            resultat.next();
            int idDocument = resultat.getInt("idDocument");
            String titreDocument = resultat.getString("titreDocument");
            LocalDate dateDocument = resultat.getDate("dateDocument").toLocalDate();
            boolean emprunte = resultat.getBoolean("emprunte");
            EtatDocument etat = (emprunte ? new Emprunte() : new Libre());

            String type = resultat.getString("type");
            switch (type) {
                case "Livre":
                    requete = this.getConnexion().prepareStatement("SELECT * FROM livre WHERE idLivre=?");
                    requete.setInt(1, id);
                    resultat = requete.executeQuery();
                    resultat.next();
                    String auteur = resultat.getString("auteur");


                    try {
                        if (resultat != null)
                            resultat.close();
                        if (requete != null)
                            requete.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    return new Livre(idDocument, titreDocument, dateDocument, auteur, etat);
                case "DVD":
                    requete = this.getConnexion().prepareStatement("SELECT * FROM DVD WHERE idDVD=?");
                    requete.setInt(1, id);
                    resultat = requete.executeQuery();
                    resultat.next();
                    String realisateur = resultat.getString("realisateur");
                    String qualite = resultat.getString("qualite");

                    try {
                        if (resultat != null)
                            resultat.close();
                        if (requete != null)
                            requete.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return new DVD(idDocument, titreDocument, dateDocument, realisateur, qualite, etat);
                case "CD":
                    requete = this.getConnexion().prepareStatement("SELECT * FROM CD WHERE idCD=?");
                    requete.setInt(1, id);
                    resultat = requete.executeQuery();
                    resultat.next();
                    String genre = resultat.getString("genre");
                    String artiste = resultat.getString("artiste");

                    try {
                        if (resultat != null)
                            resultat.close();
                        if (requete != null)
                            requete.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return new CD(idDocument, titreDocument, dateDocument, genre, artiste, etat);
            }

        } catch (Exception e) {
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

        return null;
    }

    @Override
    public void delete(int id) {
        PreparedStatement requete = null;

        try {
            requete = this.getConnexion().prepareStatement("DELETE FROM document WHERE idDocument=?");
            requete.setInt(1, id);

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

    public void emprunter(Document d, Utilisateur u) {
        PreparedStatement requete = null;
        ADocument doc = (ADocument) d;
        AUtilisateur emprunteur = (AUtilisateur) u;

        try {

            requete = this.getConnexion()
                    .prepareStatement("UPDATE document SET emprunte=?,idresev_emprunt=? WHERE idDocument=?");
            requete.setBoolean(1, true);
            requete.setInt(2, emprunteur.getId());
            requete.setInt(3, doc.getNumero());

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

    public void reserver(Document d, Utilisateur u) {
        PreparedStatement requete = null;
        ADocument doc = (ADocument) d;
        AUtilisateur emprunteur = (AUtilisateur) u;

        try {

            requete = this.getConnexion()
                    .prepareStatement("UPDATE document SET reserver=?,idresev_emprunt=? WHERE idDocument=?");
            requete.setBoolean(1, true);
            requete.setInt(2, emprunteur.getId());
            requete.setInt(3, doc.getNumero());

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

    public void retourner(Document d) {
        PreparedStatement requete = null;

        ADocument doc = (ADocument) d;

        try {

            requete = this.getConnexion().prepareStatement("UPDATE document SET emprunte=? WHERE idDocument=?");
            requete.setBoolean(1, false);
            requete.setInt(2, doc.getNumero());

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

    public Utilisateur getEmprunteur(Document document) {
        PreparedStatement requete = null;
        ResultSet resultat = null;
        Utilisateur utilisateur = null;
        try {
            this.getConnexion().setAutoCommit(false);
            requete = this.getConnexion().prepareStatement("SELECT * FROM document WHERE idDocument = ?");
            requete.setInt(1, document.numero());
            resultat = requete.executeQuery();
            if (resultat.next()) {
                int idEmprunteur = resultat.getInt("idresev_emprunt");
                utilisateur = new Utilisateurs().getFromId(idEmprunteur);
            }
            this.getConnexion().commit();
            this.getConnexion().setAutoCommit(true);
        } catch (Exception e) {
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
        return utilisateur;
    }


    public Utilisateur getReserveur(Document document) {
        PreparedStatement requete = null;
        ResultSet resultat = null;
        Utilisateur utilisateur = null;
        try {
            this.getConnexion().setAutoCommit(false);
            requete = this.getConnexion().prepareStatement("SELECT * FROM document WHERE idDocument = ?");
            requete.setInt(1, document.numero());
            resultat = requete.executeQuery();
            if (resultat.next()) {
                int idReserveur = resultat.getInt("idresev_emprunt");
                utilisateur = new Utilisateurs().getFromId(idReserveur);
            }
            this.getConnexion().commit();
            this.getConnexion().setAutoCommit(true);
        } catch (Exception e) {
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
        return utilisateur;
    }


}