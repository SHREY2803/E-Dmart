package business;

import java.util.List;

import dao.ProductDAO;
import daoimpl.ProductDAOImpl;
import model.Product;

public class ProductManager {

	private ProductDAO productDAO;

	public ProductManager() {

		this.productDAO =
				new ProductDAOImpl();
	}


	// ==========================================
	// Get all products
	// ==========================================

	public List<Product> getAllProducts() {

		return productDAO.getAllProducts();
	}


	// ==========================================
	// Get product by ID
	// ==========================================

	public Product getProductById(int id) {

		return productDAO.getProductById(id);
	}


	// ==========================================
	// Customer Product Search / Filter
	// ==========================================

	public List<Product> searchProducts(
			String keyword,
			Integer categoryId) {

		// Search + Category
		if (keyword != null
				&& !keyword.trim().isEmpty()
				&& categoryId != null) {

			return productDAO.searchProducts(
					keyword.trim(),
					categoryId
			);
		}


		// Category only
		if (categoryId != null) {

			return productDAO.getProductsByCategory(
					categoryId
			);
		}


		// Search only
		if (keyword != null
				&& !keyword.trim().isEmpty()) {

			return productDAO.searchProducts(
					keyword.trim()
			);
		}


		// No search and no category
		// Use searchProducts("") so that
		// customer only sees active products.

		return productDAO.searchProducts("");
	}
}