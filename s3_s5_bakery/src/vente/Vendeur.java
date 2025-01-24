package vente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;
import genre.Genre;  // Import du genre

public class Vendeur {
    int idVendeur;
    String nomVendeur;
    Genre genre;  // Ajout d'un attribut Genre

    // Getters et setters
    public int getIdVendeur() {
        return idVendeur;
    }

    public void setIdVendeur(int idVendeur) {
        this.idVendeur = idVendeur;
    }

    public String getNomVendeur() {
        return nomVendeur;
    }

    public void setNomVendeur(String nomVendeur) {
        this.nomVendeur = nomVendeur;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    // Méthode pour créer un vendeur avec un genre associé
    public void create() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "INSERT INTO vendeur (nom_vendeur, id_genre) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.nomVendeur);
            preparedStatement.setInt(2, this.genre.getIdGenre());  // Associe le genre au vendeur
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    // Méthode pour récupérer un vendeur par son ID, avec le genre associé
    public static Vendeur getById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Vendeur vendeur = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "SELECT v.id_vendeur, v.nom_vendeur, v.id_genre, g.nom_genre FROM vendeur v " +
                         "JOIN genre g ON v.id_genre = g.id_genre WHERE v.id_vendeur = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                vendeur = new Vendeur();
                vendeur.setIdVendeur(resultSet.getInt("id_vendeur"));
                vendeur.setNomVendeur(resultSet.getString("nom_vendeur"));
                
                // Créer l'objet Genre et l'associer au vendeur
                Genre genre = new Genre();
                genre.setIdGenre(resultSet.getInt("id_genre"));
                genre.setNomGenre(resultSet.getString("nom_genre"));
                vendeur.setGenre(genre);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return vendeur;
    }

    // Méthode pour récupérer tous les vendeurs, avec leurs genres associés
    public static List<Vendeur> getAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Vendeur> vendeurs = new ArrayList<>();
        try {
            connection = Connexion.getConnexion();
            String sql = "SELECT v.id_vendeur, v.nom_vendeur, v.id_genre, g.nom_genre FROM vendeur v " +
                         "JOIN genre g ON v.id_genre = g.id_genre";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vendeur vendeur = new Vendeur();
                vendeur.setIdVendeur(resultSet.getInt("id_vendeur"));
                vendeur.setNomVendeur(resultSet.getString("nom_vendeur"));
                
                // Créer l'objet Genre et l'associer au vendeur
                Genre genre = new Genre();
                genre.setIdGenre(resultSet.getInt("id_genre"));
                genre.setNomGenre(resultSet.getString("nom_genre"));
                vendeur.setGenre(genre);
                
                vendeurs.add(vendeur);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return vendeurs;
    }

    // Méthode pour mettre à jour un vendeur avec son genre
    public void update() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "UPDATE vendeur SET nom_vendeur = ?, id_genre = ? WHERE id_vendeur = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.nomVendeur);
            preparedStatement.setInt(2, this.genre.getIdGenre());  // Met à jour le genre
            preparedStatement.setInt(3, this.idVendeur);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    // Méthode pour supprimer un vendeur
    public static void delete(int idVendeur) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "DELETE FROM vendeur WHERE id_vendeur = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idVendeur);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    // Exemple d'utilisation
    public static void main(String[] args) throws SQLException {
        Genre genre = new Genre();
        genre.setIdGenre(1);  // Supposez que le genre avec ID 1 existe dans la base de données
        
        Vendeur vendeur = new Vendeur();
        vendeur.setNomVendeur("Dupont");
        vendeur.setGenre(genre);  // Associer un genre au vendeur
        vendeur.create();  // Créer le vendeur avec son genre associé

        // Récupérer et afficher les vendeurs avec leurs genres
        List<Vendeur> vendeurs = Vendeur.getAll();
        for (Vendeur v : vendeurs) {
            System.out.println(v.getNomVendeur() + " - Genre: " + v.getGenre().getNomGenre());
        }
    }
}
