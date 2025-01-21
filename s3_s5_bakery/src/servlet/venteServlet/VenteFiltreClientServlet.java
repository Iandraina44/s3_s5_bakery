package servlet.venteServlet;

import java.io.IOException;

import connexion.Connexion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vente.Vente;
import java.sql.*;
import java.util.*;

@WebServlet("/client-vente")
public class VenteFiltreClientServlet extends HttpServlet{
    @Override
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("vente-filtre-client.jsp").forward(req, resp);;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String date = request.getParameter("date"); // Get client ID from request


        Connection connection = null;
        try {
            // Get database connection
            connection = Connexion.getConnexion();

            List<Vente> ventes=Vente.getByDateClient(connection, date);

            // Set the filtered sales list as a request attribute
            request.setAttribute("ventes", ventes);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing the request: " + e.getMessage());
        } 

        // Redirect to JSP
        request.getRequestDispatcher("vente-filtre-client.jsp").forward(request, response);
    }
}
