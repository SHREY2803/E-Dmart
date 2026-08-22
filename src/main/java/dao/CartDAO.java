package dao;

import java.sql.Connection;
import java.util.List;

import model.Cart;

public interface CartDAO {
	boolean addToCart(int userId, int productId);

	List<Cart> getCartByUser(int userId);

	List<Cart> getCartByUser(Connection con, int userId);

	boolean removeFromCart(int cartId);

	void clearCart(int userId);

	void clearCart(Connection con, int userId);
}
