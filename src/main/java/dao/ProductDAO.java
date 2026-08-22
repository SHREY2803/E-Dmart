package dao;

import java.util.List;

import model.Product;

public interface ProductDAO {
	// Admin operations
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(int productId);
    
 // Customer operations
    List<Product> getAllProducts();
    Product getProductById(int productId);
}
