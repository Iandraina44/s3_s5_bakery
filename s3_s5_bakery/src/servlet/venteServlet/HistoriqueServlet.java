package servlet.venteServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import connexion.Connexion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import recette.Prix;
import recette.Recette;

@WebServlet("/historique")
public class HistoriqueServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Recette> recettes = Recette.readAll(Connexion.getConnexion());
            req.setAttribute("recettes", recettes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Rediriger vers la page JSP
        req.getRequestDispatcher("historique.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idRecette= Integer.parseInt(req.getParameter("id_recette"));

        try {
            List<Recette> recettes = Recette.readAll(Connexion.getConnexion());
            req.setAttribute("recettes", recettes);
            Recette recette=Recette.read(Connexion.getConnexion(), idRecette);
            
            List<Prix> prix = Prix.readAllByRecette(Connexion.getConnexion(), recette);
            req.setAttribute("prix", prix);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        req.getRequestDispatcher("historique.jsp").forward(req, resp);

    }
}
