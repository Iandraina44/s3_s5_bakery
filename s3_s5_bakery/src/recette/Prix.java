package recette;

import java.sql.*;
import connexion.Connexion;
import java.util.*;

public class Prix {
    private int idPrix;
    private Timestamp dateChangementPrix;
    private double nouveauPrix;
    private Recette recette; // Remplace l'idRecette par un objet Recette

    // Constructeurs
    public Prix() {}

    public Prix(int idPrix, Timestamp dateChangementPrix, double nouveauPrix, Recette recette) {
        this.idPrix = idPrix;
        this.dateChangementPrix = dateChangementPrix;
        this.nouveauPrix = nouveauPrix;
        this.recette = recette;
    }

    // Getters et Setters
    public int getIdPrix() {
        return idPrix;
    }

    public void setIdPrix(int idPrix) {
        this.idPrix = idPrix;
    }

    public Timestamp getDateChangementPrix() {
        return dateChangementPrix;
    }

    public void setDateChangementPrix(Timestamp dateChangementPrix) {
        this.dateChangementPrix = dateChangementPrix;
    }

    public double getNouveauPrix() {
        return nouveauPrix;
    }

    public void setNouveauPrix(double nouveauPrix) {
        this.nouveauPrix = nouveauPrix;
    }

    public Recette getRecette() {
        return recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    // CRUD Operations

    // Create
    public static void create(Connection connection, Prix prix) throws SQLException {
        String sql = "INSERT INTO prix (date_changement_prix, nouveau_prix, id_recette) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, prix.getDateChangementPrix());
            statement.setDouble(2, prix.getNouveauPrix());
            statement.setInt(3, prix.getRecette().getIdRecette()); // Utilise l'ID de l'objet Recette
            statement.executeUpdate();
        }
    }

    // Read by Recette and Date
    public static Prix readByRecetteAndDate(Connection connection, Recette recette, Timestamp date) throws SQLException {
        String sql = "SELECT * FROM prix WHERE id_recette = ? AND date_changement_prix <= ? ORDER BY date_changement_prix DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, recette.getIdRecette()); // Utilise l'ID de l'objet Recette
            statement.setTimestamp(2, date);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Prix(
                        rs.getInt("id_prix"),
                        rs.getTimestamp("date_changement_prix"),
                        rs.getDouble("nouveau_prix"),
                        recette // Utilise l'objet Recette passé en paramètre
                    );
                }
            }
        }
        return null;
    }

    // Read All by Recette
    public static List<Prix> readAllByRecette(Connection connection, Recette recette) throws SQLException {
        String sql = "SELECT * FROM prix WHERE id_recette = ? ORDER BY date_changement_prix DESC";
        List<Prix> prixList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, recette.getIdRecette()); // Utilise l'ID de l'objet Recette
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    prixList.add(new Prix(
                        rs.getInt("id_prix"),
                        rs.getTimestamp("date_changement_prix"),
                        rs.getDouble("nouveau_prix"),
                        recette // Utilise l'objet Recette passé en paramètre
                    ));
                }
            }
        }
        return prixList;
    }

    // Update
    public static void update(Connection connection, Prix prix) throws SQLException {
        String sql = "UPDATE prix SET date_changement_prix = ?, nouveau_prix = ?, id_recette = ? WHERE id_prix = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, prix.getDateChangementPrix());
            statement.setDouble(2, prix.getNouveauPrix());
            statement.setInt(3, prix.getRecette().getIdRecette()); // Utilise l'ID de l'objet Recette
            statement.setInt(4, prix.getIdPrix());
            statement.executeUpdate();
        }
    }
    

    // Delete
    public static void delete(Connection connection, int idPrix) throws SQLException {
        String sql = "DELETE FROM prix WHERE id_prix = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idPrix);
            statement.executeUpdate();
        }
    }
}