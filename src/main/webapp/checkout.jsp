<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Cart" %>

<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    <link rel="icon" type="image/png" href="./assets/images/page_favicon.png">
    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/style.css">
          
</head>
<body>

<jsp:include page="components/navbar.jsp" />

<div class="container">

    <h2>Checkout</h2>

<%
    List<Cart> cartItems = (List<Cart>) request.getAttribute("cartItems");
    double total = 0;
%>

<% if (cartItems == null || cartItems.isEmpty()) { %>

    <p>Your cart is empty.</p>

<% } else { %>

    <div class="cart-container">

        <!-- LEFT: ORDER ITEMS -->
        <div class="cart-list">

        <% for (Cart item : cartItems) {
               double subTotal = item.getPrice() * item.getQuantity();
               total += subTotal;
        %>

            <div class="cart-item">

                <img src="<%= request.getContextPath() + "/" + item.getImageUrl() %>"
                     alt="Game Image">

                <div class="cart-details">
                    <h3><%= item.getProductName() %></h3>

                    <div class="cart-meta">
                        <span>₹ <%= item.getPrice() %></span>
                        <span>Qty: <%= item.getQuantity() %></span>
                        <span>Subtotal: ₹ <%= subTotal %></span>
                    </div>
                </div>

            </div>

        <% } %>

        </div>

        <!-- RIGHT: SUMMARY -->
        <div class="cart-summary">
            <h3>Order Summary</h3>

            <p>Total Amount</p>
            <h2>₹ <%= total %></h2>

            <form action="<%= request.getContextPath() %>/place-order"
                  method="post">

                <button type="submit" class="btn checkout-btn">
                    Place Order
                </button>
            </form>

            <% if ("true".equals(request.getParameter("error"))) { %>
                <p style="color:red; margin-top:10px;">
                    Failed to place order. Please try again.
                </p>
            <% } %>

        </div>

    </div>

<% } %>

</div>

<jsp:include page="components/footer.jsp" />

</body>
</html>
