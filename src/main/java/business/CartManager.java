package business;

import daoimpl.CartDAOImpl;

import java.util.List;

import dao.CartDAO;
import model.Cart;

public class CartManager {
	private CartDAO cartDAO = new CartDAOImpl();
	
	public boolean addToCart(int userId, int productId) {
        return cartDAO.addToCart(userId, productId);
    }
	
	public List<Cart> getUserCart(int userId) {
        return cartDAO.getCartByUser(userId);
    }
	
	public boolean removeItem(int cartId) {
        return cartDAO.removeFromCart(cartId);
    }
}
