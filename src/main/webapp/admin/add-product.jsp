<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Category" %>

<!DOCTYPE html>
<html>

<head>

    <title>Add Product</title>

    <link rel="stylesheet"
          href="../assets/css/style.css">

</head>

<body>

<jsp:include page="../components/navbar.jsp" />


<div class="container" style="max-width:480px">

    <h2>Add Product</h2>


    <form
            action="<%= request.getContextPath() %>/admin/AddAdminProduct"
            method="post"
            enctype="multipart/form-data">


        <!-- Product Name -->

        <input
                class="input-box"
                type="text"
                name="name"
                placeholder="Product Name"
                required>


        <!-- Price -->

        <input
                class="input-box"
                type="number"
                name="price"
                step="0.01"
                min="0"
                placeholder="Price"
                required>


        <!-- Quantity -->

        <input
                class="input-box"
                type="number"
                name="quantity"
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
                List<Category> categories =
                        (List<Category>)
                        request.getAttribute("categories");

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


        <!-- Description -->

        <textarea
                class="input-box"
                name="description"
                placeholder="Brief description of product"
                required></textarea>


        <!-- Product Image -->

        <input
                class="input-box"
                type="file"
                name="image"
                accept="image/*">


        <!-- Submit -->

        <button
                class="btn"
                type="submit">

            Add Product

        </button>


    </form>

</div>

</body>

</html>