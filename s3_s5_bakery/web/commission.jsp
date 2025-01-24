<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="vente.Commission" %>
<%@ page import="java.sql.Date" %>
<jsp:include page="header.jsp" />

<%
    List<Commission> commissions = (List<Commission>) request.getAttribute("commissions");
%>

<div class="container-fluid">
    <div class="card">
        <div class="card-body">
            <h5 class="card-title fw-semibold mb-4">Filtrer les commissions</h5>
            <div class="card">
                <div class="card-body">
                    <form action="CommissionServlet" method="POST">
                        <div>
                            <label class="form-label" for="startDate">Date de début</label>
                            <input type="date" required class="form-control" id="startDate" name="startDate">
                        </div><br>
                        <div>
                            <label class="form-label" for="endDate">Date de fin</label>
                            <input type="date" required class="form-control" id="endDate" name="endDate">
                        </div><br>
                        <button type="submit" class="btn btn-primary">Filtrer</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="card">
        <div class="card-body">
            <h5 class="card-title fw-semibold mb-4">Liste des commissions</h5>
            <div class="table-responsive">
                <table class="table text-nowrap mb-0 align-middle">
                    <thead class="text-dark fs-4">
                        <tr>
                            <%-- <th class="border-bottom-0"><h6 class="fw-semibold mb-0">Id</h6></th> --%>
                            <th class="border-bottom-0"><h6 class="fw-semibold mb-0">Vendeur</h6></th>
                            <th class="border-bottom-0"><h6 class="fw-semibold mb-0">Montant Commission</h6></th>
                            <%-- <th class="border-bottom-0"><h6 class="fw-semibold mb-0">Date Commission</h6></th> --%>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            if (commissions != null) {
                                for (Commission commission : commissions) {
                        %>
                                    <tr>
                                        <%-- <td><%= commission.getIdCommission() %></td> --%>
                                        <td><%= commission.getVendeur().getNomVendeur() %></td>
                                        <td><%= commission.getMontantCommission() %></td>
                                        <%-- <td><%= commission.getDateCommission() %></td> --%>
                                    </tr>
                        <%
                                }
                            }
                        %>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>

<jsp:include page="footer.jsp" />
