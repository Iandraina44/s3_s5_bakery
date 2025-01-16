<%@ page import="recette.Recommandation, recette.Recette, java.util.List" %>
<jsp:include page="header.jsp" />

<div class="container-fluid">
    <div class="container-fluid">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title fw-semibold mb-4">Recommandation</h5>
                <div class="card">
                    <div class="card-body">
                        <form action="recommandation" method="post">
                            <div class="mb-3">
                                <label class="form-label">Recette</label>
                                <select class="form-select" name="id_recette" required>
                                    <%
                                        List<Recette> recettes = (List<Recette>) request.getAttribute("recettes");
                                        for (Recette recette : recettes) {
                                    %>
                                    <option value="<%= recette.getIdRecette() %>">
                                        <%= recette.getNomRecette() %>
                                    </option>
                                    <%
                                        }
                                    %>
                                </select>
                            </div>
                            <div>
                                <label class="form-label" for="month_year">Mois</label>
                                <input type="month" class="form-control" id="month_year" name="month_year" required>
                            </div>
                            <br>
                            <button type="submit" class="btn btn-primary">Inserer</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-body">
                <h5 class="card-title fw-semibold mb-4">TABLEAU</h5>
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title fw-semibold mb-4">Liste</h5>
                        <div class="table-responsive">
                            <table class="table text-nowrap mb-0 align-middle">
                                <thead class="text-dark fs-4">
                                    <tr>
                                        <th class="border-bottom-0">Id</th>
                                        <th class="border-bottom-0">Recette</th>
                                        <th class="border-bottom-0">Mois</th>
                                        <th class="border-bottom-0">Année</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        List<Recommandation> recommandations = (List<Recommandation>) request.getAttribute("recommandations");
                                        if (recommandations != null) {
                                            for (Recommandation recommandation : recommandations) {
                                    %>
                                    <tr>
                                        <td><%= recommandation.getIdRecommandation() %></td>
                                        <td><%= recommandation.getRecette().getNomRecette() %></td>
                                        <td><%= recommandation.getMois() %></td>
                                        <td><%= recommandation.getAnnee() %></td>
                                    </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="4">Aucune recommandation disponible.</td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />
