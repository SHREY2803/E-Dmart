package business;

import java.util.List;

import dao.ProductDAO;
import daoimpl.ProductDAOImpl;
import model.Product;

public class ProductManager {
	private ProductDAO productDAO;
	
	public ProductManager() {
		// TODO Auto-generated constructor stub
		this.productDAO = new ProductDAOImpl();
	}
	
	public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
	
	public Product getProductById(int id) {
        return productDAO.getProductById(id);
    }
}
