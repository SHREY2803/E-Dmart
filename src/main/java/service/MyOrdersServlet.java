package service;

import daoimpl.OrderDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order;
import model.OrderItem;

import java.io.IOException;
import java.util.List;

import dao.OrderDAO;

@WebServlet("/my-orders")
public class MyOrdersServlet extends HttpServlet {

	private OrderDAO orderDAO = new OrderDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(false);

		// Check login
		if (session == null || session.getAttribute("userId") == null) {
			res.sendRedirect("login.jsp");
			return;
		}

		int userId = (int) session.getAttribute("userId");

		// Fetch orders
		List<Order> orders = orderDAO.getOrdersByUser(userId);

		// For each order → fetch items
		for (Order order : orders) {
			List<OrderItem> items = orderDAO.getOrderItems(order.getId());
			order.setItems(items); // attach items to order
		}
		
//		for(Order order: orders) {
//			System.out.println(order.getItems().get(0).getProductName());
//		}


		// Send to JSP
		req.setAttribute("orders", orders);
		req.getRequestDispatcher("/customer/my-orders.jsp").forward(req, res);
	}
}
