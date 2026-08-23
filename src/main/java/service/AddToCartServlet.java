package service;

import java.io.IOException;

import business.CartManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    private CartManager cartManager =
            new CartManager();


    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse res)
            throws ServletException, IOException {

        // ==========================================
        // 1. Check login
        // ==========================================

        HttpSession session =
                req.getSession(false);

        if (session == null ||
                session.getAttribute("userId") == null) {

            res.sendRedirect(
                    req.getContextPath()
                            + "/login.jsp"
            );

            return;
        }


        // ==========================================
        // 2. Get user ID
        // ==========================================

        int userId =
                (int) session.getAttribute("userId");


        // ==========================================
        // 3. Get product ID
        // ==========================================

        String productParameter =
                req.getParameter("productId");

        if (productParameter == null ||
                productParameter.trim().isEmpty()) {

            res.sendRedirect(
                    req.getContextPath()
                            + "/products?error=invalid"
            );

            return;
        }


        try {

            int productId =
                    Integer.parseInt(
                            productParameter
                    );


            // ==========================================
            // 4. Add to cart
            // ==========================================

            boolean added =
                    cartManager.addToCart(
                            userId,
                            productId
                    );


            // ==========================================
            // 5. Redirect
            // ==========================================

            if (added) {

                res.sendRedirect(
                        req.getContextPath()
                                + "/cart"
                );

            } else {

                // Could be:
                // - product doesn't exist
                // - product inactive
                // - out of stock
                // - cart already reached stock limit

                res.sendRedirect(
                        req.getContextPath()
                                + "/products?error=stock"
                );
            }

        } catch (NumberFormatException e) {

            res.sendRedirect(
                    req.getContextPath()
                            + "/products?error=invalid"
            );
        }
    }
}