<%@ page import="ingredient.*, recette.*, vente.*, client.*, categorie.*,genre.*, java.util.*" %>

<jsp:include page="header.jsp" />

<%
    // Retrieve attributes
    List<Genre> genres = (List<Genre>) request.getAttribute("genres");
    List<Commission> commissions = (List<Commission>) request.getAttribute("commissions");
    Double sommeCommissionHomme = (Double) request.getAttribute("commission_homme");
    Double sommeCommissionFemme = (Double) request.getAttribute("commission_femme");
%>

<div class="container-fluid">
    <div class="container-fluid">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title fw-semibold mb-4">Filtre par genre</h5>
                <div class="card">
                    <div class="card-body">
                        <form method="post" action="genre-commission">
                            <div class="mb-3">
                                <label class="form-label">Genre</label>
                                <select class="form-select" name="id_genre">
                                    <% if (genres != null) { 
                                        for (Genre genre : genres) { %>
                                            <option value="<%= genre.getIdGenre() %>"><%= genre.getNomGenre() %></option>
                                    <% } } %>
                                </select>
                            </div>
                            <div>
                                <label class="form-label" for="date1">Date debut</label>
                                <input type="date" class="form-control" id="date1" name="date1">
                            </div><br>
                            <div>
                                <label class="form-label" for="date2">Date fin</label>
                                <input type="date" class="form-control" id="date2" name="date2">
                            </div><br>
                            <button type="submit" class="btn btn-primary">Filtrer</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <% if (sommeCommissionHomme != null || sommeCommissionFemme != null) { %>
        <div class="card">
            <div class="card-body">
                <h5 class="card-title fw-semibold mb-4">Commissions Totales</h5>
                <% if (sommeCommissionHomme != null) { %>
                    <p class="card-text">Commission Homme: <%= sommeCommissionHomme %></p>
                <% } %>
                <% if (sommeCommissionFemme != null) { %>
                    <p class="card-text">Commission Femme: <%= sommeCommissionFemme %></p>
                <% } %>
            </div>
        </div>
        <% } %>
      
        <% if (commissions != null) { %>
        <div class="card">
            <div class="card-body">
                <h5 class="card-title fw-semibold mb-4">TABLEAU</h5>
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title fw-semibold mb-4">Liste Commission</h5>
                        <div class="table-responsive">
                            <table class="table text-nowrap mb-0 align-middle">
                                <thead class="text-dark fs-4">
                                    <tr>
                                        <%-- <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Id</h6>
                                        </th> --%>
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Vendeur</h6>
                                        </th>
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Montant</h6>
                                        </th>
                                        <%-- <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Date</h6>
                                        </th> --%>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (Commission commission : commissions) { %>
                                        <tr>
                                            <%-- <td><%= commission.getIdCommission() %></td> --%>
                                            <td><%= commission.getVendeur().getNomVendeur() %></td>
                                            <td><%= commission.getMontantCommission() %></td>
                                            <%-- <td><%= commission.getDateCommission() %></td> --%>
                                        </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
    </div>
</div>

<jsp:include page="footer.jsp" />