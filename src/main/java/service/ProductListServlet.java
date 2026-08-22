package service;

import java.io.IOException;
import java.util.List;

import business.ProductManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

@WebServlet("/products")
public class ProductListServlet extends HttpServlet {

	private ProductManager productManager;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		productManager = new ProductManager();

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<Product> products = productManager.getAllProducts();

		req.setAttribute("products", products);

		req.getRequestDispatcher("products.jsp").forward(req, res);
	}
}
