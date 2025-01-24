<%@ page import="ingredient.*, recette.*,genre.* ,vente.*, client.*, categorie.*, java.util.*" %>

<jsp:include page="header.jsp" />

<%
    // Retrieve attributes
    List<Recette> listeRecette = (List<Recette>) request.getAttribute("recettes");
    List<Vente> listeVente = (List<Vente>) request.getAttribute("ventes");
    List<Client> listeClient = (List<Client>) request.getAttribute("clients");
    List<Vendeur> listeVendeur = (List<Vendeur>) request.getAttribute("vendeurs");

%>

<div class="container-fluid">
    <div class="container-fluid">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title fw-semibold mb-4">Insertion vente</h5>
                <div class="card">
                    <div class="card-body">
                        <form method="post" action="vente">
                            <div class="mb-3">
                                <label class="form-label">Recette</label>
                                <select class="form-select" name="id_recette">
                                    <% for (Recette recette : listeRecette) { %>
                                        <option value="<%= recette.getIdRecette() %>">
                                            <%= recette.getNomRecette() %> == <%= recette.getPrixRecette() %>
                                        </option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Vendeur</label>
                                <select class="form-select" name="id_vendeur">
                                    <% for (Vendeur vendeur : listeVendeur) { %>
                                        <option value="<%= vendeur.getIdVendeur() %>">
                                            <%= vendeur.getNomVendeur() %> == <%= vendeur.getGenre().getNomGenre() %>
                                        </option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Date Achat</label>
                                <input type="datetime-local" name="date_vente" class="form-control" required>
                            </div>
                            <div class="mb-3">
                                <label for="exampleInputPassword1" class="form-label">Quantite</label>
                                <input type="number" name="quantite_vente" min="1" required class="form-control"
                                    id="exampleInputPassword1">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Client</label>
                                <select class="form-select" name="id_client">
                                     <% for (Client client : listeClient) { %>
                                        <option value="<%= client.getIdClient() %>">
                                            <%= client.getNomClient() %>
                                        </option>
                                    <% } %>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">Insert</button>
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
                        <h5 class="card-title fw-semibold mb-4">List Recette</h5>
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
                                            <h6 class="fw-semibold mb-0">Client</h6>
                                        </th>
                                        
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Vendeur</h6>
                                        </th>
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Date</h6>
                                        </th>
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Quantite</h6>
                                        </th>
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Prix Total</h6>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (Vente vente : listeVente) { %>
                                        <tr>
                                            <td><%= vente.getIdVente() %></td>
                                            <td>
                                                <%= vente.getRecette().getNomRecette() %><br>
                                                <%= vente.getRecette().getPrixRecette() %>
                                            </td>
                                            <td><%= vente.getClient().getNomClient() %></td>
                                            <td><%= vente.getVendeur().getNomVendeur() %></td>
                                            <td><%= vente.getDateVente() %></td>
                                            <td><%= vente.getQuantiteVente() %></td>
                                            <td><%= vente.getPrixTotalVente() %></td>
                                        </tr>
                                    <% } %>
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
