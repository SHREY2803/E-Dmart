<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<%@ page import="model.OrderItem" %>

<!DOCTYPE html>
<html>
<head>
    <title>My Orders</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="icon" type="image/png" href="./../assets/images/page_favicon.png">
    <link rel="icon" type="image/png"
	href="<%= request.getContextPath() %>/assets/images/page_favicon.png">
    <style>
        .order-card {
            background: #1b1e24;
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 25px;
        }

        .order-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .order-status {
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 13px;
            font-weight: bold;
        }

        .PAID {
            background: #2ecc71;
            color: #000;
        }

        .PAYMENT_FAILED {
            background: #e74c3c;
            color: #fff;
        }

        .PLACED {
            background: #f1c40f;
            color: #000;
        }

        .order-items {
            margin-top: 15px;
        }

        .order-item {
            display: flex;
            align-items: center;
            gap: 15px;
            padding: 10px 0;
            border-bottom: 1px solid #2a2d36;
        }

        .order-item:last-child {
            border-bottom: none;
        }

        .order-item img {
            width: 70px;
            height: 70px;
            object-fit: cover;
            border-radius: 8px;
        }

        .item-info {
            flex: 1;
        }

        .item-info h4 {
            margin: 0;
            font-size: 16px;
        }

        .item-info span {
            font-size: 13px;
            color: #aaa;
        }
    </style>
</head>

<body>

<jsp:include page="../components/navbar.jsp" />

<div class="container">

    <h2 style="margin-bottom:25px">📦 My Orders</h2>

    <%
        List<Order> orders = (List<Order>) request.getAttribute("orders");

        if (orders == null || orders.isEmpty()) {
    %>
        <p>You haven’t placed any orders yet.</p>
    <%
        } else {
            for (Order order : orders) {
    %>

    <div class="order-card">

        <div class="order-header">
            <div>
                <h3>Order <%= order.getId() %></h3>
                <p style="margin:5px 0; color:#aaa;">
                    Order Date: <%= order.getOrderDate() %><br>
                    Total: ₹<%= order.getTotalAmount() %>
                </p>
            </div>

            <div class="order-status <%= order.getStatus() %>">
                <%= order.getStatus() %>
            </div>
        </div>

        <div class="order-items">
            <%
                for (OrderItem item : order.getItems()) {
            %>
            <div class="order-item">
                <img src="<%= item.getImageUrl() %>" alt="game">
                <div class="item-info">
                    <h4><%= item.getProductName() %></h4>
                    <span>
                        Quantity: <%= item.getQuantity() %> |
                        Price: ₹<%= item.getPrice() %>
                    </span>
                </div>
            </div>
            <%
                }
            %>
        </div>

    </div>

    <%
            }
        }
    %>

</div>

<jsp:include page="../components/footer.jsp" />

</body>
</html>
