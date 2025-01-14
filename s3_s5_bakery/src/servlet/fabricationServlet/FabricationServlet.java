package servlet.fabricationServlet;

import recette.Fabrication;
import recette.Recette;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import connexion.Connexion;
import ingredient.Ingredient;

@WebServlet("/fabrication")
public class FabricationServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = Connexion.getConnexion()) {
            // Charger les recettes et ingrédients pour les listes déroulantes
            List<Recette> recettes = Recette.readAll(connection);
            request.setAttribute("recettes", recettes);
            List<Ingredient> ingredients = Ingredient.getAll();
            request.setAttribute("ingredients", ingredients);


            // Vérifier si idRecette et idIngredient sont fournis pour rechercher les fabrications
            String idRecetteParam = request.getParameter("id_recette");
            String idIngredientParam = request.getParameter("id_ingredient");

            if (idRecetteParam != null && idIngredientParam != null) {
                int idRecette = Integer.parseInt(idRecetteParam);
                int idIngredient = Integer.parseInt(idIngredientParam);

                List<Fabrication> fabrications = Fabrication.rechercherParRecetteEtIngredient(connection, idRecette, idIngredient);
                request.setAttribute("fabrications", fabrications);
            }


            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Une erreur s'est produite : " + e.getMessage());
        }

        // Rediriger vers la page JSP
        request.getRequestDispatcher("find-fabrication.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("fabrication");
    }
}
