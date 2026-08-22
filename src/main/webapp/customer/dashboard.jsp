<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>User Dashboard</title>
<link rel="icon" type="image/png"
	href="./../assets/images/page_favicon.png">
<link rel="stylesheet" href="../assets/css/style.css">
</head>

<body>

	<jsp:include page="../components/navbar.jsp" />

	<div class="container">

		<h2>User Dashboard</h2>
		<p>Welcome to your account.</p>

		<a class="btn" href="<%= request.getContextPath() %>/my-orders">My Orders</a> 
		<a class="btn" href="<%= request.getContextPath() %>/profile">Profile</a> 
		<a class="btn" href="<%= request.getContextPath() %>/my-library">My Game Library</a>

	</div>

	<jsp:include page="../components/footer.jsp" />

</body>
</html>
