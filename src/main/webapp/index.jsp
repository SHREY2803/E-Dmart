<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

    <title>E-Dmart | Everyday Essentials Online</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/assets/css/style.css">

    <link rel="icon"
          type="image/png"
          href="<%= request.getContextPath() %>/assets/images/dmart_favicon.png">

</head>

<body>

    <jsp:include page="components/navbar.jsp" />


    <!-- =====================================================
         HERO SECTION
    ====================================================== -->

    <div class="hero-section">

        <div class="hero-content">

            <h1>
                Everything You Need, All in One Place
            </h1>

            <p>
                Shop everyday essentials at great prices.
                Simple shopping. Secure payments. Convenient delivery.
            </p>

            <div class="hero-buttons">

                <a href="<%= request.getContextPath() %>/products"
                   class="btn">
                    Browse Store
                </a>

                <%
                    if (session.getAttribute("userId") == null) {
                %>

                    <a href="<%= request.getContextPath() %>/register.jsp"
                       class="btn-outline">
                        Create Account
                    </a>

                <%
                    }
                %>

            </div>

        </div>

    </div>


    <!-- =====================================================
         FEATURED PRODUCTS
    ====================================================== -->

    <div class="container">

        <h2 class="section-title">
            Featured Products
        </h2>


        <div class="game-grid">


            <!-- PRODUCT 1 -->

            <div class="game-card">

                <img src="<%= request.getContextPath() %>/assets/images/1787455819029_milk.jpg"
                     alt="Milk">

                <h3>
                    Milk
                </h3>

                <p class="category">
                    Dairy • Fresh
                </p>

                <p class="price">
                    ₹ 55
                </p>

                <a href="<%= request.getContextPath() %>/products"
                   class="btn">
                    Shop Now
                </a>

            </div>


            <!-- PRODUCT 2 -->

            <div class="game-card">

                <img src="<%= request.getContextPath() %>/assets/images/1787455906755_curd.jpg"
                     alt="Curd">

                <h3>
                    Curd
                </h3>

                <p class="category">
                    Dairy • Fresh
                </p>

                <p class="price">
                    ₹ 30
                </p>

                <a href="<%= request.getContextPath() %>/products"
                   class="btn">
                    Shop Now
                </a>

            </div>


            <!-- PRODUCT 3 -->

            <div class="game-card">

                <img src="<%= request.getContextPath() %>/assets/images/1787456018351_apple.jpg"
                     alt="Apple">

                <h3>
                    Fresh Apples
                </h3>

                <p class="category">
                    Fruits • Fresh
                </p>

                <p class="price">
                    ₹ 60
                </p>

                <a href="<%= request.getContextPath() %>/products"
                   class="btn">
                    Shop Now
                </a>

            </div>


            <!-- PRODUCT 4 -->

            <div class="game-card">

                <img src="<%= request.getContextPath() %>/assets/images/1787456108811_kurkure.jpg"
                     alt="Kurkure">

                <h3>
                    Kurkure
                </h3>

                <p class="category">
                    Snacks • Packaged Food
                </p>

                <p class="price">
                    ₹ 25
                </p>

                <a href="<%= request.getContextPath() %>/products"
                   class="btn">
                    Shop Now
                </a>

            </div>


        </div>

    </div>


    <jsp:include page="components/footer.jsp" />

</body>
</html>