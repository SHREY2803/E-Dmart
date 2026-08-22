<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Payment Failed</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="icon" type="image/png" href="./assets/images/page_favicon.png">
</head>

<body>

<jsp:include page="components/navbar.jsp" />

<div class="container" style="max-width:500px; text-align:center; margin-top:60px">

    <h2 style="color:#ff5c5c">❌ Payment Failed</h2>

    <%
        String reason = request.getParameter("reason");
        String message = "Something went wrong.";

        if ("invalid_card".equals(reason)) {
            message = "Invalid card details. Please check and try again.";
        } else if ("insufficient_balance".equals(reason)) {
            message = "Insufficient balance in your account.";
        }
    %>

    <p style="margin:20px 0; font-size:16px;">
        <%= message %>
    </p>

    <a href="payment.jsp" class="btn">Try Again</a>
    <a href="cart" class="btn" style="margin-left:10px">Back to Cart</a>

</div>

<jsp:include page="components/footer.jsp" />

</body>
</html>
