<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Cart" %>

<!DOCTYPE html>
<html>

<head>

    <title>E-Dmart | Your Cart</title>

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
        Your Cart
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

    <div class="empty-state cart-empty">

        <h3>
            Your Cart is Empty
        </h3>

        <p>
            Browse our products and add something to your cart.
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
             LEFT SIDE : CART ITEMS
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


                    <!-- Remove -->

                    <form
                        action="<%= request.getContextPath() %>/remove-from-cart"
                        method="post"
                        class="cart-remove-form">

                        <input
                            type="hidden"
                            name="cartId"
                            value="<%= item.getId() %>">

                        <button
                            class="btn btn-remove"
                            type="submit">

                            Remove

                        </button>

                    </form>


                </div>

            </div>


        <%
            }
        %>


        </div>


        <!-- =================================================
             RIGHT SIDE : ORDER SUMMARY
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


            <a
                class="btn checkout-btn"
                href="<%= request.getContextPath() %>/checkout">

                Proceed to Checkout

            </a>


        </div>


    </div>


<% } %>


</div>


<jsp:include page="components/footer.jsp" />

</body>

</html>