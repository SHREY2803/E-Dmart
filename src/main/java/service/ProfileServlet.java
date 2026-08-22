package service;

import java.io.IOException;

import dao.UserDAO;
import daoimpl.UserDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

	private UserDAO userDAO = new UserDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(false);

		if (session == null || session.getAttribute("userId") == null) {
			res.sendRedirect(req.getContextPath() + "/login.jsp");
			return;
		}

		int userId = (int) session.getAttribute("userId");
		User user = userDAO.getUserById(userId);
		req.setAttribute("user", user);

		req.getRequestDispatcher("/customer/profile.jsp").forward(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(false);

		if (session == null || session.getAttribute("userId") == null) {
			res.sendRedirect(req.getContextPath() + "/login.jsp");
			return;
		}

		int userId = (int) session.getAttribute("userId");

		String name = req.getParameter("name");
		String email = req.getParameter("email");

		userDAO.updateProfile(userId, name, email);

		res.sendRedirect(req.getContextPath() + "/profile?success=true");
		
		session.setAttribute("userName", name);

	}

}
