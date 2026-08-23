package service;

import java.io.IOException;
import java.util.List;

import business.ProductManager;
import dao.CategoryDAO;
import daoimpl.CategoryDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Product;
import model.Category;

@WebServlet("/products")
public class ProductListServlet extends HttpServlet {

	private ProductManager productManager;
	private CategoryDAO categoryDAO;

	@Override
	public void init() {

		productManager = new ProductManager();
		categoryDAO = new CategoryDAOImpl();
	}

	@Override
	protected void doGet(
			HttpServletRequest req,
			HttpServletResponse res)
			throws ServletException, IOException {

		// Get all products
		List<Product> products =
				productManager.getAllProducts();

		// Get all categories dynamically from database
		List<Category> categories =
				categoryDAO.getAllCategories();

		// Send data to JSP
		req.setAttribute("products", products);
		req.setAttribute("categories", categories);

		// Open products page
		req.getRequestDispatcher(
				"products.jsp"
		).forward(req, res);
	}
}