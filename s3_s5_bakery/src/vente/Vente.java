package vente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connexion.Connexion;
import recette.Recette;

public class Vente {
    private int idVente;
    private double quantiteVente;
    private double prixUnitaireVente;
    private double prixTotalVente;
    private Timestamp dateVente;
    private boolean etat;
    private Recette recette;

    // Constructors
    public Vente() {}

    public Vente(double quantiteVente, double prixUnitaireVente, double prixTotalVente, Timestamp dateVente, boolean etat, Recette recette) {
        this.quantiteVente = quantiteVente;
        this.prixUnitaireVente = prixUnitaireVente;
        this.prixTotalVente = prixTotalVente;
        this.dateVente = dateVente;
        this.etat = etat;
        this.recette = recette;
    }

    // Getters and Setters
    public int getIdVente() {
        return idVente;
    }

    public void setIdVente(int idVente) {
        this.idVente = idVente;
    }

    public double getQuantiteVente() {
        return quantiteVente;
    }

    public void setQuantiteVente(double quantiteVente) {
        this.quantiteVente = quantiteVente;
    }

    public double getPrixUnitaireVente() {
        return prixUnitaireVente;
    }

    public void setPrixUnitaireVente(double prixUnitaireVente) {
        this.prixUnitaireVente = prixUnitaireVente;
    }

    public double getPrixTotalVente() {
        return prixTotalVente;
    }

    public void setPrixTotalVente(double prixTotalVente) {
        this.prixTotalVente = prixTotalVente;
    }

    public Timestamp getDateVente() {
        return dateVente;
    }

    public void setDateVente(Timestamp dateVente) {
        this.dateVente = dateVente;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
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
        String sql = "INSERT INTO vente (quantite_vente, prix_unitaire_vente, prix_total_vente, date_vente, etat, id_recette) VALUES (?, ?, ?, ?, true, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, this.quantiteVente);
            statement.setDouble(2, this.prixUnitaireVente);
            statement.setDouble(3, this.prixTotalVente);
            statement.setTimestamp(4, this.dateVente);
            statement.setInt(5, this.recette.getIdRecette());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    this.idVente = rs.getInt(1);
                }
            }
        }
    }

    // Select by ID
    public static Vente selectById(Connection connection, int idVente) throws SQLException {
        String sql = "SELECT * FROM vente WHERE id_vente = ? AND etat = true";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idVente);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToVente(connection, rs);
                }
            }
        }
        return null;
    }

    // Select All
    public static List<Vente> selectAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM vente WHERE etat = true";
        List<Vente> ventes = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                ventes.add(mapResultSetToVente(connection, rs));
            }
        }
        return ventes;
    }



    public static List<Vente> getByIdGoutAndIdCategorie(Connection connection, int idGout, int idCategorie) throws SQLException {
        List<Vente> ventes = new ArrayList<>();
        String query = 
        
        """ 
        SELECT v.*
        FROM vente v
        JOIN recette r ON v.id_recette = r.id_recette
        WHERE r.id_gout = ? AND r.id_categorie = ?
        
        """        
                ;;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, idGout);
            pstmt.setInt(2, idCategorie);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                Vente vente=mapResultSetToVente(connection, rs);
                ventes.add(vente);    
                }
            }
        }
        return ventes;
    }

    // Update
    public void update(Connection connection) throws SQLException {
        String sql = "UPDATE vente SET quantite_vente = ?, prix_unitaire_vente = ?, prix_total_vente = ?, date_vente = ?, id_recette = ? WHERE id_vente = ? AND etat = true";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, this.quantiteVente);
            statement.setDouble(2, this.prixUnitaireVente);
            statement.setDouble(3, this.prixTotalVente);
            statement.setTimestamp(4, this.dateVente);
            statement.setInt(5, this.recette.getIdRecette());
            statement.setInt(6, this.idVente);
            statement.executeUpdate();
        }
    }

    // Delete (Soft Delete)
    public void delete(Connection connection) throws SQLException {
        String sql = "UPDATE vente SET etat = false WHERE id_vente = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, this.idVente);
            statement.executeUpdate();
        }
    }

    // Helper to Map ResultSet to Vente
    private static Vente mapResultSetToVente(Connection connection, ResultSet rs) throws SQLException {
        Vente vente = new Vente();
        vente.setIdVente(rs.getInt("id_vente"));
        vente.setQuantiteVente(rs.getDouble("quantite_vente"));
        vente.setPrixUnitaireVente(rs.getDouble("prix_unitaire_vente"));
        vente.setPrixTotalVente(rs.getDouble("prix_total_vente"));
        vente.setDateVente(rs.getTimestamp("date_vente"));
        vente.setEtat(rs.getBoolean("etat"));

        // Fetch Recette
        int idRecette = rs.getInt("id_recette");
        Recette recette = Recette.read(connection, idRecette);
        vente.setRecette(recette);

        return vente;
    }

    public static void main(String[] args) throws SQLException {
        List<Vente> ventes = Vente.getByIdGoutAndIdCategorie(Connexion.getConnexion(), 1, 1);
        for (Vente vente : ventes) {
            System.out.println(vente.getIdVente());
        }
    }
}
