<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <title>E-Dmart | Order Successful</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/style.css">

    <link rel="icon"
          type="image/png"
          href="<%= request.getContextPath() %>/assets/images/dmart_favicon.png">

</head>

<body>

<jsp:include page="components/navbar.jsp" />


<div class="container">


    <div class="order-success-wrapper">

        <div class="order-success-card">


            <div class="success-icon">
                ✓
            </div>


            <h2>
                Order Placed Successfully!
            </h2>


            <p>
                Thank you for shopping with E-Dmart.
            </p>

            <p class="success-subtext">
                Your order has been placed successfully and is being processed.
            </p>


            <div class="success-actions">

                <a
                    href="<%= request.getContextPath() %>/products"
                    class="btn">

                    Continue Shopping

                </a>

                <a
                    href="<%= request.getContextPath() %>/my-orders"
                    class="btn btn-clear">

                    View My Orders

                </a>

            </div>


        </div>

    </div>


</div>


<jsp:include page="components/footer.jsp" />

</body>

</html>