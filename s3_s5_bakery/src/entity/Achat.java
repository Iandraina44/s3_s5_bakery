package entity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connexion.Connexion;

public class Achat {
    private int idAchatIngredient;
    private double prixUnitaireIngredient;
    private double prixTotalIngredient;
    private Date dateAchatIngredient;
    private int idIngredient;

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

    public Date getDateAchatIngredient() {
        return dateAchatIngredient;
    }

    public void setDateAchatIngredient(Date dateAchatIngredient) {
        this.dateAchatIngredient = dateAchatIngredient;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public void create() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "INSERT INTO achat_ingredient (prix_unitaire_ingredient, prix_total_ingredient, date_achat_ingredient, id_ingredient) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, this.prixUnitaireIngredient);
            preparedStatement.setDouble(2, this.prixTotalIngredient);
            preparedStatement.setDate(3, this.dateAchatIngredient);
            preparedStatement.setInt(4, this.idIngredient);
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
    public void update() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Connexion.getConnexion();
            String sql = "UPDATE achat_ingredient SET prix_unitaire_ingredient = ?, prix_total_ingredient = ?, date_achat_ingredient = ?, id_ingredient = ? WHERE id_achat_ingredient = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, this.prixUnitaireIngredient);
            preparedStatement.setDouble(2, this.prixTotalIngredient);
            preparedStatement.setDate(3, this.dateAchatIngredient);
            preparedStatement.setInt(4, this.idIngredient);
            preparedStatement.setInt(5, this.idAchatIngredient);
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
            String sql = "DELETE FROM achat_ingredient WHERE id_achat_ingredient = ?";
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

    public static List<Achat> getAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Achat> achats = new ArrayList<>();
        try {
            connection = Connexion.getConnexion();
            String sql = "SELECT * FROM achat_ingredient";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Achat achat = new Achat();
                achat.setIdAchatIngredient(resultSet.getInt("id_achat_ingredient"));
                achat.setPrixUnitaireIngredient(resultSet.getDouble("prix_unitaire_ingredient"));
                achat.setPrixTotalIngredient(resultSet.getDouble("prix_total_ingredient"));
                achat.setDateAchatIngredient(resultSet.getDate("date_achat_ingredient"));
                achat.setIdIngredient(resultSet.getInt("id_ingredient"));
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

    public static void main(String[] args) {
        try {
            // Create a new Achat
            Achat achat = new Achat();
            achat.setPrixUnitaireIngredient(10.0);
            achat.setPrixTotalIngredient(100.0);
            achat.setDateAchatIngredient(Date.valueOf("2023-10-01"));
            achat.setIdIngredient(1);
            achat.create();
            System.out.println("Achat created successfully");

            achat.setPrixUnitaireIngredient(12.0);
            achat.setPrixTotalIngredient(120.0);
            achat.update();
            System.out.println("Achat updated successfully");

            List<Achat> achats = Achat.getAll();
            for (Achat a : achats) {
                System.out.println(a.getIdAchatIngredient() + " " + a.getPrixUnitaireIngredient() + " " + a.getPrixTotalIngredient() + " " + a.getDateAchatIngredient() + " " + a.getIdIngredient());
            }

            Achat.delete(achat.getIdAchatIngredient());
            System.out.println("Achat deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}