<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Order, model.OrderItem"%>

<!DOCTYPE html>
<html>
<head>
  <title>View Orders</title>
  <link rel="stylesheet" href="../assets/css/style.css">
</head>

<body>
<jsp:include page="../components/navbar.jsp"/>

<div class="container">

    <h2 class="page-title">Customer Orders</h2>

    <%
        List<Order> orders = (List<Order>) request.getAttribute("orders");
        for (Order o : orders) {
    %>

    <div class="order-card">

        <div class="order-header">
            <div>
                <span class="order-id">Order #<%= o.getId() %></span>
            </div>

            <span class="status-badge <%= o.getStatus().toLowerCase() %>">
                <%= o.getStatus() %>
            </span>
        </div>

        <div class="order-meta">
            <div><strong>User:</strong> <%= o.getUserName() %></div>
            <div><strong>Date:</strong> <%= o.getOrderDate() %></div>
            <div><strong>Total:</strong> ₹ <%= o.getTotalAmount() %></div>
        </div>

        <div class="order-items">
            <table>
                <thead>
                    <tr>
                        <th>Game</th>
                        <th>Qty</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (OrderItem item : o.getItems()) {
                %>
                    <tr>
                        <td><%= item.getProductName() %></td>
                        <td><%= item.getQuantity() %></td>
                        <td>₹ <%= item.getPrice() %></td>
                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>

    </div>

    <% } %>
</div>


</body>
</html>
