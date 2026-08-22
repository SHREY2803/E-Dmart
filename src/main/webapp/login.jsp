<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="icon" type="image/png" href="./assets/images/page_favicon.png">
</head>

<body>

<jsp:include page="components/navbar.jsp" />

<div class="auth-wrapper">
    <div class="auth-card">

        <h2>Login</h2>

        <form method="post" action="<%= request.getContextPath() %>/login">

            <input class="input-box"
                   type="email"
                   name="email"
                   placeholder="Email"
                   required>

            <input class="input-box"
                   type="password"
                   name="password"
                   placeholder="Password"
                   required>

            <button class="btn" type="submit">Login</button>
        </form>

        <div class="auth-footer">
            Don’t have an account?
            <a href="<%= request.getContextPath() %>/register.jsp">Register</a>
        </div>

    </div>
</div>

<jsp:include page="components/footer.jsp" />

</body>
</html>
