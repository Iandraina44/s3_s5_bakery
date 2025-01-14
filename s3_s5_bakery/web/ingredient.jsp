<jsp:include page="header.jsp" />

<%@ page import="ingredient.* " %>
<%@ page import="java.util.* " %>

<%


String action = (String)request.getAttribute("action");

String nomIngredient = "";
String prixIngredient = "";
String idIngredient = "";


if(action != null ){
    if(action.equals("update")){
        Ingredient ingredient = (Ingredient)request.getAttribute("ingredient");   
        idIngredient=""+ingredient.getIdIngredient();
        nomIngredient=""+ingredient.getNomIngredient();
        prixIngredient=""+ingredient.getPrixIngredient();
    }
}



%>

      <div class="container-fluid">
        <div class="container-fluid">
    
          <div class="card">
            <div class="card-body">
              <h5 class="card-title fw-semibold mb-4">Ingredient</h5>
              <div class="card">
                <div class="card-body">
                  <form action="ingredient" method="post" >
                  <input type="hidden" name="id_ingredient" value="<%=idIngredient %>">
                  <input type="hidden" name="action" value="<%=action %>">
                  
                    <div class="mb-3">
                      <label class="form-label">Nom Ingredient</label>
                      <input type="text" class="form-control"  required name="nom_ingredient" value="<%=nomIngredient  %>">
                    </div>
                    <div class="mb-3">
                      <label class="form-label">Prix Ingredient</label>
                      <input type="text" class="form-control" min="1"  required name="prix_ingredient" value="<%=prixIngredient %>"  >
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                  </form>
                </div>
              </div>
            </div>
          </div>
        

            <% 

            List<Ingredient> ingredients = (List<Ingredient>)request.getAttribute("ingredients");
            
            %>
          <div class="card">
            <div class="card-body">
              <h5 class="card-title fw-semibold mb-4">Liste des Ingredients</h5>
              <div class="card">
                <div class="card-body">                  
                    <!-- TABLE -->

                <div class="table-responsive">
                  <table class="table text-nowrap mb-0 align-middle">
                    <thead class="text-dark fs-4">
                      <tr>
                        <th class="border-bottom-0">
                          <h6 class="fw-semibold mb-0">Id</h6>
                        </th>
                        <th class="border-bottom-0">
                          <h6 class="fw-semibold mb-0">Nom Ingredient</h6>
                        </th>
                        <th class="border-bottom-0">
                          <h6 class="fw-semibold mb-0">Prix Ingredient</h6>
                        </th>
                        <th class="border-bottom-0">
                          <h6 class="fw-semibold mb-0">Action</h6>
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                    <%  for (Ingredient ingredient:ingredients ) { %>
                      <tr>
                        <td class="border-bottom-0">
                          <h6 class="fw-semibold mb-0"><%=ingredient.getIdIngredient() %></h6>
                        </td>
                        <td class="border-bottom-0">
                            <p class="mb-0 fw-normal"><%=ingredient.getNomIngredient() %></p>
                        </td>
                        <td class="border-bottom-0">
                          <p class="mb-0 fw-normal"><%=ingredient.getPrixIngredient() %></p>
                        </td>
                        <td class="border-bottom-0">
                          <a href="ingredient?action=update&id_ingredient=<%=ingredient.getIdIngredient() %>" class="btn btn-secondary">Update</a>
                        </td>
                        <td class="border-bottom-0">
                          <a href="ingredient?action=delete&id_ingredient=<%=ingredient.getIdIngredient() %>" class="btn btn-danger">Delete</a>
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
