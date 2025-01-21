package servlet.recetteServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import connexion.Connexion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import recette.Recommandation;

@WebServlet("/filtre-recommandation")
public class FiltreRecommandationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String monthYear = req.getParameter("month_year"); 

        String[] parts = monthYear.split("-");

        int month =Integer.parseInt( parts[1]); 
        int year = Integer.parseInt (parts[0]);

        List<Recommandation> recommandations=null;
        try {
            recommandations=Recommandation.getByMoisandYear(Connexion.getConnexion(),year, month);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        req.setAttribute("recommandations", recommandations);
        req.getRequestDispatcher("filtre-recommandation.jsp").forward(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        req.getRequestDispatcher("filtre-recommandation.jsp").forward(req, resp);
   
    }
    
}
