package servlet.entityservlet;

import java.io.IOException;
import java.util.List;
import java.sql.SQLException;
import java.sql.Timestamp;  // Use Timestamp for handling date and time
import ingredient.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.DateUtils;

@WebServlet("/achat-ingredient")
public class AchatIngredientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            String action = request.getParameter("action");
            if (action==null) {
                action="read";
            }

            if (action.equals("delete")) {
                int idAchatIngredient = Integer.parseInt(request.getParameter("id_achat_ingredient"));
                AchatIngredient.delete(idAchatIngredient);
            }






            List<Ingredient> ingredients = Ingredient.getAll();
            request.setAttribute("ingredients", ingredients);
            List<AchatIngredient> achatIngredients = AchatIngredient.getAll();
            request.setAttribute("achat-ingredients", achatIngredients);

            request.getRequestDispatcher("/achat-ingredient.jsp").forward(request, response);
        
        } catch (Exception e) {
            throw new ServletException("Error retrieving achats", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        int idIngredient = Integer.parseInt(request.getParameter("id_ingredient"));
        double quantite= Double.parseDouble(request.getParameter("quantite_achat"));
        
        Timestamp dateAchat = DateUtils.convertToTimestamp(request.getParameter("date_achat"));

        AchatIngredient achatIngredient = new AchatIngredient();
        try {
            achatIngredient.setIngredient(Ingredient.getById(idIngredient));
            achatIngredient.setDateAchatIngredient(dateAchat);
            achatIngredient.setQuantiteAchat(quantite);
            achatIngredient.setPrixUnitaireIngredient(achatIngredient.getIngredient().getPrixIngredient());
            achatIngredient.setPrixTotalIngredient(achatIngredient.getPrixUnitaireIngredient()*quantite);
            achatIngredient.acheter();

            response.sendRedirect("achat-ingredient");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}