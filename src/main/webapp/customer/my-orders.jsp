<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Order" %>
<%@ page import="model.OrderItem" %>

<!DOCTYPE html>
<html>

<head>

    <title>My Orders</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/style.css">

    <link rel="icon"
          type="image/png"
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
            align-items: flex-start;
            gap: 20px;
        }


        .order-header h3 {
            margin-top: 0;
        }


        .order-meta {
            color: #aaa;
            line-height: 1.7;
        }


        .order-status {
            padding: 7px 14px;
            border-radius: 20px;
            font-size: 13px;
            font-weight: bold;
            white-space: nowrap;
        }


        /* ==========================================
           ORDER STATUS COLORS
           ========================================== */

        .PLACED {
            background: #f1c40f;
            color: #000;
        }

        .CONFIRMED {
            background: #3498db;
            color: #fff;
        }

        .PREPARING {
            background: #9b59b6;
            color: #fff;
        }

        .READY_FOR_PICKUP {
            background: #1abc9c;
            color: #000;
        }

        .OUT_FOR_DELIVERY {
            background: #e67e22;
            color: #fff;
        }

        .DELIVERED {
            background: #2ecc71;
            color: #000;
        }

        .COMPLETED {
            background: #27ae60;
            color: #fff;
        }

        .CANCELLED {
            background: #e74c3c;
            color: #fff;
        }


        /* ==========================================
           ORDER PROGRESS
           ========================================== */

        .order-progress {
            display: flex;
            align-items: flex-start;
            justify-content: space-between;
            margin: 30px 10px 25px;
            position: relative;
        }


        .progress-line {
            position: absolute;
            top: 15px;
            left: 5%;
            right: 5%;
            height: 3px;
            background: #3a3d45;
            z-index: 0;
        }


        .progress-step {
            position: relative;
            z-index: 1;
            text-align: center;
            width: 20%;
        }


        .progress-circle {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            background: #3a3d45;
            color: #aaa;

            display: flex;
            align-items: center;
            justify-content: center;

            margin: 0 auto 8px;

            font-size: 14px;
            font-weight: bold;
        }


        .progress-step.completed .progress-circle {
            background: #2ecc71;
            color: #000;
        }


        .progress-step.current .progress-circle {
            background: #3498db;
            color: #fff;
            box-shadow: 0 0 0 4px rgba(52, 152, 219, 0.2);
        }


        .progress-label {
            font-size: 12px;
            color: #aaa;
            white-space: nowrap;
        }


        .progress-step.completed .progress-label,
        .progress-step.current .progress-label {
            color: #fff;
            font-weight: bold;
        }


        .cancelled-message {
            margin: 20px 0;
            padding: 12px 15px;
            background: rgba(231, 76, 60, 0.15);
            border-left: 4px solid #e74c3c;
            color: #ff8b80;
            border-radius: 5px;
        }


        /* ==========================================
           ORDER ITEMS
           ========================================== */

        .order-items {
            margin-top: 20px;
        }


        .order-item {
            display: flex;
            align-items: center;
            gap: 15px;
            padding: 12px 0;
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
            margin: 0 0 6px 0;
            font-size: 16px;
        }


        .item-info span {
            font-size: 13px;
            color: #aaa;
        }


        .order-actions {
            margin-top: 18px;
        }


        /* ==========================================
           MOBILE
           ========================================== */

        @media (max-width: 700px) {

            .order-header {
                flex-direction: column;
            }


            .order-progress {
                margin-left: 0;
                margin-right: 0;
            }


            .progress-label {
                font-size: 9px;
            }


            .progress-circle {
                width: 25px;
                height: 25px;
                font-size: 12px;
            }


            .progress-line {
                top: 12px;
            }

        }

    </style>

</head>


<body>

<jsp:include page="../components/navbar.jsp" />


<div class="container">

    <h2 style="margin-bottom:25px">
        📦 My Orders
    </h2>


<%
    List<Order> orders =
            (List<Order>) request.getAttribute("orders");


    if (orders == null || orders.isEmpty()) {
%>

    <p>You haven't placed any orders yet.</p>

<%
    } else {

        for (Order order : orders) {

            String status = order.getStatus();

            boolean isPickup =
                    "PICKUP".equals(order.getFulfillmentType());

            int currentStep = 0;


            // ==========================================
            // DELIVERY ORDER PROGRESS
            // ==========================================

            if (!isPickup) {

                if ("PLACED".equals(status)) {
                    currentStep = 1;

                } else if ("CONFIRMED".equals(status)) {
                    currentStep = 2;

                } else if ("PREPARING".equals(status)) {
                    currentStep = 3;

                } else if ("OUT_FOR_DELIVERY".equals(status)) {
                    currentStep = 4;

                } else if ("DELIVERED".equals(status)
                        || "COMPLETED".equals(status)) {

                    currentStep = 5;
                }

            }

            // ==========================================
            // PICKUP ORDER PROGRESS
            // ==========================================

            else {

                if ("PLACED".equals(status)) {
                    currentStep = 1;

                } else if ("CONFIRMED".equals(status)) {
                    currentStep = 2;

                } else if ("PREPARING".equals(status)) {
                    currentStep = 3;

                } else if ("READY_FOR_PICKUP".equals(status)) {
                    currentStep = 4;

                } else if ("COMPLETED".equals(status)) {
                    currentStep = 5;
                }

            }

%>


    <!-- ========================================= -->
    <!-- ORDER CARD -->
    <!-- ========================================= -->

    <div class="order-card">


        <!-- ========================================= -->
        <!-- ORDER HEADER -->
        <!-- ========================================= -->

        <div class="order-header">

            <div>

                <h3>
                    Order #<%= order.getId() %>
                </h3>


                <div class="order-meta">

                    <strong>Order Date:</strong>
                    <%= order.getOrderDate() %>

                    <br>


                    <strong>Total:</strong>
                    ₹<%= String.format(
                            "%.2f",
                            order.getTotalAmount()
                    ) %>

                    <br>


                    <strong>Fulfillment:</strong>
                    <%= order.getFulfillmentType() %>

                </div>

            </div>


            <!-- STATUS -->

            <div class="order-status <%= status %>">

                <%= status %>

            </div>

        </div>


<%
        // ==========================================
        // CANCELLED ORDER
        // ==========================================

        if ("CANCELLED".equals(status)) {
%>

        <div class="cancelled-message">

            ❌ This order has been cancelled.

        </div>

<%
        } else {
%>


        <!-- ========================================= -->
        <!-- ORDER PROGRESS -->
        <!-- ========================================= -->

        <div class="order-progress">

            <div class="progress-line"></div>


            <!-- STEP 1 -->

            <div class="progress-step
                <%= currentStep >= 1 ? "completed" : "" %>
                <%= currentStep == 1 ? "current" : "" %>">

                <div class="progress-circle">
                    <%= currentStep >= 1 ? "✓" : "1" %>
                </div>

                <div class="progress-label">
                    Placed
                </div>

            </div>


            <!-- STEP 2 -->

            <div class="progress-step
                <%= currentStep >= 2 ? "completed" : "" %>
                <%= currentStep == 2 ? "current" : "" %>">

                <div class="progress-circle">
                    <%= currentStep >= 2 ? "✓" : "2" %>
                </div>

                <div class="progress-label">
                    Confirmed
                </div>

            </div>


            <!-- STEP 3 -->

            <div class="progress-step
                <%= currentStep >= 3 ? "completed" : "" %>
                <%= currentStep == 3 ? "current" : "" %>">

                <div class="progress-circle">
                    <%= currentStep >= 3 ? "✓" : "3" %>
                </div>

                <div class="progress-label">
                    Preparing
                </div>

            </div>


            <!-- STEP 4 -->

            <div class="progress-step
                <%= currentStep >= 4 ? "completed" : "" %>
                <%= currentStep == 4 ? "current" : "" %>">

                <div class="progress-circle">
                    <%= currentStep >= 4 ? "✓" : "4" %>
                </div>

                <div class="progress-label">

                    <%= isPickup
                            ? "Ready for Pickup"
                            : "Out for Delivery" %>

                </div>

            </div>


            <!-- STEP 5 -->

            <div class="progress-step
                <%= currentStep >= 5 ? "completed" : "" %>
                <%= currentStep == 5 ? "current" : "" %>">

                <div class="progress-circle">
                    <%= currentStep >= 5 ? "✓" : "5" %>
                </div>

                <div class="progress-label">

                    <%= isPickup
                            ? "Completed"
                            : "Delivered" %>

                </div>

            </div>

        </div>


<%
        }
%>


        <!-- ========================================= -->
        <!-- ORDER ITEMS -->
        <!-- ========================================= -->

        <div class="order-items">

<%
            List<OrderItem> items =
                    order.getItems();


            if (items != null
                    && !items.isEmpty()) {

                for (OrderItem item : items) {
%>


            <div class="order-item">


                <!-- PRODUCT IMAGE -->

                <img
                    src="<%= request.getContextPath()
                            + "/"
                            + item.getImageUrl() %>"
                    alt="Product">


                <!-- PRODUCT DETAILS -->

                <div class="item-info">

                    <h4>
                        <%= item.getProductName() %>
                    </h4>


                    <span>

                        Quantity:
                        <%= item.getQuantity() %>

                        &nbsp; | &nbsp;

                        Price:
                        ₹<%= String.format(
                                "%.2f",
                                item.getPrice()
                        ) %>

                    </span>

                </div>


            </div>


<%
                }

            } else {
%>

            <p>No items found for this order.</p>

<%
            }
%>

        </div>


        <!-- ========================================= -->
        <!-- ACTIONS -->
        <!-- ========================================= -->

        <div class="order-actions">

            <a
                class="btn"
                href="<%= request.getContextPath() %>/my-orders">

                Refresh Orders

            </a>

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