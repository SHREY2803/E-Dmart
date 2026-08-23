package service;

import java.io.IOException;

import business.CartManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet {

    private CartManager cartManager =
            new CartManager();


    @Override
    protected void doPost(
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
        // 2. Get cart ID
        // ==========================================

        String cartParameter =
                req.getParameter("cartId");

        if (cartParameter == null ||
                cartParameter.trim().isEmpty()) {

            res.sendRedirect(
                    req.getContextPath()
                            + "/cart?error=invalid"
            );

            return;
        }


        try {

            int cartId =
                    Integer.parseInt(
                            cartParameter
                    );


            // ==========================================
            // 3. Remove/decrease item
            // ==========================================

            boolean removed =
                    cartManager.removeItem(
                            cartId
                    );


            // ==========================================
            // 4. Redirect
            // ==========================================

            if (removed) {

                res.sendRedirect(
                        req.getContextPath()
                                + "/cart"
                );

            } else {

                res.sendRedirect(
                        req.getContextPath()
                                + "/cart?error=failed"
                );
            }

        } catch (NumberFormatException e) {

            res.sendRedirect(
                    req.getContextPath()
                            + "/cart?error=invalid"
            );
        }
    }
}