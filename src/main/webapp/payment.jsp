<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>

    <title>Payment</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/style.css">

    <link rel="icon"
          type="image/png"
          href="<%= request.getContextPath() %>/assets/images/page_favicon.png">

</head>

<body>

<jsp:include page="components/navbar.jsp" />


<div class="container" style="max-width:420px">

    <h2>Payment Details</h2>


    <form
            action="<%= request.getContextPath() %>/process-payment"
            method="post">


        <!-- Order ID -->

        <input
                type="hidden"
                name="orderId"
                value="<%= request.getParameter("orderId") %>">


        <!-- Payment Method -->

        <select
                class="input-box"
                name="paymentMethod"
                required>

            <option value="">
                Select Payment Method
            </option>

            <option value="UPI">
                UPI
            </option>

            <option value="CARD">
                Credit / Debit Card
            </option>

            <option value="NET_BANKING">
                Net Banking
            </option>

        </select>


        <!-- Pay -->

        <button
                type="submit"
                class="btn">

            Pay Now

        </button>


    </form>

</div>


<jsp:include page="components/footer.jsp" />

</body>

</html>