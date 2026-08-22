package business;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import daoimpl.CartDAOImpl;
import daoimpl.OrderDAOImpl;
import model.Cart;
import model.Order;
import model.OrderItem;
import util.DBConnection;
import dao.OrderDAO;
import dao.CartDAO;

public class OrderManager {
	private OrderDAO orderDAO = new OrderDAOImpl();
	private CartDAO cartDAO = new CartDAOImpl();

	/**
	 * Places an order for a user. Returns true if order is placed successfully.
	 */

	public int createOrder(int userId) {

		Connection con = null;
		try {

			con = DBConnection.getConnection();
			con.setAutoCommit(false);

			// 1️⃣ Fetch cart items
			List<Cart> cartItems = cartDAO.getCartByUser(con, userId);

			if (cartItems == null || cartItems.isEmpty()) {
				return 0;
			}

			// 2️⃣ Calculate total
			double total = 0;
			for (Cart c : cartItems) {
				total += c.getPrice() * c.getQuantity();
			}

			// 3️⃣ Create order
			Order order = new Order();
			order.setUserId(userId);
			order.setTotalAmount(total);
			order.setStatus("PLACED");

			int orderId = orderDAO.createOrder(con, order);

			if (orderId == 0) {
				con.rollback();
				return 0;
			}

			// 4️⃣ Create order items
			List<OrderItem> items = new ArrayList<>();
			for (Cart c : cartItems) {
				OrderItem item = new OrderItem();
				item.setProductId(c.getProductId());
				item.setPrice(c.getPrice());
				item.setQuantity(c.getQuantity());
				items.add(item);
			}

			// 5️⃣ Insert order items
			orderDAO.addOrderItems(con, orderId, items);

			// 6️⃣ Clear cart (same connection)
//			cartDAO.clearCart(con, userId);

			// 7️⃣ Commit
			con.commit();
			return orderId;

		} catch (Exception e) {
			try {
				if (con != null)
					con.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return 0;
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
