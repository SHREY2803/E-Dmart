package service;

import java.io.IOException;
import java.util.List;

import business.CartManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

	private CartManager cartManager = new CartManager();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		// 1️⃣ Check login
		if (session == null || session.getAttribute("userId") == null) {
			res.sendRedirect("login.jsp");
			return;
		}

		int userId = (int) session.getAttribute("userId");

		// 2️⃣ Load cart items
		List<Cart> cartItems = cartManager.getUserCart(userId);

		if (cartItems == null || cartItems.isEmpty()) {
			res.sendRedirect("cart");
			return;
		}

		// 3️⃣ Send data to JSP
		req.setAttribute("cartItems", cartItems);

		// 4️⃣ Forward to checkout page
		req.getRequestDispatcher("checkout.jsp").forward(req, res);
	}
}
