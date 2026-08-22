package service;

import java.io.IOException;

import business.ProductManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

@WebServlet("/product-details")
public class ProductDetailsServlet extends HttpServlet {
	private ProductManager productManager;

    @Override
    public void init() {
        productManager = new ProductManager();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	String idParam = req.getParameter("id");
    	
    	if (idParam == null || idParam.trim().isEmpty()) {
            res.sendRedirect("products");
            return;
        }
    	
    	int id = Integer.parseInt(idParam);
    	
    	 Product product = productManager.getProductById(id);
    	 
    	 if (product == null) {
             res.sendRedirect("noproduct.jsp");
             return;
         }
    	 
    	 req.setAttribute("product", product);
         req.getRequestDispatcher("product-details.jsp").forward(req, res);

    }
}
