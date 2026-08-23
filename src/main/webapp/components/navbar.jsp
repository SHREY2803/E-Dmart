<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<link rel="stylesheet"
      href="<%= request.getContextPath() %>/assets/css/style.css">

<%
    String role = (String) session.getAttribute("role");
    Integer userId = (Integer) session.getAttribute("userId");
%>

<div class="navbar">

    <!-- =====================================================
         LEFT SIDE
    ====================================================== -->

    <div class="nav-left">

        <!-- BRAND -->
        <a class="brand"
           href="<%= request.getContextPath() %>/index.jsp">
            E-Dmart
        </a>


        <!-- =================================================
             CUSTOMER NAVIGATION
        ================================================== -->

        <% if ("CUSTOMER".equals(role)) { %>

            <a href="<%= request.getContextPath() %>/products">
                Store
            </a>

            <a href="<%= request.getContextPath() %>/cart">
                Cart
            </a>

            <a href="<%= request.getContextPath() %>/customer/dashboard.jsp">
                Dashboard
            </a>

        <% } %>


        <!-- =================================================
             ADMIN NAVIGATION
        ================================================== -->

        <% if ("ADMIN".equals(role)) { %>

            <a href="<%= request.getContextPath() %>/admin/admin-dashboard.jsp">
                Admin Dashboard
            </a>

        <% } %>


        <!-- =================================================
             STAFF NAVIGATION
        ================================================== -->

        <% if ("STAFF".equals(role)) { %>

            <a href="<%= request.getContextPath() %>/customer/dashboard.jsp">
                Dashboard
            </a>

        <% } %>


        <!-- =================================================
             GUEST NAVIGATION
        ================================================== -->

        <% if (userId == null) { %>

            <a href="<%= request.getContextPath() %>/products">
                Store
            </a>

        <% } %>

    </div>


    <!-- =====================================================
         RIGHT SIDE
    ====================================================== -->

    <div class="nav-right">

        <% if (userId == null) { %>

            <!-- NOT LOGGED IN -->

            <a href="<%= request.getContextPath() %>/login.jsp">
                Login
            </a>

            <a href="<%= request.getContextPath() %>/register.jsp">
                Register
            </a>

        <% } else { %>

            <!-- LOGGED IN -->

            <span class="user-name">
                👤 <%= session.getAttribute("userName") %>
            </span>

            <a href="<%= request.getContextPath() %>/logout">
                Logout
            </a>

        <% } %>

    </div>

</div>