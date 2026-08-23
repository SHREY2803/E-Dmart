<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Category" %>
<%@ page import="model.Product" %>

<%
    Product product =
            (Product) request.getAttribute("product");

    List<Category> categories =
            (List<Category>) request.getAttribute("categories");
%>

<!DOCTYPE html>
<html>

<head>

    <title>Edit Product</title>

    <link rel="stylesheet"
          href="../assets/css/style.css">

</head>

<body>

<jsp:include page="../components/navbar.jsp" />


<div class="container" style="max-width:480px">

    <h2>Edit Product</h2>


    <form
            action="<%= request.getContextPath() %>/admin/UpdateAdminProduct"
            method="post"
            enctype="multipart/form-data">


        <!-- Product ID -->

        <input
                type="hidden"
                name="id"
                value="<%= product.getId() %>">


        <!-- Product Name -->

        <input
                class="input-box"
                type="text"
                name="name"
                value="<%= product.getName() %>"
                placeholder="Product Name"
                required>


        <!-- Price -->

        <input
                class="input-box"
                type="number"
                name="price"
                value="<%= product.getPrice() %>"
                step="0.01"
                min="0"
                placeholder="Price"
                required>


        <!-- Quantity -->

        <input
                class="input-box"
                type="number"
                name="quantity"
                value="<%= product.getQuantity() %>"
                min="0"
                placeholder="Stock Quantity"
                required>


        <!-- Category -->

        <select
                class="input-box"
                name="category"
                required>

            <option value="">
                Select Category
            </option>


            <%
                if (categories != null) {

                    for (Category category : categories) {

                        boolean selected =
                                category.getId()
                                        == product.getCategoryId();
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


        <!-- Description -->

        <textarea
                class="input-box"
                name="description"
                placeholder="Product Description"
                required><%= product.getDescription() %></textarea>


        <!-- Current Image -->

        <%
            if (product.getImageUrl() != null
                    && !product.getImageUrl().trim().isEmpty()) {
        %>

            <p>Current Image:</p>

            <img
                    src="<%= request.getContextPath() %>/<%= product.getImageUrl() %>"
                    style="width:120px;border-radius:8px">

        <%
            }
        %>


        <!-- New Image -->

        <input
                class="input-box"
                type="file"
                name="image"
                accept="image/*">


        <!-- Submit -->

        <button
                class="btn"
                type="submit">

            Update Product

        </button>


    </form>

</div>

</body>

</html>