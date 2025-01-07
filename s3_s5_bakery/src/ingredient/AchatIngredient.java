package ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;

public class AchatIngredient {
    private int idAchatIngredient;
    private double prixUnitaireIngredient;
    private double prixTotalIngredient;
    private double quantiteAchat;
    private Timestamp dateAchatIngredient;
    private boolean etat;
    private Ingredient ingredient;
    private int idStockIngredient;

    // Getters and setters
    public int getIdAchatIngredient() {
        return idAchatIngredient;
    }

    public void setIdAchatIngredient(int idAchatIngredient) {
        this.idAchatIngredient = idAchatIngredient;
    }

    public double getPrixUnitaireIngredient() {
        return prixUnitaireIngredient;
    }

    public void setPrixUnitaireIngredient(double prixUnitaireIngredient) {
        this.prixUnitaireIngredient = prixUnitaireIngredient;
    }

    public double getPrixTotalIngredient() {
        return prixTotalIngredient;
    }

    public void setPrixTotalIngredient(double prixTotalIngredient) {
        this.prixTotalIngredient = prixTotalIngredient;
    }

    public double getQuantiteAchat() {
        return quantiteAchat;
    }

    public void setQuantiteAchat(double quantiteAchat) {
        this.quantiteAchat = quantiteAchat;
    }

    public Timestamp getDateAchatIngredient() {
        return dateAchatIngredient;
    }

    public void setDateAchatIngredient(Timestamp dateAchatIngredient) {
        this.dateAchatIngredient = dateAchatIngredient;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getIdStockIngredient() {
        return idStockIngredient;
    }

    public void setIdStockIngredient(int idStockIngredient) {
        this.idStockIngredient = idStockIngredient;
    }

    // Create method
    public void create() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = """
                INSERT INTO achat_ingredient 
                (prix_unitaire_ingredient, prix_total_ingredient, quantite_achat, 
                date_achat_ingredient, id_stock_ingredient, id_ingredient, etat) 
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, this.prixUnitaireIngredient);
            preparedStatement.setDouble(2, this.prixTotalIngredient);
            preparedStatement.setDouble(3, this.quantiteAchat);
            preparedStatement.setTimestamp(4, this.dateAchatIngredient);
            preparedStatement.setInt(5, this.idStockIngredient);
            preparedStatement.setInt(6, this.ingredient.getIdIngredient());
            preparedStatement.setBoolean(7, true);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    // Update method
    public void update() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = """
                UPDATE achat_ingredient 
                SET prix_unitaire_ingredient = ?, prix_total_ingredient = ?, quantite_achat = ?, 
                date_achat_ingredient = ?, id_stock_ingredient = ?, id_ingredient = ? 
                WHERE id_achat_ingredient = ? AND etat = true
            """;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, this.prixUnitaireIngredient);
            preparedStatement.setDouble(2, this.prixTotalIngredient);
            preparedStatement.setDouble(3, this.quantiteAchat);
            preparedStatement.setTimestamp(4, this.dateAchatIngredient);
            preparedStatement.setInt(5, this.idStockIngredient);
            preparedStatement.setInt(6, this.ingredient.getIdIngredient());
            preparedStatement.setInt(7, this.idAchatIngredient);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    // Delete method
    public static void delete(int idAchatIngredient) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = """
                UPDATE achat_ingredient SET etat = false WHERE id_achat_ingredient = ?
            """;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idAchatIngredient);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    // Fetch all active entries
    public static List<AchatIngredient> getAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<AchatIngredient> achats = new ArrayList<>();
        try {
            connection = Connexion.getConnexion();
            String sql = "SELECT * FROM achat_ingredient WHERE etat = true";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AchatIngredient achat = new AchatIngredient();
                achat.setIdAchatIngredient(resultSet.getInt("id_achat_ingredient"));
                achat.setPrixUnitaireIngredient(resultSet.getDouble("prix_unitaire_ingredient"));
                achat.setPrixTotalIngredient(resultSet.getDouble("prix_total_ingredient"));
                achat.setQuantiteAchat(resultSet.getDouble("quantite_achat"));
                achat.setDateAchatIngredient(resultSet.getTimestamp("date_achat_ingredient"));
                achat.setIdStockIngredient(resultSet.getInt("id_stock_ingredient"));
                achat.setEtat(resultSet.getBoolean("etat"));

                Ingredient ingredient = Ingredient.getById(resultSet.getInt("id_ingredient"));
                achat.setIngredient(ingredient);
                achats.add(achat);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
        return achats;
    }

    // Fetch by ID
    public static AchatIngredient getById(int idAchatIngredient) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "SELECT * FROM achat_ingredient WHERE id_achat_ingredient = ? AND etat = true";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idAchatIngredient);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                AchatIngredient achat = new AchatIngredient();
                achat.setIdAchatIngredient(resultSet.getInt("id_achat_ingredient"));
                achat.setPrixUnitaireIngredient(resultSet.getDouble("prix_unitaire_ingredient"));
                achat.setPrixTotalIngredient(resultSet.getDouble("prix_total_ingredient"));
                achat.setQuantiteAchat(resultSet.getDouble("quantite_achat"));
                achat.setDateAchatIngredient(resultSet.getTimestamp("date_achat_ingredient"));
                achat.setIdStockIngredient(resultSet.getInt("id_stock_ingredient"));
                achat.setEtat(resultSet.getBoolean("etat"));

                Ingredient ingredient = Ingredient.getById(resultSet.getInt("id_ingredient"));
                achat.setIngredient(ingredient);
                return achat;
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
        return null;
    }


    public void acheter() throws SQLException{
        StockIngredient stockIngredient=new StockIngredient();
        stockIngredient.setIngredient(this.ingredient);
        stockIngredient.setQuantiteStockIngredient(this.quantiteAchat);
        stockIngredient.setDateStockIngredient(this.dateAchatIngredient);
        int id=stockIngredient.create();

        this.idStockIngredient=id;
        this.create();
    }

}
