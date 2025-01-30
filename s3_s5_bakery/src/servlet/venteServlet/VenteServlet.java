package servlet.venteServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.postgresql.jdbc.TimestampUtils;

import client.Client;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import recette.Recette;
import utils.DateUtils;
import connexion.Connexion;
import vente.Commission;
import vente.Vendeur;
import vente.Vente;
import java.sql.*;

@WebServlet("/vente")
public class VenteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        try {
            connection = Connexion.getConnexion();

            List<Recette> recettes = Recette.readAll(connection);
            request.setAttribute("recettes", recettes);

            List<Vendeur> vendeurs = Vendeur.getAll();
            request.setAttribute("vendeurs", vendeurs);

            List<Client> clients = Client.getAll();
            request.setAttribute("clients", clients);

            List<Vente> ventes = Vente.selectAll(connection);
            request.setAttribute("ventes", ventes);
            request.getRequestDispatcher("vente.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        try {
            connection = Connexion.getConnexion();
            
            // Retrieve form data
            int idRecette = Integer.parseInt(request.getParameter("id_recette"));
            int idVendeur = Integer.parseInt(request.getParameter("id_vendeur"));
            double quantiteVente = Double.parseDouble(request.getParameter("quantite_vente"));
            Timestamp timestamp=DateUtils.convertToTimestamp(request.getParameter("date_vente"));

            int idClient=Integer.parseInt(request.getParameter("id_client"));

            Vente vente = new Vente();
            Recette recette = Recette.read(connection, idRecette);


            Recette recetteAjour=Recette.readOneByDate(Connexion.getConnexion(), recette, request.getParameter("date_vente"));


            Client client=Client.getById(idClient);
            
            Vendeur vendeur=Vendeur.getById(idVendeur);


            vente.setRecette(recetteAjour);
            vente.setQuantiteVente(quantiteVente);
            vente.setPrixUnitaireVente(recetteAjour.getPrixRecette());
            vente.setPrixTotalVente(recetteAjour.getPrixRecette()*quantiteVente);
            vente.setDateVente(timestamp);
            vente.setEtat(true);
            vente.setClient(client);
            vente.setVendeur(vendeur);

            if (vente.getPrixTotalVente()>=200000) {    
                Commission commission=new Commission();
                commission.setVendeur(vendeur);
                commission.setMontantCommission(vente.getPrixTotalVente()*0.05);
                commission.setDateCommission(timestamp);
                commission.insert(connection);
            }


            // Insert into database
            vente.insert(connection);

            

            // Redirect to list of ventes
            response.sendRedirect("vente");
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing the request");
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
}
