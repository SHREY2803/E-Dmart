<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="model.Product" %>
<%@ page import="model.Category" %>

<!DOCTYPE html>

<html>

<head>

    <title>E-Dmart | Product Details</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/style.css">

    <link rel="icon"
          type="image/png"
          href="<%= request.getContextPath() %>/assets/images/dmart_favicon.png">

</head>


<body>

<jsp:include page="components/navbar.jsp" />


<div class="container">

    <%
        Product product =
                (Product) request.getAttribute("product");

        Category category =
                (Category) request.getAttribute("category");


        if (product == null) {
    %>

        <div class="empty-state">

            <h2>
                Product Not Found
            </h2>

            <p>
                The product you're looking for could not be found.
            </p>

            <a
                class="btn"
                href="<%= request.getContextPath() %>/products">

                Back to Products

            </a>

        </div>


    <%
        } else {
    %>


    <!-- =====================================================
         PRODUCT DETAILS
    ====================================================== -->

    <div class="details-card">


        <!-- =================================================
             PRODUCT IMAGE
        ================================================== -->

        <div class="details-left">

            <%
                if (product.getImageUrl() != null &&
                    !product.getImageUrl().isEmpty()) {
            %>

                <img
                    class="details-img"
                    src="<%= request.getContextPath()
                            + "/" + product.getImageUrl() %>"
                    alt="<%= product.getName() %>">

            <%
                } else {
            %>

                <div class="product-image-placeholder">
                    No Image Available
                </div>

            <%
                }
            %>

        </div>


        <!-- =================================================
             PRODUCT INFORMATION
        ================================================== -->

        <div class="details-right">


            <h2>
                <%= product.getName() %>
            </h2>


            <!-- Category -->

            <%
                if (category != null) {
            %>

                <p>
                    <strong>Category:</strong>
                    <%= category.getName() %>
                </p>

            <%
                }
            %>


            <!-- Price -->

            <p>
                <strong>Price:</strong>
                ₹ <%= product.getPrice() %>
            </p>


            <!-- Availability -->

            <%
                if (product.getQuantity() > 0) {
            %>

                <p class="stock-available">
                    <strong>Available:</strong>
                    <%= product.getQuantity() %> units
                </p>

            <%
                } else {
            %>

                <p class="stock-unavailable">
                    <strong>Availability:</strong>
                    Out of Stock
                </p>

            <%
                }
            %>


            <!-- Description -->

            <%
                if (product.getDescription() != null &&
                    !product.getDescription().isEmpty()) {
            %>

                <div class="product-description">

                    <h4>
                        Product Description
                    </h4>

                    <p>
                        <%= product.getDescription() %>
                    </p>

                </div>

            <%
                }
            %>


            <!-- =================================================
                 ACTIONS
            ================================================== -->

            <div class="product-detail-actions">


                <%
                    if (product.getQuantity() > 0) {
                %>

                    <a
                        class="btn"
                        href="<%= request.getContextPath() %>/add-to-cart?productId=<%= product.getId() %>">

                        Add to Cart

                    </a>

                <%
                    } else {
                %>

                    <button
                        class="btn"
                        disabled>

                        Out of Stock

                    </button>

                <%
                    }
                %>


                <a
                    class="btn btn-clear"
                    href="<%= request.getContextPath() %>/products">

                    Back to Products

                </a>


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