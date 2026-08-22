package service;

import java.io.IOException;

import business.OrderManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

	private OrderManager orderManager = new OrderManager();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(false);

		// 1️⃣ Check login
		if (session == null || session.getAttribute("userId") == null) {
			res.sendRedirect("login.jsp");
			return;
		}

		int userId = (int) session.getAttribute("userId");

		// ✅ Create order and get orderId
        int orderId = orderManager.createOrder(userId);

        if (orderId > 0) {
            res.sendRedirect("payment.jsp?orderId=" + orderId);
        } else {
            res.sendRedirect("checkout.jsp?error=true");
        }
		
	}

}
