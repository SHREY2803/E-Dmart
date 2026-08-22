<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
  <title>Payment</title>
  <link rel="stylesheet" href="assets/css/style.css">
  <link rel="icon" type="image/png" href="./assets/images/page_favicon.png">
</head>

<body>

<jsp:include page="components/navbar.jsp" />

<div class="container" style="max-width:420px">

  <h2>Payment Details</h2>

  <form action="process-payment" method="post">

  	<input type="hidden" name="orderId" value="${param.orderId}">

  	<input class="input-box" name="cardNumber" placeholder="Card Number" required>
  	<input class="input-box" name="cardHolder" placeholder="Card Holder Name" required>
  	<input class="input-box" name="expiry" placeholder="MM/YY" required>
  	<input class="input-box" type="password" name="cvv" placeholder="CVV" required>

  	<button type="submit" class="btn">Pay Now</button>

</form>

</div>

<jsp:include page="components/footer.jsp" />

</body>
</html>
