<%@ page import="ingredient.*, recette.*, vente.*, categorie.*, java.util.*" %>
<jsp:include page="header.jsp" />

<%
    // Récupération des données
    List<Gout> gouts = (List<Gout>) request.getAttribute("gouts");
    List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
    List<Vente> ventesFiltrees = (List<Vente>) request.getSession().getAttribute("ventesFiltrees");
%>

<div class="container-fluid">
    <div class="container-fluid">

        <!-- Formulaire de filtre -->
        <div class="card">
            <div class="card-body">
                <h5 class="card-title fw-semibold mb-4">Vente</h5>
                <div class="card">
                    <div class="card-body">
                        <form method="post" action="<%= request.getContextPath() %>/filtrerVentes">
                            <div class="mb-3">
                                <label class="form-label">Goût</label>
                                <select class="form-select" name="id_gout">
                                    <% for (Gout gout : gouts) { %>
                                        <option value="<%= gout.getIdGout() %>"><%= gout.getNomGout() %></option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Catégorie</label>
                                <select class="form-select" name="id_categorie">
A                                    <% for (Categorie categorie : categories) { %>
                                        <option value="<%= categorie.getIdCategorie() %>"><%= categorie.getNomCategorie() %></option>
                                    <% } %>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">Rechercher</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Tableau des résultats -->
        <div class="card">
            <div class="card-body">
                <h5 class="card-title fw-semibold mb-4">Liste Vente</h5>
                <div class="table-responsive">
                    <table class="table text-nowrap mb-0 align-middle">
                        <thead class="text-dark fs-4">
                            <tr>
                                <th class="border-bottom-0">ID</th>
                                <th class="border-bottom-0">Recette</th>
                                <th class="border-bottom-0">Date</th>
                                <th class="border-bottom-0">Quantité</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                            if (ventesFiltrees != null && !ventesFiltrees.isEmpty()) {
                                for (Vente vente : ventesFiltrees) { 
                            %>
                                <tr>
                                    <td><%= vente.getIdVente() %></td>
                                    <td><%= vente.getRecette().getNomRecette() %></td>
                                    <td><%= vente.getDateVente() %></td>
                                    <td><%= vente.getQuantiteVente() %></td>
                                </tr>
                            <% 
                                }
                            } else { 
                            %>
                                <tr>
                                    <td colspan="4" class="text-center">Aucune vente trouvée pour les filtres sélectionnés.</td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />
