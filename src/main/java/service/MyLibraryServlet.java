package service;

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
import model.OrderItem;

@WebServlet("/my-library")
public class MyLibraryServlet extends HttpServlet {

	private OrderDAO orderDAO = new OrderDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		if (session == null || session.getAttribute("userId") == null) {
			res.sendRedirect(req.getContextPath() + "/login.jsp");
			return;
		}

		int userId = (int) session.getAttribute("userId");

		List<OrderItem> games = orderDAO.getPurchasedGames(userId);

		req.setAttribute("games", games);
		req.getRequestDispatcher("/customer/my-library.jsp").forward(req, res);
	}
}
