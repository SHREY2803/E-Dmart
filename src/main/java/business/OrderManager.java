package business;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import dao.CartDAO;
import dao.OrderDAO;
import daoimpl.CartDAOImpl;
import daoimpl.OrderDAOImpl;
import model.Cart;
import model.Order;
import model.OrderItem;
import util.DBConnection;

public class OrderManager {

	private OrderDAO orderDAO =
			new OrderDAOImpl();

	private CartDAO cartDAO =
			new CartDAOImpl();


	/**
	 * Places an order for a user.
	 *
	 * Returns:
	 * > 0  → order successfully created
	 * 0   → order failed
	 */
	public int createOrder(int userId) {

		Connection con = null;

		try {

			// ==========================================
			// 1. Open database connection
			// ==========================================

			con = DBConnection.getConnection();

			// Start transaction
			con.setAutoCommit(false);


			// ==========================================
			// 2. Get user's cart
			// ==========================================

			List<Cart> cartItems =
					cartDAO.getCartByUser(
							con,
							userId
					);


			// Empty cart
			if (cartItems == null
					|| cartItems.isEmpty()) {

				con.rollback();
				return 0;
			}


			// ==========================================
			// 3. Calculate total
			// ==========================================

			double total = 0;

			for (Cart c : cartItems) {

				if (c.getQuantity() <= 0
						|| c.getPrice() < 0) {

					con.rollback();
					return 0;
				}

				total +=
						c.getPrice()
								* c.getQuantity();
			}


			// ==========================================
			// 4. Validate and reduce stock
			// ==========================================

			for (Cart c : cartItems) {

				boolean stockUpdated =
						orderDAO.reduceStock(
								con,
								c.getProductId(),
								c.getQuantity()
						);


				// Stock not available
				if (!stockUpdated) {

					con.rollback();

					return 0;
				}
			}


			// ==========================================
			// 5. Create order
			// ==========================================

			Order order =
					new Order();

			order.setUserId(userId);

			order.setTotalAmount(total);

			order.setStatus("PLACED");


			/*
			 * D-Mart fulfillment type.
			 *
			 * Currently our checkout flow supports
			 * delivery, so every newly created order
			 * is marked as DELIVERY.
			 *
			 * Address/pickup details can be added later
			 * when we build that part of checkout.
			 */
			order.setFulfillmentType("DELIVERY");

			order.setDeliveryAddress(null);

			order.setPickupStoreId(null);

			order.setPickupDate(null);

			order.setPickupSlot(null);


			int orderId =
					orderDAO.createOrder(
							con,
							order
					);


			// Order creation failed
			if (orderId == 0) {

				con.rollback();

				return 0;
			}


			// ==========================================
			// 6. Create order items
			// ==========================================

			List<OrderItem> items =
					new ArrayList<>();


			for (Cart c : cartItems) {

				OrderItem item =
						new OrderItem();

				item.setProductId(
						c.getProductId()
				);

				item.setPrice(
						c.getPrice()
				);

				item.setQuantity(
						c.getQuantity()
				);

				items.add(item);
			}


			// Insert order items
			orderDAO.addOrderItems(
					con,
					orderId,
					items
			);


			// ==========================================
			// 7. Clear cart
			// ==========================================

			cartDAO.clearCart(
					con,
					userId
			);


			// ==========================================
			// 8. Commit everything
			// ==========================================

			con.commit();


			return orderId;


		} catch (Exception e) {

			// ==========================================
			// Rollback everything if anything fails
			// ==========================================

			try {

				if (con != null) {
					con.rollback();
				}

			} catch (Exception rollbackException) {

				rollbackException.printStackTrace();
			}


			e.printStackTrace();

			return 0;


		} finally {

			// ==========================================
			// Close connection
			// ==========================================

			try {

				if (con != null) {
					con.close();
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}
}