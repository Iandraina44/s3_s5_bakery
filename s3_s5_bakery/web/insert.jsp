<%@ page import="entity.*, connexion.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un Ingrédient</title>
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
</body>
</html>