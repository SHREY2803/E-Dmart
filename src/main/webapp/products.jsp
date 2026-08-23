<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page import="model.Category" %>

<!DOCTYPE html>

<html>

<head>

    <title>GameStore | Store</title>

    <link rel="stylesheet"
          href="assets/css/style.css">

    <link rel="icon"
          type="image/png"
          href="./assets/images/page_favicon.png">

</head>

<body>

<jsp:include page="components/navbar.jsp" />


<div class="container">

    <h2>All Products</h2>


    <%
        List<Product> products =
                (List<Product>)
                request.getAttribute("products");

        List<Category> categories =
                (List<Category>)
                request.getAttribute("categories");
    %>


    <!-- ============================= -->
    <!-- CATEGORY FILTER -->
    <!-- ============================= -->

    <div style="margin-bottom: 25px;">

        <label for="category">
            Filter by Category:
        </label>

        <select
                id="category"
                class="input-box"
                style="max-width:300px;"
                onchange="filterCategory(this.value)">

            <option value="">
                All Categories
            </option>

            <%
                if (categories != null) {

                    for (Category category : categories) {
            %>

                <option value="<%= category.getId() %>">
                    <%= category.getName() %>
                </option>

            <%
                    }
                }
            %>

        </select>

    </div>


    <!-- ============================= -->
    <!-- PRODUCT GRID -->
    <!-- ============================= -->

    <div class="game-grid">

        <%
            if (products != null &&
                    !products.isEmpty()) {

                for (Product p : products) {
        %>

        <div class="game-card">

            <!-- Product Image -->

            <%
                if (p.getImageUrl() != null &&
                        !p.getImageUrl().isEmpty()) {
            %>

                <img
                    src="<%= request.getContextPath()
                            + "/" + p.getImageUrl() %>"
                    alt="<%= p.getName() %>">

            <%
                }
            %>


            <!-- Product Name -->

            <h3>
                <%= p.getName() %>
            </h3>


            <!-- Price -->

            <p>
                ₹ <%= p.getPrice() %>
            </p>


            <!-- Stock -->

            <%
                if (p.getQuantity() > 0) {
            %>

                <p>
                    In Stock:
                    <%= p.getQuantity() %>
                </p>

            <%
                } else {
            %>

                <p>
                    Out of Stock
                </p>

            <%
                }
            %>


            <!-- Product Details -->

            <a
                class="btn"
                href="product-details?id=<%= p.getId() %>">

                View Details

            </a>


            <!-- Add To Cart -->

            <%
                if (p.getQuantity() > 0) {
            %>

                <a
                    class="btn"
                    href="add-to-cart?productId=<%= p.getId() %>">

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

        </div>


        <%
                }

            } else {
        %>

            <p>
                No products available.
            </p>

        <%
            }
        %>

    </div>

</div>


<script>

function filterCategory(categoryId) {

    if (categoryId === "") {

        window.location.href =
            "<%= request.getContextPath() %>/products";

    } else {

        window.location.href =
            "<%= request.getContextPath() %>/products?category="
            + categoryId;
    }
}

</script>


<jsp:include page="components/footer.jsp" />

</body>

</html>