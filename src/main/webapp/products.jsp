<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page import="model.Category" %>

<!DOCTYPE html>

<html>

<head>

    <title>E-Dmart | Products</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/style.css">

    <link rel="icon"
          type="image/png"
          href="<%= request.getContextPath() %>/assets/images/dmart_favicon.png">

</head>


<body>

<jsp:include page="components/navbar.jsp" />


<div class="container">

    <h2 class="page-title">
        All Products
    </h2>


    <%
        List<Product> products =
                (List<Product>)
                request.getAttribute("products");

        List<Category> categories =
                (List<Category>)
                request.getAttribute("categories");

        String search =
                (String)
                request.getAttribute("search");

        Integer selectedCategory =
                (Integer)
                request.getAttribute("selectedCategory");
    %>


    <!-- =====================================================
         SEARCH & CATEGORY FILTER
    ====================================================== -->

    <form
        action="<%= request.getContextPath() %>/products"
        method="get"
        class="product-filter-form">


        <!-- Search -->

        <input
            class="input-box"
            type="text"
            name="search"
            value="<%= search != null ? search : "" %>"
            placeholder="Search products...">


        <!-- Category -->

        <select
            class="input-box"
            name="category">

            <option value="">
                All Categories
            </option>


            <%
                if (categories != null) {

                    for (Category category :
                            categories) {

                        boolean selected =
                                selectedCategory != null
                                && selectedCategory
                                    == category.getId();
            %>

                <option
                    value="<%= category.getId() %>"
                    <%= selected ? "selected" : "" %>>

                    <%= category.getName() %>

                </option>

            <%
                    }
                }
            %>

        </select>


        <!-- Search Button -->

        <button
            class="btn"
            type="submit">

            Search

        </button>


        <!-- Clear Button -->

        <a
            class="btn btn-clear"
            href="<%= request.getContextPath() %>/products">

            Clear

        </a>

    </form>


    <!-- =====================================================
         PRODUCT GRID
    ====================================================== -->

    <div class="game-grid">


        <%
            if (products != null
                    && !products.isEmpty()) {

                for (Product p : products) {
        %>


        <div class="game-card">


            <!-- Product Image -->

            <%
                if (p.getImageUrl() != null
                        && !p.getImageUrl().isEmpty()) {
            %>

                <img
                    src="<%= request.getContextPath()
                            + "/" + p.getImageUrl() %>"
                    alt="<%= p.getName() %>">

            <%
                } else {
            %>

                <div class="product-image-placeholder">
                    No Image
                </div>

            <%
                }
            %>


            <!-- Product Name -->

            <h3>
                <%= p.getName() %>
            </h3>


            <!-- Price -->

            <p class="price">
                ₹ <%= p.getPrice() %>
            </p>


            <!-- Stock -->

            <%
                if (p.getQuantity() > 0) {
            %>

                <p class="stock-available">
                    In Stock:
                    <%= p.getQuantity() %>
                </p>

            <%
                } else {
            %>

                <p class="stock-unavailable">
                    Out of Stock
                </p>

            <%
                }
            %>


            <!-- Product Actions -->

            <div class="product-actions">

                <a
                    class="btn"
                    href="<%= request.getContextPath() %>/product-details?id=<%= p.getId() %>">

                    View Details

                </a>


                <%
                    if (p.getQuantity() > 0) {
                %>

                    <a
                        class="btn"
                        href="<%= request.getContextPath() %>/add-to-cart?productId=<%= p.getId() %>">

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


        </div>


        <%
                }

            } else {
        %>


            <div class="empty-state">

                <h3>
                    No Products Found
                </h3>

                <p>
                    Try changing your search or category filter.
                </p>

            </div>


        <%
            }
        %>


    </div>

</div>


<jsp:include page="components/footer.jsp" />


</body>

</html>