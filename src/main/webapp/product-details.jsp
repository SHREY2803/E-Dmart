<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="model.Product" %>
<%@ page import="model.Category" %>

<!DOCTYPE html>

<html>

<head>

    <title>GameStore | Product Details</title>

    <link rel="stylesheet"
          href="assets/css/style.css">

    <link rel="icon"
          type="image/png"
          href="./assets/images/page_favicon.png">

</head>


<body>

<jsp:include page="components/navbar.jsp" />


<div class="container">

    <%
        Product product =
                (Product)
                request.getAttribute("product");

        Category category =
                (Category)
                request.getAttribute("category");


        if (product == null) {
    %>


        <h2>
            Product not found
        </h2>

        <a
            class="btn"
            href="products">

            Back to Store

        </a>


    <%
        } else {
    %>


    <div class="details-card">


        <!-- ============================= -->
        <!-- PRODUCT IMAGE -->
        <!-- ============================= -->

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
                }
            %>

        </div>


        <!-- ============================= -->
        <!-- PRODUCT INFORMATION -->
        <!-- ============================= -->

        <div class="details-right">


            <h2>
                <%= product.getName() %>
            </h2>


            <!-- Category -->

            <%
                if (category != null) {
            %>

                <p>
                    <b>Category:</b>
                    <%= category.getName() %>
                </p>

            <%
                }
            %>


            <!-- Price -->

            <p>

                <b>Price:</b>

                ₹ <%= product.getPrice() %>

            </p>


            <!-- Stock -->

            <p>

                <b>Available:</b>

                <%= product.getQuantity() %>

            </p>


            <!-- Description -->

            <p style="margin-top:12px;">

                <%= product.getDescription() %>

            </p>


            <!-- ============================= -->
            <!-- ACTIONS -->
            <!-- ============================= -->

            <div style="margin-top:18px;">


                <%
                    if (product.getQuantity() > 0) {
                %>


                    <a
                        class="btn"
                        href="add-to-cart?productId=<%= product.getId() %>">

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
                    class="btn"
                    href="products">

                    Back

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