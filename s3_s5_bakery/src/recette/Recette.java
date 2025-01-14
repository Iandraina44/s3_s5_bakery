package recette;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;
import categorie.Categorie;
import categorie.Gout;

public class Recette {
    private int idRecette;
    private String nomRecette;
    private double prixRecette;
    private boolean etat;
    private List<RecetteDetail> details;
    private Categorie categorie;
    private Gout gout;

    // Constructors
    public Recette() {
        this.details = new ArrayList<>();
    }

    public Recette(int idRecette, String nomRecette, double prixRecette, boolean etat) {
        this.idRecette = idRecette;
        this.nomRecette = nomRecette;
        this.prixRecette = prixRecette;
        this.etat = etat;
        this.details = new ArrayList<>();
    }

    // Getters and Setters
    public int getIdRecette() {
        return idRecette;
    }

    public void setIdRecette(int idRecette) {
        this.idRecette = idRecette;
    }

    public String getNomRecette() {
        return nomRecette;
    }

    public void setNomRecette(String nomRecette) {
        this.nomRecette = nomRecette;
    }

    public double getPrixRecette() {
        return prixRecette;
    }

    public void setPrixRecette(double prixRecette) {
        this.prixRecette = prixRecette;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public List<RecetteDetail> getDetails() {
        return details;
    }

    public void setDetails(List<RecetteDetail> details) {
        this.details = details;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Gout getGout() {
        return gout;
    }

    public void setGout(Gout gout) {
        this.gout = gout;
    }

    // CRUD Operations

    // Create
    public static void create(Connection connection, Recette recette) throws SQLException {
        String sql = "INSERT INTO recette (nom_recette, prix_recette, etat, id_categorie, id_gout) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, recette.getNomRecette());
            statement.setDouble(2, recette.getPrixRecette());
            statement.setBoolean(3, true); // Default etat = true
            statement.setInt(4, recette.getCategorie().getIdCategorie());
            statement.setInt(5, recette.getGout().getIdGout());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    recette.setIdRecette(rs.getInt(1));
                }
            }

            // Save associated details
            for (RecetteDetail detail : recette.getDetails()) {
                detail.setIdRecette(recette.getIdRecette());
                RecetteDetail.create(connection, detail);
            }
        }
    }

    // Read
    public static Recette read(Connection connection, int idRecette) throws SQLException {
        String sql = "SELECT * FROM recette r JOIN categorie c ON r.id_categorie = c.id_categorie JOIN gout g ON r.id_gout = g.id_gout WHERE r.id_recette = ? AND r.etat = true";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRecette);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Recette recette = new Recette(
                        rs.getInt("id_recette"),
                        rs.getString("nom_recette"),
                        rs.getDouble("prix_recette"),
                        rs.getBoolean("etat")
                    );

                    // Set associated Categorie and Gout
                    Categorie categorie = new Categorie();
                    categorie.setIdCategorie(rs.getInt("id_categorie"));
                    categorie.setNomCategorie(rs.getString("nom_categorie"));
                    categorie.setEtat(rs.getBoolean("etat"));
                    recette.setCategorie(categorie);

                    Gout gout = new Gout();
                    gout.setIdGout(rs.getInt("id_gout"));
                    gout.setNomGout(rs.getString("nom_gout"));
                    gout.setEtat(rs.getBoolean("etat"));
                    recette.setGout(gout);

                    // Fetch associated details
                    recette.setDetails(RecetteDetail.readAllByRecette(connection, idRecette));
                    return recette;
                }
            }
        }
        return null;
    }

    // Read All
    public static List<Recette> readAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM recette r JOIN categorie c ON r.id_categorie = c.id_categorie JOIN gout g ON r.id_gout = g.id_gout WHERE r.etat = true";
        List<Recette> recettes = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Recette recette = new Recette(
                    rs.getInt("id_recette"),
                    rs.getString("nom_recette"),
                    rs.getDouble("prix_recette"),
                    rs.getBoolean("etat")
                );

                // Set associated Categorie and Gout
                Categorie categorie = new Categorie();
                categorie.setIdCategorie(rs.getInt("id_categorie"));
                categorie.setNomCategorie(rs.getString("nom_categorie"));
                categorie.setEtat(rs.getBoolean("etat"));
                recette.setCategorie(categorie);

                Gout gout = new Gout();
                gout.setIdGout(rs.getInt("id_gout"));
                gout.setNomGout(rs.getString("nom_gout"));
                gout.setEtat(rs.getBoolean("etat"));
                recette.setGout(gout);

                // Fetch associated details
                recette.setDetails(RecetteDetail.readAllByRecette(connection, recette.getIdRecette()));
                recettes.add(recette);
            }
        }
        return recettes;
    }

    // Update
    public static void update(Connection connection, Recette recette) throws SQLException {
        String sql = "UPDATE recette SET nom_recette = ?, prix_recette = ?, id_categorie = ?, id_gout = ? WHERE id_recette = ? AND etat = true";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, recette.getNomRecette());
            statement.setDouble(2, recette.getPrixRecette());
            statement.setInt(3, recette.getCategorie().getIdCategorie());
            statement.setInt(4, recette.getGout().getIdGout());
            statement.setInt(5, recette.getIdRecette());
            statement.executeUpdate();
        }

        // Update associated details
        for (RecetteDetail detail : recette.getDetails()) {
            if (detail.getIdRecetteDetail() > 0) {
                RecetteDetail.update(connection, detail);
            } else {
                RecetteDetail.create(connection, detail);
            }
        }
    }

    // Delete (Soft Delete)
    public static void delete(Connection connection, int idRecette) throws SQLException {
        String sql = "UPDATE recette SET etat = false WHERE id_recette = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idRecette);
            statement.executeUpdate();
        }

        // Soft delete associated details
        for (RecetteDetail detail : RecetteDetail.readAllByRecette(connection, idRecette)) {
            RecetteDetail.delete(connection, detail.getIdRecetteDetail());
        }
    }

    public static void main(String[] args) throws SQLException {
        Recette recette = new Recette();
        recette.setNomRecette("Recette 1");
        recette.setPrixRecette(10.0);

        Categorie categorie = new Categorie();
        categorie.setIdCategorie(1);
        recette.setCategorie(categorie);

        Gout gout = new Gout();
        gout.setIdGout(1);
        recette.setGout(gout);


        Recette.create(Connexion.getConnexion(), recette);
    }
}
