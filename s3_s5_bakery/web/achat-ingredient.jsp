<jsp:include page="header.jsp" />

<%@ page import="ingredient.* " %>
<%@ page import="java.util.* " %>

<% 
  List<Ingredient> ingredients = (List<Ingredient>) request.getAttribute("ingredients");
  List<AchatIngredient> achatIngredients = (List<AchatIngredient>) request.getAttribute("achat-ingredients");
%>

<div class="container-fluid">
  <div class="container-fluid">
    <!-- Ingredient Form -->
    <div class="card">
      <div class="card-body">
        <h5 class="card-title fw-semibold mb-4">Ingredient</h5>
        <div class="card">
          <div class="card-body">
            <form action="achat-ingredient" method="post">
              <div class="mb-3">
                <label class="form-label">Choisir Ingredient</label>
                <select class="form-select" name="id_ingredient">
                  <% for (Ingredient ingredient : ingredients) { %>
                    <option value="<%= ingredient.getIdIngredient() %>"><%= ingredient.getNomIngredient() %></option>
                  <% } %>
                </select>
              </div>
              <div class="mb-3">
                <label class="form-label">Quantite</label>
                <input type="number" min="1" class="form-control" required name="quantite_achat" value="1.00">
              </div>
              <div class="mb-3">
                <label class="form-label">Date Achat</label>
                <input type="datetime-local" name="date_achat" class="form-control" required>
              </div>
              <button type="submit" class="btn btn-primary">Submit</button>
            </form>
          </div>
        </div>
      </div>
    </div>

 

    <!-- AchatIngredient List Table -->
    <div class="card">
      <div class="card-body">
        <h5 class="card-title fw-semibold mb-4">Liste des Achat Ingredients</h5>
        <div class="card">
          <div class="card-body">
            <div class="table-responsive">
              <table class="table text-nowrap mb-0 align-middle">
                <thead class="text-dark fs-4">
                  <tr>
                    <th class="border-bottom-0"><h6 class="fw-semibold mb-0">Id Achat</h6></th>
                    <th class="border-bottom-0"><h6 class="fw-semibold mb-0">Ingredient</h6></th>
                    <th class="border-bottom-0"><h6 class="fw-semibold mb-0">Quantité</h6></th>
                    <th class="border-bottom-0"><h6 class="fw-semibold mb-0">Date Achat</h6></th>
                    <th class="border-bottom-0"><h6 class="fw-semibold mb-0">Action</h6></th>
                  </tr>
                </thead>
                <tbody>
                  <% for (AchatIngredient achatIngredient : achatIngredients) { %>
                    <tr>
                      <td class="border-bottom-0"><h6 class="fw-semibold mb-0"><%= achatIngredient.getIdAchatIngredient() %></h6></td>
                      <td class="border-bottom-0"><p class="mb-0 fw-normal"><%= achatIngredient.getIngredient().getNomIngredient() %></p></td>
                      <td class="border-bottom-0"><p class="mb-0 fw-normal"><%= achatIngredient.getQuantiteAchat() %></p></td>
                      <td class="border-bottom-0"><p class="mb-0 fw-normal"><%= achatIngredient.getDateAchatIngredient() %></p></td>
                      <td class="border-bottom-0">
                        <a href="achatIngredient?action=delete&id_achat_ingredient=<%= achatIngredient.getIdAchatIngredient() %>" class="btn btn-danger">Delete</a>
                      </td>
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
