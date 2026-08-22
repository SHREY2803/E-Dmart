<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>

<!DOCTYPE html>
<html>
<head>
  <title>GameStore | Store</title>
  <link rel="stylesheet" href="assets/css/style.css">
  <link rel="icon" type="image/png" href="./assets/images/page_favicon.png">
</head>

<body>

<jsp:include page="components/navbar.jsp" />

<div class="container">
  <h2>All Games</h2>

  <div class="game-grid">

    <%
      List<Product> products = (List<Product>) request.getAttribute("products");

      if (products != null && !products.isEmpty()) {
        for (Product p : products) {
    %>

      <div class="game-card">
      
        <img src="<%= p.getImageUrl() %>" alt="Game Image">
        <h3><%= p.getName() %></h3>
        <p><%= p.getCategory() %></p>
        <p>₹ <%= p.getPrice() %></p>

        <a class="btn" href="product-details?id=<%= p.getId() %>">View Details</a>
        <a class="btn" href="add-to-cart?productId=<%= p.getId() %>">Add to Cart</a>

      </div>	

    <%
        }
      } else {
    %>

      <p>No products available.</p>

    <%
      }
    %>

  </div>
</div>

<jsp:include page="components/footer.jsp" />

</body>
</html>
