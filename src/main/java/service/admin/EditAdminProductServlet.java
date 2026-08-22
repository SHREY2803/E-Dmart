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
import model.Product;

@WebServlet("/admin/EditAdminProduct")
public class EditAdminProductServlet extends HttpServlet{
	private ProductDAO productDAO = new ProductDAOImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            res.sendRedirect(req.getContextPath() + "/access-denied.jsp");
            return;
        }
        
        int productId = Integer.parseInt(req.getParameter("id"));

        Product p = productDAO.getProductById(productId);
        
        req.setAttribute("id", p.getId());
        req.setAttribute("name", p.getName());
        req.setAttribute("price", p.getPrice());
        req.setAttribute("category", p.getCategory());
        req.setAttribute("description", p.getDescription());
        req.setAttribute("imageUrl", p.getImageUrl());
        
        req.getRequestDispatcher("/admin/edit-product.jsp").forward(req, res);
	}

}
