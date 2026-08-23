<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Product" %>

<!DOCTYPE html>
<html>

<head>

    <title>Manage Products</title>

    <link rel="stylesheet"
          href="../assets/css/style.css">

    <link rel="icon"
          type="image/png"
          href="./../assets/images/page_favicon.png">

</head>

<body>

<jsp:include page="../components/navbar.jsp"/>


<div class="container manage-product-container">

    <h2>Manage Products</h2>


    <!--
        IMPORTANT:
        Do NOT directly open add-product.jsp.

        We go through AddAdminProductServlet so that
        the servlet can load categories from the database.
    -->
    <a class="btn"
       href="<%= request.getContextPath() %>/admin/AddAdminProduct">

        Add Product

    </a>


    <div class="admin-table">

        <div class="admin-table-header">

            <div>Image</div>

            <div>Name</div>

            <div>Price</div>

            <div>Actions</div>

        </div>


        <%
            List<Product> products =
                    (List<Product>) request.getAttribute("adminProducts");

            if (products != null) {

                for (Product p : products) {
        %>


        <div class="admin-table-row">

            <!-- Product Image -->
            <div>

                <img
                    src="<%= request.getContextPath() + "/" + p.getImageUrl() %>"
                    class="admin-product-img">

            </div>


            <!-- Product Name -->
            <div>
                <%= p.getName() %>
            </div>


            <!-- Product Price -->
            <div>
                ₹ <%= p.getPrice() %>
            </div>


            <!-- Actions -->
            <div class="action-buttons">

                <!-- Edit -->
                <form
                    action="<%= request.getContextPath() %>/admin/EditAdminProduct"
                    method="get"
                    style="display:inline">

                    <input
                        type="hidden"
                        name="id"
                        value="<%= p.getId() %>">

                    <button class="btn">
                        Edit
                    </button>

                </form>


                <!-- Delete -->
                <form
                    action="<%= request.getContextPath() %>/admin/DeleteAdminProduct"
                    method="post"
                    style="display:inline">

                    <input
                        type="hidden"
                        name="id"
                        value="<%= p.getId() %>">

                    <button class="btn danger">
                        Delete
                    </button>

                </form>

            </div>

        </div>


        <%
                }
            }
        %>


    </div>

</div>

</body>

</html>