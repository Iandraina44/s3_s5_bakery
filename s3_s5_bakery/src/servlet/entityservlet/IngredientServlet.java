package servlet.entityservlet;

import entity.Ingredient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/IngredientServlet")
public class IngredientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        String nomIngredient = request.getParameter("nomIngredient");
        double prixIngredient = Double.parseDouble(request.getParameter("prixIngredient"));

        Ingredient ingredient = new Ingredient();
        ingredient.setNomIngredient(nomIngredient);
        ingredient.setPrixIngredient(prixIngredient);

        try {
            ingredient.create();
            response.sendRedirect("IngredientServlet");
        } catch (SQLException e) {
            throw new ServletException("Cannot create ingredient", e);
        }
    }
}