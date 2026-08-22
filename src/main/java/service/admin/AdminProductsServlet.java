package service.admin;

import java.io.IOException;

import dao.ProductDAO;
import daoimpl.ProductDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/admin/AdminProduct")
public class AdminProductsServlet extends HttpServlet {

	private ProductDAO productDAO = new ProductDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(false);

		if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
			res.sendRedirect(req.getContextPath() + "/access-denied.jsp");
			return;
		}
		
		req.setAttribute("adminProducts", productDAO.getAllProducts());
		req.getRequestDispatcher("/admin/manage-products.jsp").forward(req, res);
	}

}
