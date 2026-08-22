package service.admin;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import dao.UserDAO;
import daoimpl.UserDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet("/admin/ViewUsers")
public class AllUsersAdminServlet extends HttpServlet {
	private UserDAO userDAO = new UserDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(false);
		if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
			res.sendRedirect(req.getContextPath() + "/access-denied.jsp");
			return;
		}

		List<User> users = userDAO.getAllUsers();
		Iterator<User> it = users.iterator();

		while (it.hasNext()) {
			User u = it.next();
			if (u.getRole().equals("ADMIN")) {
				it.remove();
			}
		}
		
		Collections.sort(users);

//		for(User user : users) {
//			System.out.println(user.getName());
//		}

		req.setAttribute("users", users);
		req.getRequestDispatcher("/admin/view-users.jsp").forward(req, res);

	}

}
