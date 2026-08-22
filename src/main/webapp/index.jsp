<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>GameStore | Buy Games Online</title>
<link rel="stylesheet" href="assets/css/style.css">
<link rel="icon" type="image/png"
	href="./assets/images/page_favicon.png">
</head>

<body>

	<jsp:include page="components/navbar.jsp" />

	<!-- HERO SECTION -->
	<div class="hero-section">
		<div class="hero-content">
			<h1>Level Up Your Game Library</h1>
			<p>Buy top PC games instantly. Secure payments. Lifetime access.</p>

			<div class="hero-buttons">
				<a href="<%= request.getContextPath() %>/products" class="btn">
					Browse Store
				</a>

				<%
					if (session.getAttribute("userId") == null) {
				%>
				<a href="<%= request.getContextPath() %>/register.jsp" class="btn-outline">
					Create Account
				</a>
				<% } %>
			</div>
		</div>
	</div>

	<!-- FEATURED GAMES -->
	<div class="container">
		<h2 class="section-title">Featured Games</h2>

		<div class="game-grid">

			<div class="game-card">
				<img src="assets/images/cyber_quest.jpg">
				<h3>Cyber Quest</h3>
				<p class="category">Action • Adventure</p>
				<p class="price">₹ 999</p>
				<a href="<%= request.getContextPath() %>/products" class="btn">
					Know More
				</a>
			</div>

			<div class="game-card">
				<img src="assets/images/shadow_arena.jpg">
				<h3>Shadow Arena</h3>
				<p class="category">Multiplayer • Battle</p>
				<p class="price">₹ 799</p>
				<a href="<%= request.getContextPath() %>/products" class="btn">
					Know More
				</a>
			</div>

			<div class="game-card">
				<img src="assets/images/galaxy_raiders.jpg">
				<h3>Galaxy Raiders</h3>
				<p class="category">Sci-Fi • Shooter</p>
				<p class="price">₹ 699</p>
				<a href="<%= request.getContextPath() %>/products" class="btn">
					Know More
				</a>
			</div>
			
			<div class="game-card">
				<img src="assets/images/mythic_realms.jpg">
				<h3>Galaxy Raiders</h3>
				<p class="category">RPG</p>
				<p class="price">₹ 1199</p>
				<a href="<%= request.getContextPath() %>/products" class="btn">
					Know More
				</a>
			</div>

		</div>
	</div>

	<jsp:include page="components/footer.jsp" />

</body>
</html>
