package service;

import java.io.IOException;

import dao.CartDAO;
import daoimpl.CartDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1️⃣ Get cartId from URL
        int cartId = Integer.parseInt(request.getParameter("cartId"));

        // 2️⃣ Call DAO (already exists)
        CartDAO cartDAO = new CartDAOImpl();
        cartDAO.removeFromCart(cartId);

        // 3️⃣ Redirect back to cart
        response.sendRedirect("cart");
    }

}
