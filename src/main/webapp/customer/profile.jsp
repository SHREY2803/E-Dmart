<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>

<%
    User user = (User) request.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/dashboard");
        return;
    }

    boolean editMode = "true".equals(request.getParameter("edit"));
%>

<!DOCTYPE html>
<html>
<head>
    <title>My Profile</title>
    <link rel="stylesheet" href="../assets/css/style.css">
</head>

<body>

<jsp:include page="../components/navbar.jsp" />

<div class="container" style="max-width: 500px">

    <h2>My Profile</h2>

    <% if ("true".equals(request.getParameter("success"))) { %>
        <p style="color: lightgreen;">Profile updated successfully</p>
    <% } %>

    <!-- ================= VIEW MODE ================= -->
    <% if (!editMode) { %>

        <p><strong>Name:</strong> <%= user.getName() %></p>
        <p><strong>Email:</strong> <%= user.getEmail() %></p>

        <a class="btn"
           href="<%= request.getContextPath() %>/profile?edit=true">
           Update Profile
        </a>

    <% } %>

    <!-- ================= EDIT MODE ================= -->
    <% if (editMode) { %>

        <form method="post" action="<%= request.getContextPath() %>/profile">

            <label>Name</label>
            <input class="input-box" type="text" name="name"
                   value="<%= user.getName() %>" required>

            <label>Email</label>
            <input class="input-box" type="email" name="email"
                   value="<%= user.getEmail() %>" readonly>

            <button class="btn" type="submit">Save Changes</button>

            <a class="btn" style="background:#444"
               href="<%= request.getContextPath() %>/profile">
               Cancel
            </a>

        </form>

    <% } %>

</div>

<jsp:include page="../components/footer.jsp" />

</body>
</html>
