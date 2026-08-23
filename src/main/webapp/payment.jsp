<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>

    <title>E-Dmart | Payment</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/style.css">

    <link rel="icon"
          type="image/png"
          href="<%= request.getContextPath() %>/assets/images/dmart_favicon.png">

</head>

<body>

<jsp:include page="components/navbar.jsp" />


<div class="container">


    <div class="payment-wrapper">

        <div class="payment-card">


            <h2>
                Payment
            </h2>

            <p class="payment-subtitle">
                Select your preferred payment method
            </p>


            <form
                action="<%= request.getContextPath() %>/process-payment"
                method="post">


                <!-- Order ID -->

                <input
                    type="hidden"
                    name="orderId"
                    value="<%= request.getParameter("orderId") %>">


                <!-- Payment Method -->

                <label for="paymentMethod">
                    Payment Method
                </label>

                <select
                    id="paymentMethod"
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
                    class="btn payment-btn">

                    Pay Now

                </button>


            </form>

        </div>

    </div>

</div>


<jsp:include page="components/footer.jsp" />

</body>

</html>