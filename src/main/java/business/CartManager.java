package business;

import java.util.List;

import dao.CartDAO;
import daoimpl.CartDAOImpl;
import model.Cart;

public class CartManager {

    private CartDAO cartDAO =
            new CartDAOImpl();


    // ==========================================
    // Add product to cart
    // ==========================================

    public boolean addToCart(
            int userId,
            int productId) {

        return cartDAO.addToCart(
                userId,
                productId
        );
    }


    // ==========================================
    // Get user's cart
    // ==========================================

    public List<Cart> getUserCart(
            int userId) {

        return cartDAO.getCartByUser(
                userId
        );
    }


    // ==========================================
    // Remove/decrease item
    // ==========================================

    public boolean removeItem(
            int cartId) {

        return cartDAO.removeFromCart(
                cartId
        );
    }
}