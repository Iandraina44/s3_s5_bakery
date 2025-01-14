package categorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;

public class Gout {
    int idGout;
    String nomGout;
    Boolean etat;

    // Getters and Setters
    public int getIdGout() {
        return idGout;
    }

    public void setIdGout(int idGout) {
        this.idGout = idGout;
    }

    public String getNomGout() {
        return nomGout;
    }

    public void setNomGout(String nomGout) {
        this.nomGout = nomGout;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    // Create
    public void create() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "INSERT INTO gout (nom_gout, etat) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.nomGout);
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

    // Get by ID
    public static Gout getById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Gout gout = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "SELECT * FROM gout WHERE id_gout = ? AND etat = true";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                gout = new Gout();
                gout.setIdGout(resultSet.getInt("id_gout"));
                gout.setNomGout(resultSet.getString("nom_gout"));
                gout.setEtat(resultSet.getBoolean("etat"));
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
        return gout;
    }

    // Get all
    public static List<Gout> getAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Gout> gouts = new ArrayList<>();
        try {
            connection = Connexion.getConnexion();
            String sql = "SELECT * FROM gout WHERE etat = true";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Gout gout = new Gout();
                gout.setIdGout(resultSet.getInt("id_gout"));
                gout.setNomGout(resultSet.getString("nom_gout"));
                gout.setEtat(resultSet.getBoolean("etat"));
                gouts.add(gout);
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
        return gouts;
    }

    // Update
    public void update() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "UPDATE gout SET nom_gout = ? WHERE id_gout = ? AND etat = true";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.nomGout);
            preparedStatement.setInt(2, this.idGout);
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

    // Delete
    public static void delete(int idGout) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "UPDATE gout SET etat = false WHERE id_gout = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idGout);
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
        Gout gout = new Gout();
        gout.setNomGout("Gout 1");
        gout.create();
    }
}
