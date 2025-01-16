<%@ page import="recette.Recommandation, recette.Recette, java.util.List" %>
<jsp:include page="header.jsp" />

<div class="container-fluid">
    <div class="container-fluid">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title fw-semibold mb-4">Filtrer les recommandations</h5>
                <div class="card">
                    <div class="card-body">
                        <!-- Filter Form -->
                        <form action="filtre-recommandation" method="post">
                            <div>
                                <label class="form-label" for="mois">Mois</label>
                                <input type="month" class="form-control" id="mois" name="month_year" required>
                            </div><br>
                            <button type="submit" class="btn btn-primary">Rechercher</button>
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
                        <!-- TABLE -->
                        <h5 class="card-title fw-semibold mb-4">Liste recommandations</h5>
                        <div class="table-responsive">
                            <table class="table text-nowrap mb-0 align-middle">
                                <thead class="text-dark fs-4">
                                    <tr>
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Id</h6>
                                        </th>
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Recette</h6>
                                        </th>
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Mois</h6>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        List<Recommandation> recommandations = (List<Recommandation>) request.getAttribute("recommandations");
                                        if (recommandations != null && !recommandations.isEmpty()) {
                                            for (Recommandation recommandation : recommandations) {
                                                Recette recette = recommandation.getRecette();
                                    %>
                                    <tr>
                                        <td><%= recommandation.getIdRecommandation() %></td>
                                        <td><%= recette != null ? recette.getNomRecette() : "N/A" %></td>
                                        <td><%= recommandation.getMois() + "/" + recommandation.getAnnee() %></td>
                                    </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="3" class="text-center">Aucune recommandation trouvée.</td>
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
