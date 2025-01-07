<%@ page import="entity.*, connexion.*, java.util.*" %>
<%-- <%@ include file="WEB-INF/header.jsp" %> --%>

<%
    String title = "Liste des Ingrédients";
%>

<h1>Ajouter un Ingrédient</h1>
<form action="IngredientServlet" method="post" class="mb-4">
    <div class="form-group">
        <label for="nomIngredient">Nom:</label>
        <input type="text" class="form-control" id="nomIngredient" name="nomIngredient" required>
    </div>
    <div class="form-group">
        <label for="prixIngredient">Prix:</label>
        <input type="number" step="0.01" class="form-control" id="prixIngredient" name="prixIngredient" required>
    </div>
    <button type="submit" class="btn btn-primary">Ajouter</button>
</form>

<h1>Liste des Ingrédients</h1>
<table class="table table-bordered">
    <thead class="thead-light">
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prix</th>
            <th>État</th>
        </tr>
    </thead>
    <tbody>
        <%
            List<Ingredient> ingredients = (List<Ingredient>) request.getAttribute("ingredients");
            for (Ingredient ingredient : ingredients) {
        %>
            <tr>
                <td><%= ingredient.getIdIngredient() %></td>
                <td><%= ingredient.getNomIngredient() %></td>
                <td><%= ingredient.getPrixIngredient() %></td>
                <td><%= ingredient.isEtatIngredient() ? "Disponible" : "Indisponible" %></td>
            </tr>
        <%
            }
        %>
    </tbody>
</table>

<%-- <%@ include file="WEB-INF/footer.jsp" %> --%>