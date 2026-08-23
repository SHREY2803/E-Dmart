<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Order, model.OrderItem"%>

<!DOCTYPE html>
<html>
<head>
    <title>View Orders</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/style.css">
</head>

<body>

<jsp:include page="../components/navbar.jsp"/>

<div class="container">

    <h2 class="page-title">Customer Orders</h2>

    <%
        List<Order> orders =
            (List<Order>) request.getAttribute("orders");
    %>

    <% if (orders == null || orders.isEmpty()) { %>

        <p>No orders found.</p>

    <% } else { %>

        <% for (Order o : orders) { %>

        <div class="order-card">

            <!-- ============================= -->
            <!-- ORDER HEADER -->
            <!-- ============================= -->

            <div class="order-header">

                <div>
                    <span class="order-id">
                        Order #<%= o.getId() %>
                    </span>
                </div>

                <span class="status-badge <%= o.getStatus().toLowerCase() %>">
                    <%= o.getStatus() %>
                </span>

            </div>


            <!-- ============================= -->
            <!-- ORDER DETAILS -->
            <!-- ============================= -->

            <div class="order-meta">

                <div>
                    <strong>User:</strong>
                    <%= o.getUserName() %>
                </div>

                <div>
                    <strong>Date:</strong>
                    <%= o.getOrderDate() %>
                </div>

                <div>
                    <strong>Total:</strong>
                    ₹ <%= o.getTotalAmount() %>
                </div>

                <div>
                    <strong>Fulfillment:</strong>
                    <%= o.getFulfillmentType() %>
                </div>

            </div>


            <!-- ============================= -->
            <!-- ORDER ITEMS -->
            <!-- ============================= -->

            <div class="order-items">

                <table>

                    <thead>
                        <tr>
                            <th>Product</th>
                            <th>Qty</th>
                            <th>Price</th>
                        </tr>
                    </thead>

                    <tbody>

                    <%
                        if (o.getItems() != null) {

                            for (OrderItem item : o.getItems()) {
                    %>

                        <tr>

                            <td>
                                <%= item.getProductName() %>
                            </td>

                            <td>
                                <%= item.getQuantity() %>
                            </td>

                            <td>
                                ₹ <%= item.getPrice() %>
                            </td>

                        </tr>

                    <%
                            }
                        }
                    %>

                    </tbody>

                </table>

            </div>


            <!-- ============================= -->
            <!-- UPDATE ORDER STATUS -->
            <!-- ============================= -->

            <div class="order-status-update">

                <form
                    action="<%= request.getContextPath() %>/admin/update-order-status"
                    method="post">

                    <!-- Order ID -->

                    <input
                        type="hidden"
                        name="orderId"
                        value="<%= o.getId() %>"
                    >


                    <label for="status-<%= o.getId() %>">
                        <strong>Update Status:</strong>
                    </label>


                    <select
                        id="status-<%= o.getId() %>"
                        name="status"
                    >

                        <option value="PLACED"
                            <%= "PLACED".equals(o.getStatus()) ? "selected" : "" %>>
                            Placed
                        </option>

                        <option value="CONFIRMED"
                            <%= "CONFIRMED".equals(o.getStatus()) ? "selected" : "" %>>
                            Confirmed
                        </option>

                        <option value="PREPARING"
                            <%= "PREPARING".equals(o.getStatus()) ? "selected" : "" %>>
                            Preparing
                        </option>

                        <option value="READY_FOR_PICKUP"
                            <%= "READY_FOR_PICKUP".equals(o.getStatus()) ? "selected" : "" %>>
                            Ready for Pickup
                        </option>

                        <option value="OUT_FOR_DELIVERY"
                            <%= "OUT_FOR_DELIVERY".equals(o.getStatus()) ? "selected" : "" %>>
                            Out for Delivery
                        </option>

                        <option value="DELIVERED"
                            <%= "DELIVERED".equals(o.getStatus()) ? "selected" : "" %>>
                            Delivered
                        </option>

                        <option value="COMPLETED"
                            <%= "COMPLETED".equals(o.getStatus()) ? "selected" : "" %>>
                            Completed
                        </option>

                        <option value="CANCELLED"
                            <%= "CANCELLED".equals(o.getStatus()) ? "selected" : "" %>>
                            Cancelled
                        </option>

                    </select>


                    <button
                        type="submit"
                        class="btn">
                        Update Status
                    </button>

                </form>

            </div>

        </div>

        <% } %>

    <% } %>

</div>

</body>
</html>