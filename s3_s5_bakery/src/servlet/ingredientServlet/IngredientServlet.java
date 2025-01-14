package servlet.ingredientServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import ingredient.Ingredient;

@WebServlet("/ingredient")
public class IngredientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action==null) {
            action="read";
        }

        if (action.equals("update")) {
            int idIngredient = Integer.parseInt(request.getParameter("id_ingredient"));
            try {
                Ingredient ingredient = Ingredient.getById(idIngredient);
                request.setAttribute("ingredient", ingredient);        
                request.setAttribute("action", "update");
            } catch (SQLException e) {
                throw new ServletException("Cannot obtain ingredient from DB", e);
            }
        }

        if (action.equals("delete")) {
            int idIngredient = Integer.parseInt(request.getParameter("id_ingredient"));
            try {
                Ingredient.delete(idIngredient);
            } catch (SQLException e) {
                throw new ServletException("Cannot delete ingredient", e);
            }
        }


        try {
            List<Ingredient> ingredients = Ingredient.getAll();
            request.setAttribute("ingredients", ingredients);
            request.getRequestDispatcher("/ingredient.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Cannot obtain ingredients from DB", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if (action==null) {
            action="create";
        }
        
        String nomIngredient = request.getParameter("nom_ingredient");
        double prixIngredient = Double.parseDouble(request.getParameter("prix_ingredient"));


        if (action.equals("update")) {

            int idIngredient = Integer.parseInt(request.getParameter("id_ingredient"));
            Ingredient ingredient = new Ingredient();
            ingredient.setIdIngredient(idIngredient);
            ingredient.setNomIngredient(nomIngredient);
            ingredient.setPrixIngredient(prixIngredient);
            try {
                ingredient.update();
                response.sendRedirect("ingredient");
            } catch (SQLException e) {
                throw new ServletException("Cannot update ingredient", e);
            }   
        }
 
        else{
            Ingredient ingredient = new Ingredient();
            ingredient.setNomIngredient(nomIngredient);
            ingredient.setPrixIngredient(prixIngredient);

            try {
                ingredient.create();
                response.sendRedirect("ingredient");
            } catch (SQLException e) {
                throw new ServletException("Cannot create ingredient", e);
            }
        }


    }
}