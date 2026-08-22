<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Product" %>

<!DOCTYPE html>
<html>
<head>
    <title>GameStore | Product Details</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="icon" type="image/png" href="./assets/images/page_favicon.png">
</head>

<body>

<jsp:include page="components/navbar.jsp" />

<div class="container">

    <%
        Product product = (Product) request.getAttribute("product");

        if (product == null) {
    %>
        <h2>Product not found</h2>
        <a class="btn" href="products">Back to Store</a>
    <%
        } else {
    %>

    <div class="details-card">
        <div class="details-left">
            <img class="details-img"
                 src="<%= request.getContextPath() + "/" + product.getImageUrl() %>"
                 alt="Game Image">
        </div>

        <div class="details-right">
            <h2><%= product.getName() %></h2>
            <p><b>Category:</b> <%= product.getCategory() %></p>
            <p><b>Price:</b> ₹ <%= product.getPrice() %></p>
            <p><b>Available:</b> <%= product.getQuantity() %></p>

            <p style="margin-top: 12px;">
                <%= product.getDescription() %>
            </p>

            <div style="margin-top: 18px;">
                <a class="btn" href="add-to-cart?productId=<%= product.getId() %>">Add to Cart</a>

                <a class="btn" href="products">Back</a>
            </div>
        </div>
    </div>

    <%
        }
    %>

</div>

<jsp:include page="components/footer.jsp" />

</body>
</html>
