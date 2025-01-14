package recette;

import ingredient.Ingredient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;

public class RecetteDetail {
    private int idRecetteDetail;
    private double quantite;
    private boolean etat;
    private int idRecette;
    private Ingredient ingredient;

    // Constructors
    public RecetteDetail() {
    }

    public RecetteDetail(int idRecetteDetail, double quantite, boolean etat, int idRecette, Ingredient ingredient) {
        this.idRecetteDetail = idRecetteDetail;
        this.quantite = quantite;
        this.etat = etat;
        this.idRecette = idRecette;
        this.ingredient = ingredient;
    }

    // Getters and Setters
    public int getIdRecetteDetail() {
        return idRecetteDetail;
    }

    public void setIdRecetteDetail(int idRecetteDetail) {
        this.idRecetteDetail = idRecetteDetail;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public int getIdRecette() {
        return idRecette;
    }

    public void setIdRecette(int idRecette) {
        this.idRecette = idRecette;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    // CRUD Methods

    // Create
    public static void create(Connection connection, RecetteDetail recetteDetail) throws SQLException {
        String sql = "INSERT INTO recette_detail (quantite, etat, id_recette, id_ingredient) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, recetteDetail.getQuantite());
            statement.setBoolean(2, true); // Default etat = true for new records
            statement.setInt(3, recetteDetail.getIdRecette());
            statement.setInt(4, recetteDetail.getIngredient().getIdIngredient());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    recetteDetail.setIdRecetteDetail(rs.getInt(1)); // Set the generated ID
                }
            }
        }
    }

    // Read
    public static RecetteDetail read(Connection connection, int idRecetteDetail) throws SQLException {
        String sql = "SELECT * FROM recette_detail WHERE id_recette_detail = ? AND etat = true";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRecetteDetail);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Ingredient ingredient = Ingredient.getById( rs.getInt("id_ingredient"));
                    return new RecetteDetail(
                        rs.getInt("id_recette_detail"),
                        rs.getDouble("quantite"),
                        rs.getBoolean("etat"),
                        rs.getInt("id_recette"),
                        ingredient
                    );
                }
            }
        }
        return null;
    }

    // Read All
    public static List<RecetteDetail> readAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM recette_detail WHERE etat = true";
        List<RecetteDetail> recetteDetails = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Ingredient ingredient = Ingredient.getById(rs.getInt("id_ingredient"));
                recetteDetails.add(new RecetteDetail(
                    rs.getInt("id_recette_detail"),
                    rs.getDouble("quantite"),
                    rs.getBoolean("etat"),
                    rs.getInt("id_recette"),
                    ingredient
                ));
            }
        }
        return recetteDetails;
    }

    // Update
    public static void update(Connection connection, RecetteDetail recetteDetail) throws SQLException {
        String sql = "UPDATE recette_detail SET quantite = ?, id_recette = ?, id_ingredient = ? WHERE id_recette_detail = ? AND etat = true";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, recetteDetail.getQuantite());
            statement.setInt(2, recetteDetail.getIdRecette());
            statement.setInt(3, recetteDetail.getIngredient().getIdIngredient());
            statement.setInt(4, recetteDetail.getIdRecetteDetail());
            statement.executeUpdate();
        }
    }


    public static List<RecetteDetail> readAllByRecette(Connection connection, int idRecette) throws SQLException {
        String sql = "SELECT * FROM recette_detail WHERE id_recette = ? AND etat = true";
        List<RecetteDetail> details = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRecette);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    RecetteDetail detail = new RecetteDetail();
                    detail.setIdRecetteDetail(rs.getInt("id_recette_detail"));
                    detail.setIdRecette(rs.getInt("id_recette"));
                    detail.setQuantite(rs.getDouble("quantite"));
                    detail.setEtat(rs.getBoolean("etat"));
    
                    // Fetch and set the associated Ingredient
                    Ingredient ingredient = Ingredient.getById( rs.getInt("id_ingredient"));
                    detail.setIngredient(ingredient);
    
                    details.add(detail);
                }
            }
        }
        return details;
    }



    public static void main(String[] args) throws SQLException {
        RecetteDetail recetteDetail = new RecetteDetail();
        recetteDetail.setQuantite(10.0);
        recetteDetail.setIdRecette(1);
        Ingredient ingredient = new Ingredient();
        ingredient.setIdIngredient(1);
        recetteDetail.setIngredient(ingredient);
        RecetteDetail.create(Connexion.getConnexion(), recetteDetail);
    }
    

    // Soft Delete (Toggle etat to false)
    public static void delete(Connection connection, int idRecetteDetail) throws SQLException {
        String sql = "UPDATE recette_detail SET etat = false WHERE id_recette_detail = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRecetteDetail);
            statement.executeUpdate();
        }
    }

    // Toggle Etat
    public static void toggleEtat(Connection connection, int idRecetteDetail, boolean etat) throws SQLException {
        String sql = "UPDATE recette_detail SET etat = ? WHERE id_recette_detail = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, etat);
            statement.setInt(2, idRecetteDetail);
            statement.executeUpdate();
        }
    }
}
