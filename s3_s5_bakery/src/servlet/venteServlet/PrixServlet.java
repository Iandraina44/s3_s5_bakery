package servlet.venteServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import connexion.Connexion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import recette.Prix;
import recette.Recette;
import utils.DateUtils;

@WebServlet("/prix-modif")
public class PrixServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Récupérer la liste des recettes pour le formulaire
        try {
            List<Recette> recettes = Recette.readAll(Connexion.getConnexion());
            req.setAttribute("recettes", recettes);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Rediriger vers la page JSP
        req.getRequestDispatcher("prix.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Récupérer les données du formulaire
        int idRecette = Integer.parseInt(req.getParameter("idRecette"));
        double nouveauPrix = Double.parseDouble(req.getParameter("nouveauPrix"));
        String dateChangement = req.getParameter("dateChangement");

        // Convertir la date en Timestamp
        Timestamp dateChangementPrix = DateUtils.convertToTimestamp(dateChangement);
        

        // Créer un objet Prix
        Prix prix = new Prix();
        prix.setNouveauPrix(nouveauPrix);
        prix.setDateChangementPrix(dateChangementPrix);

        // Récupérer la recette associée
        try (Connection connection = Connexion.getConnexion()) {
            Recette recette = Recette.read(connection, idRecette);
            prix.setRecette(recette);

            // Insérer le nouveau prix dans la base de données
            Prix.create(connection, prix);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Rediriger vers la même page pour afficher le résultat
        resp.sendRedirect(req.getContextPath() + "/prix-modif");
    }
}