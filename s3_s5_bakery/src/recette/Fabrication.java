package recette;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;
import recette.Recette;
import ingredient.*;

public class Fabrication {
    private int idFabrication;
    private double quantiteFabrication;
    private Timestamp dateFabrication;
    private boolean etat;
    private StockIngredient stockIngredient;
    private Recette recette;

    // Constructors
    public Fabrication() {}

    public Fabrication(double quantiteFabrication, Timestamp dateFabrication, boolean etat, StockIngredient stockIngredient, Recette recette) {
        this.quantiteFabrication = quantiteFabrication;
        this.dateFabrication = dateFabrication;
        this.etat = etat;
        this.stockIngredient = stockIngredient;
        this.recette = recette;
    }

    // Getters and Setters
    public int getIdFabrication() {
        return idFabrication;
    }

    public void setIdFabrication(int idFabrication) {
        this.idFabrication = idFabrication;
    }

    public double getQuantiteFabrication() {
        return quantiteFabrication;
    }

    public void setQuantiteFabrication(double quantiteFabrication) {
        this.quantiteFabrication = quantiteFabrication;
    }

    public Timestamp getDateFabrication() {
        return dateFabrication;
    }

    public void setDateFabrication(Timestamp dateFabrication) {
        this.dateFabrication = dateFabrication;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public StockIngredient getStockIngredient() {
        return stockIngredient;
    }

    public void setStockIngredient(StockIngredient stockIngredient) {
        this.stockIngredient = stockIngredient;
    }

    public Recette getRecette() {
        return recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    // CRUD Methods

    // Insert
    public void insert(Connection connection) throws SQLException {
        String sql = "INSERT INTO fabrication (quantite_fabrication, date_fabrication, etat, id_stock_ingredient, id_recette) VALUES (?, ?, true, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, this.quantiteFabrication);
            statement.setTimestamp(2, this.dateFabrication);
            statement.setInt(3, this.stockIngredient.getIdStockIngredient());
            statement.setInt(4, this.recette.getIdRecette());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    this.idFabrication = rs.getInt(1);
                }
            }
        }
    }

    // Select by ID
    public static Fabrication selectById(Connection connection, int idFabrication) throws SQLException {
        String sql = "SELECT * FROM fabrication WHERE id_fabrication = ? AND etat = true";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idFabrication);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFabrication(connection, rs);
                }
            }
        }
        return null;
    }

    // Select All
    public static List<Fabrication> selectAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM fabrication WHERE etat = true";
        List<Fabrication> fabrications = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                fabrications.add(mapResultSetToFabrication(connection, rs));
            }
        }
        return fabrications;
    }

    // Update
    public void update(Connection connection) throws SQLException {
        String sql = "UPDATE fabrication SET quantite_fabrication = ?, date_fabrication = ?, id_stock_ingredient = ?, id_recette = ? WHERE id_fabrication = ? AND etat = true";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, this.quantiteFabrication);
            statement.setTimestamp(2, this.dateFabrication);
            statement.setInt(3, this.stockIngredient.getIdStockIngredient());
            statement.setInt(4, this.recette.getIdRecette());
            statement.setInt(5, this.idFabrication);
            statement.executeUpdate();
        }
    }

    // Delete (Soft Delete)
    public void delete(Connection connection) throws SQLException {
        String sql = "UPDATE fabrication SET etat = false WHERE id_fabrication = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, this.idFabrication);
            statement.executeUpdate();
        }
    }

    // Helper to Map ResultSet to Fabrication
    private static Fabrication mapResultSetToFabrication(Connection connection, ResultSet rs) throws SQLException {
        Fabrication fabrication = new Fabrication();
        fabrication.setIdFabrication(rs.getInt("id_fabrication"));
        fabrication.setQuantiteFabrication(rs.getDouble("quantite_fabrication"));
        fabrication.setDateFabrication(rs.getTimestamp("date_fabrication"));
        fabrication.setEtat(rs.getBoolean("etat"));

        // Fetch StockIngredient
        int idStockIngredient = rs.getInt("id_stock_ingredient");
        StockIngredient stockIngredient = StockIngredient.getById( idStockIngredient);
        fabrication.setStockIngredient(stockIngredient);

        // Fetch Recette
        int idRecette = rs.getInt("id_recette");
        Recette recette = Recette.read(connection, idRecette);
        fabrication.setRecette(recette);

        return fabrication;
    }



    // Méthode de recherche par idRecette et idIngredient
    public static List<Fabrication> rechercherParRecetteEtIngredient(Connection connection, int idRecette, int idIngredient) throws SQLException {
        String sql = """
            SELECT fabrication.* 
            FROM fabrication
            JOIN recette_detail ON fabrication.id_recette = recette_detail.id_recette
            WHERE fabrication.etat = true 
                AND recette_detail.etat = true
                AND fabrication.id_recette = ?
            AND recette_detail.id_ingredient = ?
        """;
        List<Fabrication> fabrications = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRecette);
            statement.setInt(2, idIngredient);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    fabrications.add(mapResultSetToFabrication(connection, rs));
                }
            }
        }
        return fabrications;
    }


    public static void main(String[] args) throws SQLException {
        // Fabrication fabrication=new Fabrication();
        // fabrication.setQuantiteFabrication(0);
        // Recette recette=new Recette(1, null, 0, false);
        // fabrication.setRecette(recette);
        // fabrication.setDateFabrication(new Timestamp(System.currentTimeMillis()));
        
        // StockIngredient stockIngredient=new StockIngredient(1, 0, false, null, null);
        // fabrication.setStockIngredient(stockIngredient);

        // fabrication.insert(Connexion.getConnexion());
        
        
        List<Fabrication> fabrications = Fabrication.rechercherParRecetteEtIngredient(Connexion.getConnexion(), 1, 1);
        for (Fabrication fabrication : fabrications) {
            System.out.println(fabrication.getIdFabrication());
        }
    }



}
