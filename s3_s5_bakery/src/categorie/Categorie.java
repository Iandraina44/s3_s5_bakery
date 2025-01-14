package categorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;

public class Categorie {
    int idCategorie;
    String nomCategorie;
    boolean etat;

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
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
            String sql = "INSERT INTO categorie (nom_categorie, etat) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.nomCategorie);
            preparedStatement.setBoolean(2, true);
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

    public static Categorie getById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Categorie categorie = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "SELECT * FROM categorie WHERE id_categorie = ? AND etat = true";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                categorie = new Categorie();
                categorie.setIdCategorie(resultSet.getInt("id_categorie"));
                categorie.setNomCategorie(resultSet.getString("nom_categorie"));
                categorie.setEtat(resultSet.getBoolean("etat"));
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
        return categorie;
    }

    public static List<Categorie> getAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Categorie> categories = new ArrayList<>();
        try {
            connection = Connexion.getConnexion();
            String sql = "SELECT * FROM categorie WHERE etat = true";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Categorie categorie = new Categorie();
                categorie.setIdCategorie(resultSet.getInt("id_categorie"));
                categorie.setNomCategorie(resultSet.getString("nom_categorie"));
                categorie.setEtat(resultSet.getBoolean("etat"));
                categories.add(categorie);
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
        return categories;
    }

    public void update() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "UPDATE categorie SET nom_categorie = ? WHERE id_categorie = ? AND etat = true";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.nomCategorie);
            preparedStatement.setInt(2, this.idCategorie);
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

    public static void delete(int idCategorie) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "UPDATE categorie SET etat = false WHERE id_categorie = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idCategorie);
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
        Categorie categorie = new Categorie();
        categorie.setNomCategorie("C1");
        categorie.create();
    }
}
