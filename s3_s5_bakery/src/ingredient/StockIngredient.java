package ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import connexion.Connexion;

public class StockIngredient {
    private int idStockIngredient;
    private double quantiteStockIngredient;
    private boolean etat;
    private Ingredient ingredient;
    private Timestamp dateStockIngredient;
    private AchatIngredient achatIngredient;  // New attribute for AchatIngredient

    public int getIdStockIngredient() {
        return idStockIngredient;
    }

    public void setIdStockIngredient(int idStockIngredient) {
        this.idStockIngredient = idStockIngredient;
    }

    public double getQuantiteStockIngredient() {
        return quantiteStockIngredient;
    }

    public void setQuantiteStockIngredient(double quantiteStockIngredient) {
        this.quantiteStockIngredient = quantiteStockIngredient;
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

    public Timestamp getDateStockIngredient() {
        return dateStockIngredient;
    }

    public void setDateStockIngredient(Timestamp dateStockIngredient) {
        this.dateStockIngredient = dateStockIngredient;
    }

    public AchatIngredient getAchatIngredient() {
        return achatIngredient;
    }

    public void setAchatIngredient(AchatIngredient achatIngredient) {
        this.achatIngredient = achatIngredient;
    }

    public void create() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "INSERT INTO stock_ingredient (quantite_stock_ingredient, etat, date_stock_ingredient, id_ingredient, id_achat_ingredient) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, this.quantiteStockIngredient);
            preparedStatement.setBoolean(2, true);
            preparedStatement.setTimestamp(3, this.dateStockIngredient);
            preparedStatement.setInt(4, this.ingredient.getIdIngredient());
            preparedStatement.setInt(5, this.achatIngredient.getIdAchatIngredient());  // Use AchatIngredient ID
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error creating StockIngredient", e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void update() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "UPDATE stock_ingredient SET quantite_stock_ingredient = ?, id_ingredient = ?, date_stock_ingredient = ?, id_achat_ingredient = ? WHERE id_stock_ingredient = ? AND etat = true";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, this.quantiteStockIngredient);
            preparedStatement.setInt(2, this.ingredient.getIdIngredient());
            preparedStatement.setTimestamp(3, this.dateStockIngredient);
            preparedStatement.setInt(4, this.achatIngredient.getIdAchatIngredient());  // Use AchatIngredient ID
            preparedStatement.setInt(5, this.idStockIngredient);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error updating StockIngredient", e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void delete(int idStockIngredient) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "UPDATE stock_ingredient SET etat = false WHERE id_stock_ingredient = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idStockIngredient);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting StockIngredient", e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static StockIngredient getById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "SELECT * FROM stock_ingredient WHERE id_stock_ingredient = ? AND etat = true";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                StockIngredient stock = new StockIngredient();
                stock.setIdStockIngredient(resultSet.getInt("id_stock_ingredient"));
                stock.setQuantiteStockIngredient(resultSet.getDouble("quantite_stock_ingredient"));
                stock.setEtat(resultSet.getBoolean("etat"));
                stock.setDateStockIngredient(resultSet.getTimestamp("date_stock_ingredient"));

                Ingredient ingredient = Ingredient.getById(resultSet.getInt("id_ingredient"));
                stock.setIngredient(ingredient);

                // Set AchatIngredient using the foreign key
                AchatIngredient achatIngredient = AchatIngredient.getById(resultSet.getInt("id_achat_ingredient"));
                stock.setAchatIngredient(achatIngredient);

                return stock;
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving StockIngredient by ID", e);
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
        return null;
    }

    public static List<StockIngredient> getAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<StockIngredient> stockList = new ArrayList<>();
        try {
            connection = Connexion.getConnexion();
            String sql = "SELECT * FROM stock_ingredient WHERE etat = true";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StockIngredient stock = new StockIngredient();
                stock.setIdStockIngredient(resultSet.getInt("id_stock_ingredient"));
                stock.setQuantiteStockIngredient(resultSet.getDouble("quantite_stock_ingredient"));
                stock.setEtat(resultSet.getBoolean("etat"));
                stock.setDateStockIngredient(resultSet.getTimestamp("date_stock_ingredient"));

                Ingredient ingredient = Ingredient.getById(resultSet.getInt("id_ingredient"));
                stock.setIngredient(ingredient);

                // Set AchatIngredient using the foreign key
                AchatIngredient achatIngredient = AchatIngredient.getById(resultSet.getInt("id_achat_ingredient"));
                stock.setAchatIngredient(achatIngredient);

                stockList.add(stock);
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving all StockIngredients", e);
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
        return stockList;
    }


    public static void deleteByIdAchatIngredient(int idAchatIngredient) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            // SQL query to update the 'etat' field to false for all stock ingredients associated with the given AchatIngredient
            String sql = "UPDATE stock_ingredient SET etat = false WHERE id_achat_ingredient = ? AND etat = true";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idAchatIngredient);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting StockIngredients by AchatIngredient ID", e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    

}
