package ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;  // Use Timestamp for handling date and time
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;

public class AchatIngredient {
    private int idAchatIngredient;
    private double prixUnitaireIngredient;
    private double prixTotalIngredient;
    private double quantiteAchat;  // Added quantiteAchat
    private Timestamp dateAchatIngredient;  // Changed to Timestamp
    private Ingredient ingredient;
    private boolean etat;

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

    public double getQuantiteAchat() {  // Getter for quantiteAchat
        return quantiteAchat;
    }

    public void setQuantiteAchat(double quantiteAchat) {  // Setter for quantiteAchat
        this.quantiteAchat = quantiteAchat;
    }

    public Timestamp getDateAchatIngredient() {  // Changed to return Timestamp
        return dateAchatIngredient;
    }

    public void setDateAchatIngredient(Timestamp dateAchatIngredient) {  // Changed to accept Timestamp
        this.dateAchatIngredient = dateAchatIngredient;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public void create() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "INSERT INTO achat_ingredient (prix_unitaire_ingredient, prix_total_ingredient, quantite_achat, date_achat_ingredient, id_ingredient, etat) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, this.prixUnitaireIngredient);
            preparedStatement.setDouble(2, this.prixTotalIngredient);
            preparedStatement.setDouble(3, this.quantiteAchat);  // Include quantite_achat
            preparedStatement.setTimestamp(4, this.dateAchatIngredient);  // Use setTimestamp
            preparedStatement.setInt(5, this.ingredient.getIdIngredient());
            preparedStatement.setBoolean(6, true);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error creating AchatIngredient", e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }



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
    
                Ingredient ingredient = Ingredient.getById(resultSet.getInt("id_ingredient"));
                achat.setIngredient(ingredient);
                achat.setEtat(resultSet.getBoolean("etat"));
    
                return achat;
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving AchatIngredient by ID", e);
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
        return null;  // Return null if no record is found
    }
    

    
    public void update() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "UPDATE achat_ingredient SET prix_unitaire_ingredient = ?, prix_total_ingredient = ?, quantite_achat = ?, date_achat_ingredient = ?, id_ingredient = ? WHERE id_achat_ingredient = ? AND etat = true";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, this.prixUnitaireIngredient);
            preparedStatement.setDouble(2, this.prixTotalIngredient);
            preparedStatement.setDouble(3, this.quantiteAchat);  // Include quantite_achat
            preparedStatement.setTimestamp(4, this.dateAchatIngredient);  // Use setTimestamp
            preparedStatement.setInt(5, this.ingredient.getIdIngredient());
            preparedStatement.setInt(6, this.idAchatIngredient);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error updating AchatIngredient", e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void delete(int idAchatIngredient) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "UPDATE achat_ingredient SET etat = false WHERE id_achat_ingredient = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idAchatIngredient);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting AchatIngredient", e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

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
                achat.setQuantiteAchat(resultSet.getDouble("quantite_achat"));  // Retrieve quantite_achat
                achat.setDateAchatIngredient(resultSet.getTimestamp("date_achat_ingredient"));  // Use getTimestamp

                Ingredient ingredient = Ingredient.getById(resultSet.getInt("id_ingredient"));
                achat.setIngredient(ingredient);
                achat.setEtat(resultSet.getBoolean("etat"));

                achats.add(achat);
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving AchatIngredients", e);
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
        return achats;
    }

    public void acheter() throws SQLException{
    
        this.create();
        StockIngredient stockIngredient=new StockIngredient();
        stockIngredient.setQuantiteStockIngredient(this.quantiteAchat);
        stockIngredient.setDateStockIngredient(this.dateAchatIngredient);
        stockIngredient.setIngredient(this.ingredient);
        stockIngredient.create();
    
    }


    public static void  annulerAchat(int idAchatIngredient) throws SQLException{

        AchatIngredient.delete(idAchatIngredient);
        StockIngredient.deleteByIdAchatIngredient(idAchatIngredient);
    
    }

    public static void main(String[] args) throws SQLException {
        // For testing purposes
        AchatIngredient achat = new AchatIngredient();
        achat.setPrixUnitaireIngredient(10.0);
        achat.setPrixTotalIngredient(100.0);
        achat.setQuantiteAchat(10.0);  // Set quantiteAchat
        achat.setDateAchatIngredient(new Timestamp(System.currentTimeMillis()));  // Use Timestamp
        achat.setIngredient(Ingredient.getById(1));
        achat.acheter();
    }
}
