package service.admin;

import java.io.IOException;
import java.util.List;

import dao.OrderDAO;
import daoimpl.OrderDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order;


@WebServlet("/admin/ViewAdminOrders")
public class ViewAdminOrdersServlet extends HttpServlet {

	private OrderDAO orderDAO = new OrderDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(false);
		if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
			res.sendRedirect(req.getContextPath() + "/access-denied.jsp");
			return;
		}
		
		List<Order> orders = orderDAO.getAllAdminOrders();
        req.setAttribute("orders", orders);
        
        req.getRequestDispatcher("/admin/view-orders.jsp").forward(req, res);
	}

}
