<%@ page import="ingredient.*, recette.*, java.util.*" %>

<jsp:include page="header.jsp" />




      <div class="container-fluid">
        <div class="container-fluid">

<div class="card">
<div class="card-body">

<form action="fabrication" method="get">
    <div class="mb-3">
        <label class="form-label" for="id_recette">Recette</label>
        <select class="form-select" id="id_recette" name="id_recette" required>
            <% List<Recette> recettes = (List<Recette>) request.getAttribute("recettes");
               if (recettes != null) {
                   for (Recette recette : recettes) { %>
                       <option value="<%= recette.getIdRecette() %>"><%= recette.getNomRecette() %></option>
            <%     }
               } %>
        </select>
    </div>
    <div class="mb-3">
        <label class="form-label" for="id_ingredient">Ingrédient</label>
        <select class="form-select" id="id_ingredient" name="id_ingredient" required>
            <% List<Ingredient> ingredients = (List<Ingredient>) request.getAttribute("ingredients");
               if (ingredients != null) {
                   for (Ingredient ingredient : ingredients) { %>
                       <option value="<%= ingredient.getIdIngredient() %>"><%= ingredient.getNomIngredient() %></option>
            <%     }
               } %>
        </select>
    </div>
    <button type="submit" class="btn btn-primary">Rechercher</button>
</form>

</div>
</div>

<div class="card">
<div class="card-body">
    <div class="table-responsive">
        <table class="table text-nowrap mb-0 align-middle">
            <thead class="text-dark fs-4">
                <tr>
                    <th class="border-bottom-0"><h6 class="fw-semibold mb-0">Produit</h6></th>
                    <th class="border-bottom-0"><h6 class="fw-semibold mb-0">Date</h6></th>
                    <th class="border-bottom-0"><h6 class="fw-semibold mb-0">Quantité</h6></th>
                </tr>
            </thead>
            <tbody>
                <% List<Fabrication> fabrications = (List<Fabrication>) request.getAttribute("fabrications");
                if (fabrications != null && !fabrications.isEmpty()) {
                    for (Fabrication fabrication : fabrications) { %>
                        <tr>
                            <td><%= fabrication.getRecette().getNomRecette() %></td>
                            <td><%= fabrication.getDateFabrication() %></td>
                            <td><%= fabrication.getQuantiteFabrication() %></td>
                        </tr>
                <%     }
                } else { %>
                    <tr>
                        <td colspan="3" class="text-center">Aucune fabrication trouvée.</td>
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
