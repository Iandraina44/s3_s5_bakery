package servlet.venteServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import connexion.Connexion;
import vente.Vente;
import categorie.Gout;
import categorie.Categorie;

@WebServlet("/filtrerVentes")
public class VenteFiltreServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;

        try {
            // Récupérer les paramètres de filtre
            String idGoutParam = request.getParameter("id_gout");
            String idCategorieParam = request.getParameter("id_categorie");

            // Vérifier et convertir les paramètres
            Integer idGout = (idGoutParam != null && !idGoutParam.isEmpty()) ? Integer.parseInt(idGoutParam) : null;
            Integer idCategorie = (idCategorieParam != null && !idCategorieParam.isEmpty()) ? Integer.parseInt(idCategorieParam) : null;

            // Obtenir une connexion à la base de données
            connection = Connexion.getConnexion();

            // Filtrer les ventes en fonction des paramètres
            
            // List<Vente> ventes = Vente.getByIdGoutAndIdCategorie(connection, idGout, idCategorie);
            
            List<Vente> ventes = Vente.selectAll(connection);

            // Stocker les résultats dans la session pour la redirection
            request.getSession().setAttribute("ventesFiltrees", ventes);

            // Rediriger vers doGet pour afficher les résultats
            response.sendRedirect(request.getContextPath() + "/filtrerVentes");
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du traitement de la requête");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les résultats stockés dans la session


        // Transférer les résultats vers la vue filtre-vente.jsp

        
        try {
            List<Gout> gouts = Gout.getAll();
            request.setAttribute("gouts", gouts);
            List<Categorie> categories = Categorie.getAll();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("filtre-vente.jsp").forward(request, response);
          
            // Supprimer les données de la session après affichage pour éviter des fuites
        } catch (SQLException | ServletException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
