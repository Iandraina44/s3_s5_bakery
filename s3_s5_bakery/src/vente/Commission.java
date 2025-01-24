package vente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;

public class Commission {
    private int idCommission;
    private double montantCommission;
    private Timestamp dateCommission;
    private Vendeur vendeur;

    // Constructors
    public Commission() {}

    public Commission(double montantCommission, Timestamp dateCommission, Vendeur vendeur) {
        this.montantCommission = montantCommission;
        this.dateCommission = dateCommission;
        this.vendeur = vendeur;
    }

    // Getters and Setters
    public int getIdCommission() {
        return idCommission;
    }

    public void setIdCommission(int idCommission) {
        this.idCommission = idCommission;
    }

    public double getMontantCommission() {
        return montantCommission;
    }

    public void setMontantCommission(double montantCommission) {
        this.montantCommission = montantCommission;
    }

    public Timestamp getDateCommission() {
        return dateCommission;
    }

    public void setDateCommission(Timestamp dateCommission) {
        this.dateCommission = dateCommission;
    }

    public Vendeur getVendeur() {
        return vendeur;
    }

    public void setVendeur(Vendeur vendeur) {
        this.vendeur = vendeur;
    }

    // CRUD Methods

    // Insert
    public void insert(Connection connection) throws SQLException {
        String sql = "INSERT INTO commission (montant_commission, date_commission, id_vendeur) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, this.montantCommission);
            statement.setTimestamp(2, this.dateCommission);
            statement.setInt(3, this.vendeur.getIdVendeur());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    this.idCommission = rs.getInt(1);
                }
            }
        }
    }

    // Select by ID
    public static Commission selectById(Connection connection, int idCommission) throws SQLException {
        String sql = "SELECT commission.*, vendeur.nom_vendeur FROM commission JOIN vendeur ON commission.id_vendeur = vendeur.id_vendeur WHERE commission.id_commission = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCommission);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCommission(rs);
                }
            }
        }
        return null;
    }

    // Select All
    public static List<Commission> selectAll(Connection connection) throws SQLException {
        String sql = "SELECT commission.*, vendeur.nom_vendeur FROM commission JOIN vendeur ON commission.id_vendeur = vendeur.id_vendeur";
        List<Commission> commissions = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                commissions.add(mapResultSetToCommission(rs));
            }
        }
        return commissions;
    }

    // Update
    public void update(Connection connection) throws SQLException {
        String sql = "UPDATE commission SET montant_commission = ?, date_commission = ?, id_vendeur = ? WHERE id_commission = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, this.montantCommission);
            statement.setTimestamp(2, this.dateCommission);
            statement.setInt(3, this.vendeur.getIdVendeur());
            statement.setInt(4, this.idCommission);
            statement.executeUpdate();
        }
    }

    // Delete
    public void delete(Connection connection) throws SQLException {
        String sql = "DELETE FROM commission WHERE id_commission = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, this.idCommission);
            statement.executeUpdate();
        }
    }

    // Helper to Map ResultSet to Commission
    private static Commission mapResultSetToCommission(ResultSet rs) throws SQLException {
        Commission commission = new Commission();
        commission.setIdCommission(rs.getInt("id_commission"));
        commission.setMontantCommission(rs.getDouble("montant_commission"));
        commission.setDateCommission(rs.getTimestamp("date_commission"));

        Vendeur vendeur = new Vendeur();
        vendeur.setIdVendeur(rs.getInt("id_vendeur"));
        vendeur.setNomVendeur(rs.getString("nom_vendeur"));
        commission.setVendeur(vendeur);

        return commission;
    }

    public static List<Commission> getSumByVendeurBetweenDates(Connection connection, Date startDate, Date endDate) throws SQLException {
        String sql = "SELECT v.id_vendeur, v.nom_vendeur, COALESCE(SUM(c.montant_commission), 0) AS total_commission " +
                     "FROM vendeur v " +
                     "LEFT JOIN commission c ON c.id_vendeur = v.id_vendeur " +
                     "AND DATE(c.date_commission) BETWEEN ? AND ? " +
                     "GROUP BY v.id_vendeur, v.nom_vendeur";
        List<Commission> results = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Les dates restent de type java.sql.Date pour l'extraction SQL
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
    
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int idVendeur = rs.getInt("id_vendeur");
                    String nomVendeur = rs.getString("nom_vendeur");
                    double totalCommission = rs.getDouble("total_commission");
    
                    Commission commission = new Commission();
    
                    Vendeur vendeur = new Vendeur();
                    vendeur.setIdVendeur(idVendeur);
                    vendeur.setNomVendeur(nomVendeur);
    
                    commission.setVendeur(vendeur);
                    commission.setMontantCommission(totalCommission);
    
                    results.add(commission);
                }
            }
        }
        return results;
    }
    

    public static List<Commission> getCommissionByGenre(Connection connection, int idGenre, Date startDate, Date endDate) throws SQLException {
        String sql = "SELECT v.id_vendeur, v.nom_vendeur, c.montant_commission,c.date_commission " +
                     "FROM commission c " +
                     "LEFT JOIN vendeur v ON c.id_vendeur = v.id_vendeur " +
                     "WHERE v.id_genre = ? " +
                     "AND DATE(c.date_commission) BETWEEN ? AND ? ";
        List<Commission> results = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idGenre);
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);
    
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int idVendeur = rs.getInt("id_vendeur");
                    String nomVendeur = rs.getString("nom_vendeur");
                    double montantCommission = rs.getDouble("montant_commission");
                    // Timestamp dateCommission=rs.getTimestamp("c.date_commission");
    
                    Commission commission = new Commission();
    
                    Vendeur vendeur = new Vendeur();
                    vendeur.setIdVendeur(idVendeur);
                    vendeur.setNomVendeur(nomVendeur);
    
                    commission.setVendeur(vendeur);
                    commission.setMontantCommission(montantCommission);
    
                    // commission.setDateCommission(dateCommission);

                    results.add(commission);
                }
            }
        }
        return results;
    }


    public static double getSommeCommission(List<Commission> commissions){
        double results=0;
        if (commissions!=null) {
            for (Commission commission : commissions) {
                results+=commission.getMontantCommission();
            }    
        }
        return results;
    }



    public static void main(String[] args) {
        try (Connection connection = Connexion.getConnexion()) {
            // Example: Select All
            List<Commission> commissions = Commission.selectAll(connection);
            for (Commission c : commissions) {
                System.out.println("ID: " + c.getIdCommission() + ", Montant: " + c.getMontantCommission() + ", Vendeur: " + c.getVendeur().getNomVendeur());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}