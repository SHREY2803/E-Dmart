<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Create Account</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="icon" type="image/png" href="./assets/images/page_favicon.png">
</head>

<body>

<jsp:include page="components/navbar.jsp" />

<div class="auth-wrapper">
    <div class="auth-card">

        <h2>Create Account</h2>

        <form method="post" action="<%= request.getContextPath() %>/register">

            <input class="input-box"
                   type="text"
                   name="name"
                   placeholder="Full Name"
                   required>

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

            <button class="btn" type="submit">Register</button>
        </form>

        <div class="auth-footer">
            Already have an account?
            <a href="<%= request.getContextPath() %>/login.jsp">Login</a>
        </div>

    </div>
</div>

<jsp:include page="components/footer.jsp" />

</body>
</html>
