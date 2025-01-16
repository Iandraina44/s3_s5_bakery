package recette;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connexion.Connexion;

public class Recommandation {
    private int idRecommandation;
    private int annee;
    private int mois;  // Updated to "mois" as per SQL schema
    private Recette recette;

    // Constructors
    public Recommandation() {
    }

    public Recommandation(int idRecommandation, int annee, int mois, Recette recette) {
        this.idRecommandation = idRecommandation;
        this.annee = annee;
        this.mois = mois;
        this.recette = recette;
    }

    // Getters and Setters
    public int getIdRecommandation() {
        return idRecommandation;
    }

    public void setIdRecommandation(int idRecommandation) {
        this.idRecommandation = idRecommandation;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public Recette getRecette() {
        return recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    // CRUD Operations

    // Create
    public static void create(Connection connection, Recommandation recommandation) throws SQLException {
        String sql = "INSERT INTO recommandation (annee, mois, id_recette) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, recommandation.getAnnee());
            statement.setInt(2, recommandation.getMois());
            statement.setInt(3, recommandation.getRecette().getIdRecette());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    recommandation.setIdRecommandation(rs.getInt(1));
                }
            }
        }
    }

    // Read
    public static Recommandation read(Connection connection, int idRecommandation) throws SQLException {
        String sql = "SELECT r.*, rec.* FROM recommandation r JOIN recette rec ON r.id_recette = rec.id_recette WHERE r.id_recommandation = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRecommandation);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Recette recette = Recette.read(connection, rs.getInt("id_recette"));
                    return new Recommandation(
                        rs.getInt("id_recommandation"),
                        rs.getInt("annee"),
                        rs.getInt("mois"),  // Updated to "mois"
                        recette
                    );
                }
            }
        }
        return null;
    }

    // Read All
    public static List<Recommandation> readAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM recommandation ";
        List<Recommandation> recommandations = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Recette recette = Recette.read(connection, rs.getInt("id_recette"));
                Recommandation recommandation = new Recommandation(
                    rs.getInt("id_recommandation"),
                    rs.getInt("annee"),
                    rs.getInt("mois"),  // Updated to "mois"
                    recette
                );
                recommandations.add(recommandation);
            }
        }
        return recommandations;
    }

    // Get by Mois and Year
    public static List<Recommandation> getByMoisandYear(Connection connection, int annee, int mois) throws SQLException {
        String sql = "SELECT * FROM recommandation WHERE annee = ? AND mois = ?";
        List<Recommandation> recommandations = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, annee);
            statement.setInt(2, mois);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Recette recette = Recette.read(connection, rs.getInt("id_recette"));
                    Recommandation recommandation = new Recommandation(
                        rs.getInt("id_recommandation"),
                        rs.getInt("annee"),
                        rs.getInt("mois"),  // Updated to "mois"
                        recette
                    );
                    recommandations.add(recommandation);
                }
            }
        }
        return recommandations;
    }

    // Update
    public static void update(Connection connection, Recommandation recommandation) throws SQLException {
        String sql = "UPDATE recommandation SET annee = ?, mois = ?, id_recette = ? WHERE id_recommandation = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, recommandation.getAnnee());
            statement.setInt(2, recommandation.getMois());
            statement.setInt(3, recommandation.getRecette().getIdRecette());
            statement.setInt(4, recommandation.getIdRecommandation());
            statement.executeUpdate();
        }
    }

    // Delete
    public static void delete(Connection connection, int idRecommandation) throws SQLException {
        String sql = "DELETE FROM recommandation WHERE id_recommandation = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRecommandation);
            statement.executeUpdate();
        }
    }

    public static void main(String[] args) throws SQLException {
        
        List<Recommandation> recommandations = Recommandation.getByMoisandYear(Connexion.getConnexion(), 2025,1 );
        for (Recommandation recommandation : recommandations) {
            System.out.println(recommandation.getIdRecommandation() + " " + recommandation.getAnnee() + " " + recommandation.getMois() + " " + recommandation.getRecette().getIdRecette());
        }
    }
}
