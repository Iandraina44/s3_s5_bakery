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

    // Getters and Setters
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


    // Create method
    public int create() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "INSERT INTO stock_ingredient (quantite_stock_ingredient, etat, date_stock_ingredient, id_ingredient) VALUES (?, ?, ?, ?) RETURNING id_stock_ingredient";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, this.quantiteStockIngredient);
            preparedStatement.setBoolean(2, true);
            preparedStatement.setTimestamp(3, this.dateStockIngredient);
            preparedStatement.setInt(4, this.ingredient.getIdIngredient());
    
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                this.idStockIngredient = resultSet.getInt("id_stock_ingredient");
            }
            return this.idStockIngredient;
        } catch (SQLException e) {
            throw new SQLException("Error creating StockIngredient", e);
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
    }

    
    

    // Update method
    public void update() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "UPDATE stock_ingredient SET quantite_stock_ingredient = ?, id_ingredient = ?, date_stock_ingredient = ? WHERE id_stock_ingredient = ? AND etat = true";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, this.quantiteStockIngredient);
            preparedStatement.setInt(2, this.ingredient.getIdIngredient());
            preparedStatement.setTimestamp(3, this.dateStockIngredient);
            preparedStatement.setInt(4, this.idStockIngredient);
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

    // Delete method
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

    // Get by ID method
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

    // Get all method
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
}
