<%@ page import="entity.*, connexion.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Ingrédients</title>
</head>
<body>
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