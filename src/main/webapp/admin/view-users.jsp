<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.User"%>

<!DOCTYPE html>
<html>
<head>
  <title>View Users</title>
  <link rel="stylesheet" href="../assets/css/style.css">
</head>

<body>
<jsp:include page="../components/navbar.jsp"/>

<div class="container user-container">

  <h2 class="page-title">Registered Users</h2>
  
  <div class="admin-card">

        <table class="admin-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    
                </tr>
            </thead>

            <tbody>
            <%
                List<User> users = (List<User>) request.getAttribute("users");
                for (User u : users) {
            %>
                <tr>
                    <td><%= u.getId() %></td>
                    <td><%= u.getName() %></td>
                    <td><%= u.getEmail() %></td>
                    <td>
                        <span class="role-badge <%= u.getRole().toLowerCase() %>">
                            <%= u.getRole() %>
                        </span>
                    </td>
                    
                </tr>
            <% } %>
            </tbody>
        </table>

    </div>

</div>

</body>
</html>
