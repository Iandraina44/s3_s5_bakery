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
import recette.Recette;
import recette.Recommandation;

@WebServlet("/recommandation")
public class RecommandationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        List<Recette> recettes=null;
        List<Recommandation> recommandations=null;
        try {
            recettes=Recette.readAll(Connexion.getConnexion());
            recommandations = Recommandation.getByYear(Connexion.getConnexion(), 2024);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        req.setAttribute("recettes", recettes);
        req.setAttribute("recommandations", recommandations);
        req.getRequestDispatcher("/recommandations.jsp").forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idRecette=Integer.parseInt(req.getParameter("id_recette"));
        Recette recette=new Recette();
        recette.setIdRecette(idRecette);

        String monthYear = req.getParameter("month_year");

        String[] parts = monthYear.split("-");

        int month =Integer.parseInt( parts[1]); 
        int year = Integer.parseInt (parts[0]);

        Recommandation recommandation=new Recommandation();
        recommandation.setRecette(recette);
        recommandation.setAnnee(year);
        recommandation.setMois(month);

        try {
            Recommandation.create(Connexion.getConnexion(), recommandation);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        resp.sendRedirect("recommandation");


    }
    
}
