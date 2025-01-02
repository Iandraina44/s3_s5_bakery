<%@ page import="entity.*, connexion.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Ingrédients</title>
</head>
<body>

 <h1>Ajouter un Ingrédient</h1>
    <form action="IngredientServlet" method="post">
        <label for="nomIngredient">Nom:</label>
        <input type="text" id="nomIngredient" name="nomIngredient" required><br><br>
        <label for="prixIngredient">Prix:</label>
        <input type="number" step="0.01" id="prixIngredient" name="prixIngredient" required><br><br>
        <input type="submit" value="Ajouter">
</form>

    <h1>Liste des Ingrédients</h1>
    <table border="1">
        <thead>
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
</body>
</html>