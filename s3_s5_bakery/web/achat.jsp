<%@ page import="entity.*, connexion.*, java.util.*" %>
<%-- <%@ include file="WEB-INF/header.jsp" %> --%>

<%
    String title = "Liste des Achats";
%>

<h1>Ajouter un Achat</h1>
<form action="achats" method="post" class="mb-4">
    <input type="hidden" name="action" value="create">
    <div class="form-group">
        <label for="prixUnitaire">Prix Unitaire:</label>
        <input type="number" step="0.01" class="form-control" id="prixUnitaire" name="prixUnitaire" required>
    </div>
    <div class="form-group">
        <label for="prixTotal">Prix Total:</label>
        <input type="number" step="0.01" class="form-control" id="prixTotal" name="prixTotal" required>
    </div>
    <div class="form-group">
        <label for="dateAchat">Date d'Achat:</label>
        <input type="date" class="form-control" id="dateAchat" name="dateAchat" required>
    </div>
    <div class="form-group">
        <label for="idIngredient">ID Ingrédient:</label>
        <input type="number" class="form-control" id="idIngredient" name="idIngredient" required>
    </div>
    <button type="submit" class="btn btn-primary">Ajouter</button>
</form>

<h1>Liste des Achats</h1>
<table class="table table-bordered">
    <thead class="thead-light">
        <tr>
            <th>ID</th>
            <th>Prix Unitaire</th>
            <th>Prix Total</th>
            <th>Date d'Achat</th>
            <th>ID Ingrédient</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <%
            List<Achat> achats = (List<Achat>) request.getAttribute("achats");
            for (Achat achat : achats) {
        %>
            <tr>
                <td><%= achat.getIdAchatIngredient() %></td>
                <td><%= achat.getPrixUnitaireIngredient() %></td>
                <td><%= achat.getPrixTotalIngredient() %></td>
                <td><%= achat.getDateAchatIngredient() %></td>
                <td><%= achat.getIdIngredient() %></td>
                <td>
                    <form action="achats" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="<%= achat.getIdAchatIngredient() %>">
                        <button type="submit" class="btn btn-danger">Supprimer</button>
                    </form>
                    <form action="achats" method="get" style="display:inline;">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="<%= achat.getIdAchatIngredient() %>">
                        <button type="submit" class="btn btn-warning">Modifier</button>
                    </form>
                </td>
            </tr>
        <%
            }
        %>
    </tbody>
</table>

<%-- <%@ include file="WEB-INF/footer.jsp" %> --%>