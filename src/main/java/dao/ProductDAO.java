package dao;

import java.util.List;

import model.Product;

public interface ProductDAO {

    // Add product
    boolean addProduct(Product product);

    // Get all products
    List<Product> getAllProducts();

    // Get product by ID
    Product getProductById(int id);

    // Update product
    boolean updateProduct(Product product);

    // Update stock quantity
    boolean updateStock(int productId, int quantity);

    // Search products
    List<Product> searchProducts(String keyword);

    // Filter products by category
    List<Product> getProductsByCategory(int categoryId);

    // Delete product
    boolean deleteProduct(int productId);
}