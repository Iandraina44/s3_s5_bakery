package connexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {

    public static Connection getConnexion() {
        Connection connexion = null;
        try {
            // Charger le driver PostgreSQL
            Class.forName("org.postgresql.Driver");

            // URL de connexion pour PostgreSQL
            connexion = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/boulangerieS3S5", 
                "postgres", 
                "postgre" // Remplacez par votre mot de passe
            );
        } catch (Exception e) {
            e.printStackTrace(); // Affiche la stack trace complète pour le débogage
        }
        return connexion;
    }
}
