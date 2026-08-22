<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <title>Admin Dashboard</title>
  <link rel="stylesheet" href="../assets/css/style.css">
  <link rel="icon" type="image/png"
	href="./../assets/images/page_favicon.png">
</head>

<body>
<jsp:include page="./../components/navbar.jsp" />

<div class="container">

  <h2>Admin Dashboard</h2>

  
  <a class="btn" href="<%= request.getContextPath() %>/admin/AdminProduct">Manage Products</a>
  <a class="btn" href="<%= request.getContextPath() %>/admin/ViewUsers">View Users</a>
  <a class="btn" href="<%= request.getContextPath() %>/admin/ViewAdminOrders">View Orders</a>

</div>

</body>
</html>
