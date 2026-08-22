<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.OrderItem" %>

<!DOCTYPE html>
<html>
<head>
    <title>My Game Library</title>
    <link rel="stylesheet" href="../assets/css/style.css">
    <link rel="icon" type="image/png"
	href="<%= request.getContextPath() %>/assets/images/page_favicon.png">
</head>

<body>

<jsp:include page="../components/navbar.jsp" />

<div class="container">

    <h2>My Game Library</h2>

    <%
        List<OrderItem> games = (List<OrderItem>) request.getAttribute("games");

        if (games == null || games.isEmpty()) {
    %>
        <p>You have not purchased any games yet.</p>
    <%
        } else {
    %>

    <div class="game-grid">
        <%
            for (OrderItem game : games) {
        %>
            <div class="game-card">
                <img src="<%= game.getImageUrl() %>" alt="<%= game.getProductName() %>">
                <h3><%= game.getProductName() %></h3>
                <p>Purchased at ₹ <%= game.getPrice() %></p>

                <button class="btn" disabled>Owned</button>
            </div>
        <%
            }
        %>
    </div>

    <%
        }
    %>

</div>

<jsp:include page="../components/footer.jsp" />

</body>
</html>