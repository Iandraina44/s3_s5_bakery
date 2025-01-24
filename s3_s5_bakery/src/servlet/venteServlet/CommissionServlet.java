package servlet.venteServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vente.Commission;
import connexion.Connexion;

@WebServlet("/CommissionServlet")
public class CommissionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // GET: Affiche la page de filtrage
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Charger la page JSP de filtrage
        request.getRequestDispatcher("commission.jsp").forward(request, response);
    }

    // POST: Traite le filtrage par date et affiche les commissions filtrées
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        try {
            connection = Connexion.getConnexion();

            // Récupération des dates de début et de fin du filtre
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            
            // Convertir les dates au format java.sql.Date
            Date startDate = Date.valueOf(startDateStr);
            Date endDate = Date.valueOf(endDateStr);
            
            // Récupération de la liste des commissions filtrées
            List<Commission> commissions = Commission.getSumByVendeurBetweenDates(connection, startDate, endDate);
            
            // Ajouter la liste des commissions à la requête pour la transmettre à la JSP
            request.setAttribute("commissions", commissions);
            
            // Charger la page JSP avec la liste des commissions
            request.getRequestDispatcher("commission.jsp").forward(request, response);
        } catch (SQLException | IllegalArgumentException e) {
        }
    }
}