package servlet.entityservlet;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import entity.Achat;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/achats")
public class AchatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Achat> achats = Achat.getAll();
            request.setAttribute("achats", achats);
            request.getRequestDispatcher("/achat.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error retrieving achats", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Achat.delete(id);
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                double prixUnitaire = Double.parseDouble(request.getParameter("prixUnitaire"));
                double prixTotal = Double.parseDouble(request.getParameter("prixTotal"));
                Date dateAchat = Date.valueOf(request.getParameter("dateAchat"));
                int idIngredient = Integer.parseInt(request.getParameter("idIngredient"));

                Achat achat = new Achat();
                achat.setIdAchatIngredient(id);
                achat.setPrixUnitaireIngredient(prixUnitaire);
                achat.setPrixTotalIngredient(prixTotal);
                achat.setDateAchatIngredient(dateAchat);
                achat.setIdIngredient(idIngredient);

                achat.update();
            } else {
                double prixUnitaire = Double.parseDouble(request.getParameter("prixUnitaire"));
                double prixTotal = Double.parseDouble(request.getParameter("prixTotal"));
                Date dateAchat = Date.valueOf(request.getParameter("dateAchat"));
                int idIngredient = Integer.parseInt(request.getParameter("idIngredient"));

                Achat achat = new Achat();
                achat.setPrixUnitaireIngredient(prixUnitaire);
                achat.setPrixTotalIngredient(prixTotal);
                achat.setDateAchatIngredient(dateAchat);
                achat.setIdIngredient(idIngredient);

                achat.create();
            }
            response.sendRedirect("achats");
        } catch (Exception e) {
            throw new ServletException("Error processing achat", e);
        }
    }
}