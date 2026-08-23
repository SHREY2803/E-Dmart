<%@ page import="java.util.List" %>
<%@ page import="model.Category" %>

<%
    List<Category> categories =
            (List<Category>) request.getAttribute("categories");

    String success = request.getParameter("success");
    String error = request.getParameter("error");
%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>Category Management - E-Dmart</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f5f5f5;
        }

        .container {
            max-width: 900px;
            margin: auto;
        }

        .card {
            background: white;
            padding: 25px;
            margin-bottom: 25px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        h1, h2 {
            margin-top: 0;
        }

        input, textarea {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 15px;
            box-sizing: border-box;
        }

        textarea {
            height: 80px;
            resize: vertical;
        }

        button {
            padding: 10px 20px;
            cursor: pointer;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        th {
            background-color: #f0f0f0;
        }

        .success {
            color: green;
            margin-bottom: 15px;
        }

        .error {
            color: red;
            margin-bottom: 15px;
        }

    </style>

</head>

<body>

<div class="container">

    <h1>Category Management</h1>

    <%
        if ("added".equals(success)) {
    %>
        <div class="success">
            Category added successfully!
        </div>
    <%
        }

        if ("invalid".equals(error)) {
    %>
        <div class="error">
            Category name is required.
        </div>
    <%
        }

        if ("failed".equals(error)) {
    %>
        <div class="error">
            Failed to add category.
        </div>
    <%
        }
    %>


    <!-- Add Category -->

    <div class="card">

        <h2>Add Category</h2>

        <form method="post"
              action="<%= request.getContextPath() %>/admin/categories">

            <label>Category Name</label>

            <input type="text"
                   name="name"
                   required
                   maxlength="100">


            <label>Description</label>

            <textarea name="description"
                      maxlength="500"></textarea>


            <button type="submit">
                Add Category
            </button>

        </form>

    </div>


    <!-- Category List -->

    <div class="card">

        <h2>Categories</h2>

        <%
            if (categories == null || categories.isEmpty()) {
        %>

            <p>No categories available.</p>

        <%
            } else {
        %>

            <table>

                <thead>

                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                </tr>

                </thead>

                <tbody>

                <%
                    for (Category category : categories) {
                %>

                    <tr>

                        <td>
                            <%= category.getId() %>
                        </td>

                        <td>
                            <%= category.getName() %>
                        </td>

                        <td>
                            <%= category.getDescription() %>
                        </td>

                    </tr>

                <%
                    }
                %>

                </tbody>

            </table>

        <%
            }
        %>

    </div>

</div>

</body>
</html>