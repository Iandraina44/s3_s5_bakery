<%@ page import="ingredient.*, recette.*, vente.*, client.*, categorie.*, java.util.*" %>
<jsp:include page="header.jsp" />

<div class="container-fluid">
    <div class="container-fluid">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title fw-semibold mb-4">Filtre les ventes par Client</h5>
                <div class="card">
                    <div class="card-body">
                        <!-- Formulaire de recherche -->
                        <form method="post" action="client-vente">
                            <div>
                                <label class="form-label" for="mois">Date</label>
                                <input type="date" class="form-control" id="mois" name="date" required>
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
                        <!-- Affichage des résultats -->
                        <h5 class="card-title fw-semibold mb-4">Liste</h5>
                        <div class="table-responsive">
                            <table class="table text-nowrap mb-0 align-middle">
                                <thead class="text-dark fs-4">
                                    <tr>
                                        <th class="border-bottom-0"><h6 class="fw-semibold mb-0">Id Client</h6></th>
                                        <th class="border-bottom-0"><h6 class="fw-semibold mb-0">Client</h6></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        // Récupérer les données envoyées par le servlet
                                        List<Vente> listeVente = (List<Vente>) request.getAttribute("ventes");
                                        if (listeVente != null && !listeVente.isEmpty()) {
                                            for (Vente vente : listeVente) {
                                    %>
                                    <tr>
                                        <td><%= vente.getClient().getIdClient() %></td>
                                        <td><%= vente.getClient().getNomClient() %></td>
                                    </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="4" class="text-center">Aucune vente trouvée pour cette date.</td>
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
