<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Cart" %>

<!DOCTYPE html>
<html>

<head>

    <title>E-Dmart | Checkout</title>

    <link rel="icon"
          type="image/png"
          href="<%= request.getContextPath() %>/assets/images/dmart_favicon.png">

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/style.css">

</head>

<body>

<jsp:include page="components/navbar.jsp" />


<div class="container">

    <h2 class="page-title">
        Checkout
    </h2>


<%
    List<Cart> cartItems =
            (List<Cart>) request.getAttribute("cartItems");

    double total = 0;
%>


<% if (cartItems == null || cartItems.isEmpty()) { %>


    <!-- =====================================================
         EMPTY CART
    ====================================================== -->

    <div class="empty-state">

        <h3>
            Your Cart is Empty
        </h3>

        <p>
            Add some products before proceeding to checkout.
        </p>

        <a
            class="btn"
            href="<%= request.getContextPath() %>/products">

            Browse Products

        </a>

    </div>


<% } else { %>


    <div class="cart-container">


        <!-- =================================================
             LEFT : ORDER ITEMS
        ================================================== -->

        <div class="cart-list">


        <%
            for (Cart item : cartItems) {

                double subTotal =
                        item.getPrice()
                        * item.getQuantity();

                total += subTotal;
        %>


            <div class="cart-item">


                <!-- Product Image -->

                <img
                    src="<%= request.getContextPath()
                            + "/"
                            + item.getImageUrl() %>"
                    alt="<%= item.getProductName() %>">


                <!-- Product Details -->

                <div class="cart-details">

                    <h3>
                        <%= item.getProductName() %>
                    </h3>


                    <div class="cart-meta">

                        <span>
                            Price: ₹ <%= item.getPrice() %>
                        </span>

                        <span>
                            Qty: <%= item.getQuantity() %>
                        </span>

                        <span>
                            Subtotal: ₹ <%= subTotal %>
                        </span>

                    </div>

                </div>

            </div>


        <%
            }
        %>


        </div>


        <!-- =================================================
             RIGHT : ORDER SUMMARY
        ================================================== -->

        <div class="cart-summary">


            <h3>
                Order Summary
            </h3>


            <div class="summary-row">

                <span>
                    Items
                </span>

                <span>
                    <%= cartItems.size() %>
                </span>

            </div>


            <div class="summary-divider"></div>


            <p>
                Total Amount
            </p>


            <h2>
                ₹ <%= total %>
            </h2>


            <!-- Place Order -->

            <form
                action="<%= request.getContextPath() %>/place-order"
                method="post">

                <button
                    type="submit"
                    class="btn checkout-btn">

                    Place Order

                </button>

            </form>


            <!-- Error -->

            <% if ("true".equals(request.getParameter("error"))) { %>

                <p class="checkout-error">
                    Failed to place order.
                    Please try again.
                </p>

            <% } %>


        </div>


    </div>


<% } %>


</div>


<jsp:include page="components/footer.jsp" />

</body>

</html>