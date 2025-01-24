package servlet.venteServlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import connexion.Connexion;
import genre.Genre;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vente.Commission;

@WebServlet("/genre-commission")
public class GenreCommissionServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Genre> genres=null;
        try {
            genres=Genre.getAll();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        req.setAttribute("genres", genres);
        req.getRequestDispatcher("filtre-commission-genre.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int idGenre=Integer.parseInt(req.getParameter("id_genre"));

        Date datedebut=Date.valueOf(req.getParameter("date1"));
        Date datefin=Date.valueOf(req.getParameter("date2"));

        List<Commission> commissions=null;
        try {
            commissions = Commission.getCommissionByGenre(Connexion.getConnexion(), idGenre, datedebut, datefin);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        req.setAttribute("commissions", commissions);


        List<Commission> commissionhomme=null;
        List<Commission> commissionfemme=null;
        try {
            commissionhomme = Commission.getCommissionByGenre(Connexion.getConnexion(), 1, datedebut, datefin);
            commissionfemme=Commission.getCommissionByGenre(Connexion.getConnexion(), 2, datedebut, datefin);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        List<Genre> genres=null;
        try {
            genres=Genre.getAll();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        req.setAttribute("genres", genres);



        req.setAttribute("commission_homme",Commission.getSommeCommission(commissionhomme));
        req.setAttribute("commission_femme",Commission.getSommeCommission(commissionfemme));


        req.getRequestDispatcher("filtre-commission-genre.jsp").forward(req, resp);

    }
}
