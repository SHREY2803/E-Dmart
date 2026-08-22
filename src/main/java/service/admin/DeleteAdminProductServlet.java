package service.admin;

import java.io.File;
import java.io.IOException;

import dao.ProductDAO;
import daoimpl.ProductDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Product;

@WebServlet("/admin/DeleteAdminProduct")
public class DeleteAdminProductServlet extends HttpServlet {
	private ProductDAO productDAO = new ProductDAOImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = req.getSession(false);
		if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
			res.sendRedirect(req.getContextPath() + "/access-denied.jsp");
			return;
		}

		int productId = Integer.parseInt(req.getParameter("id"));

		Product p = productDAO.getProductById(productId);
		
		if (p != null) {
            File img = new File(
                getServletContext().getRealPath("/") + p.getImageUrl()
            );
            if (img.exists()) img.delete();
        }
		
		productDAO.deleteProduct(productId);
		
		res.sendRedirect(req.getContextPath() + "/admin/AdminProduct");
	}
}
