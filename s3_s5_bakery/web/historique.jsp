<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="recette.Recette" %>
<%@ page import="recette.Prix" %>
<jsp:include page="header.jsp" />

<div class="container-fluid">
    <div class="container-fluid">

        <!-- Selection Form -->
        <div class="card">
            <div class="card-body">
                <h5 class="card-title fw-semibold mb-4">Historique</h5>
                <div class="card">
                    <div class="card-body">
                        <form method="post" action="historique">
                            <div class="mb-3">
                                <label class="form-label">Recette</label>
                                <select class="form-select" name="id_recette" required>
                                    <% 
                                        List<Recette> recettes = (List<Recette>) request.getAttribute("recettes");
                                        if (recettes != null) {
                                            for (Recette recette : recettes) {
                                    %>
                                    <option value="<%= recette.getIdRecette() %>">
                                        <%= recette.getNomRecette() %>
                                    </option>
                                    <% 
                                            }
                                        }
                                    %>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">OK</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Table Display -->
        <div class="card">
            <div class="card-body">
                <h5 class="card-title fw-semibold mb-4">TABLEAU</h5>
                <div class="card">
                    <div class="card-body">

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
                                            <h6 class="fw-semibold mb-0">Date</h6>
                                        </th>
                                        <th class="border-bottom-0">
                                            <h6 class="fw-semibold mb-0">Prix</h6>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        List<Prix> prixList = (List<Prix>) request.getAttribute("prix");
                                        if (prixList != null && !prixList.isEmpty()) {
                                            for (Prix prix : prixList) {
                                    %>
                                    <tr>
                                        <td><%= prix.getIdPrix() %></td>
                                        <td><%= prix.getRecette().getNomRecette() %></td>
                                        <td><%= prix.getDateChangementPrix() %></td>
                                        <td><%= prix.getNouveauPrix() %></td>
                                    </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="4" class="text-center">Aucun historique disponible</td>
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
