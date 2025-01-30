<%@ page import="java.util.List, recette.Recette" %>
<jsp:include page="header.jsp" />

    <div class="container-fluid">
        <div class="container-fluid">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title fw-semibold mb-4">Changer le prix</h5>
                    <div class="card">
                        <div class="card-body">
                            <form action="<%= request.getContextPath() %>/prix-modif" method="POST">
                                <div class="mb-3">
                                    <label class="form-label">Recette</label>
                                    <select class="form-select" name="idRecette" required>
                                        <%
                                            List<Recette> recettes = (List<Recette>) request.getAttribute("recettes");
                                            if (recettes != null) {
                                                for (Recette recette : recettes) {
                                        %>
                                            <option value="<%= recette.getIdRecette() %>"><%= recette.getNomRecette() %></option>
                                        <%
                                                }
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label for="nouveauPrix" class="form-label">Nouveau prix</label>
                                    <input type="text" required class="form-control" id="nouveauPrix" name="nouveauPrix">
                                </div>
                                <div class="mb-3">
                                    <label for="dateChangement" class="form-label">Date changement</label>
                                    <input type="datetime-local" required class="form-control" id="dateChangement" name="dateChangement">
                                </div>
                                <button type="submit" class="btn btn-primary">OK</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<jsp:include page="footer.jsp" />

