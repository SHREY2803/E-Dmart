<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" href="assets/css/style.css">

<%
	String role = (String) session.getAttribute("role");
	Integer userId = (Integer) session.getAttribute("userId");
%>
<div class="navbar">

    <!-- LEFT SIDE -->
    <div class="nav-left">
    	
    	
        <%-- Show Store & Cart ONLY for CUSTOMER --%>
        <% if ("CUSTOMER".equals(role)) { %>
        	<a class="brand"
		   		href="<%= request.getContextPath() %>/index.jsp">
				GameStore
			</a>
            <a href="<%= request.getContextPath() %>/products">Store</a>
            <a href="<%= request.getContextPath() %>/cart">Cart</a>
        <% } %>

        <%-- Dashboard links --%>
        <% if (userId != null) { %>

            <% if ("ADMIN".equals(role)) { %>
                <a href="<%= request.getContextPath() %>/admin/admin-dashboard.jsp">
                    Admin Dashboard
                </a>
            <% } else { %>
                <a href="<%= request.getContextPath() %>/customer/dashboard.jsp">
                    Dashboard
                </a>
            <% } %>

        <% } else{%>
        	<!-- BRAND / HOME LINK -->
			<a class="brand"
		   		href="<%= request.getContextPath() %>/index.jsp">
				GameStore
			</a>
        	<a href="<%= request.getContextPath() %>/products">Store</a>
            <a href="<%= request.getContextPath() %>/cart">Cart</a>
        		<%} %>
        

    </div>

    <!-- RIGHT SIDE -->
    <div class="nav-right">

        <% if (userId == null) { %>
            <!-- NOT LOGGED IN -->
            
            <a href="<%= request.getContextPath() %>/login.jsp">Login</a>
            <a href="<%= request.getContextPath() %>/register.jsp">Register</a>
            
        <% } else { %>
            <!-- LOGGED IN -->
            <span style="margin-right: 12px;">
                👤 <%= session.getAttribute("userName") %>
            </span>
            <a href="<%= request.getContextPath() %>/logout">Logout</a>
        <% } %>

    </div>

</div>