package ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;

public class Ingredient {
    int idIngredient;
    String nomIngredient;
    double prixIngredient;
    boolean etat;

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getNomIngredient() {
        return nomIngredient;
    }

    public void setNomIngredient(String nomIngredient) {
        this.nomIngredient = nomIngredient;
    }

    public double getPrixIngredient() {
        return prixIngredient;
    }

    public void setPrixIngredient(double prixIngredient) {
        this.prixIngredient = prixIngredient;
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
            String sql = "INSERT INTO ingredient (nom_ingredient, prix_ingredient, etat) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.nomIngredient);
            preparedStatement.setDouble(2, this.prixIngredient);
            preparedStatement.setBoolean(3, true);
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

    public static Ingredient getById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Ingredient ingredient = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "SELECT * FROM ingredient WHERE id_ingredient = ? AND etat = true";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ingredient = new Ingredient();
                ingredient.setIdIngredient(resultSet.getInt("id_ingredient"));
                ingredient.setNomIngredient(resultSet.getString("nom_ingredient"));
                ingredient.setPrixIngredient(resultSet.getDouble("prix_ingredient"));
                ingredient.setEtat(resultSet.getBoolean("etat"));
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
        return ingredient;
    }

    public static List<Ingredient> getAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            connection = Connexion.getConnexion();
            String sql = "SELECT * FROM ingredient WHERE etat = true";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setIdIngredient(resultSet.getInt("id_ingredient"));
                ingredient.setNomIngredient(resultSet.getString("nom_ingredient"));
                ingredient.setPrixIngredient(resultSet.getDouble("prix_ingredient"));
                ingredient.setEtat(resultSet.getBoolean("etat"));
                ingredients.add(ingredient);
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
        return ingredients;
    }

    public void update() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "UPDATE ingredient SET nom_ingredient = ?, prix_ingredient = ? WHERE id_ingredient = ? AND etat = true";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.nomIngredient);
            preparedStatement.setDouble(2, this.prixIngredient);
            preparedStatement.setInt(3, this.idIngredient);
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

    public static void delete(int idIngredient) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "UPDATE ingredient SET etat = false WHERE id_ingredient = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idIngredient);
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

    public static void main(String[] args) throws SQLException {
        Ingredient ingredient = new Ingredient();
        ingredient.setNomIngredient("Sucre");
        ingredient.setPrixIngredient(0.5);
        ingredient.create();
    }
}
