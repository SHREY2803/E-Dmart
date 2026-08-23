package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.OrderDAO;
import model.Order;
import model.OrderItem;
import util.DBConnection;

public class OrderDAOImpl implements OrderDAO {

	// =========================================================
	// 1. CREATE ORDER
	// =========================================================

	@Override
	public int createOrder(Connection con, Order order) {

		int orderId = 0;

		String query =
				"INSERT INTO orders " +
						"(user_id, total_amount, status, fulfillment_type, " +
						"delivery_address, pickup_store_id, pickup_date, pickup_slot) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps =
					 con.prepareStatement(
							 query,
							 Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, order.getUserId());

			ps.setDouble(2, order.getTotalAmount());

			ps.setString(3, order.getStatus());

			ps.setString(4, order.getFulfillmentType());

			ps.setString(5, order.getDeliveryAddress());

			if (order.getPickupStoreId() != null) {
				ps.setInt(6, order.getPickupStoreId());
			} else {
				ps.setNull(6, java.sql.Types.INTEGER);
			}

			if (order.getPickupDate() != null) {
				ps.setDate(7, order.getPickupDate());
			} else {
				ps.setNull(7, java.sql.Types.DATE);
			}

			ps.setString(8, order.getPickupSlot());

			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {

				if (rs.next()) {
					orderId = rs.getInt(1);
				}
			}

			if (orderId == 0) {

				throw new RuntimeException(
						"Order was inserted but no generated order ID was returned."
				);
			}

			return orderId;

		} catch (Exception e) {

			throw new RuntimeException(
					"Failed to create order",
					e
			);
		}
	}


	// =========================================================
	// 2. INSERT ORDER ITEMS
	// =========================================================

	@Override
	public void addOrderItems(
			Connection con,
			int orderId,
			List<OrderItem> items) {

		String query =
				"INSERT INTO order_items " +
						"(order_id, product_id, product_name, price, quantity) " +
						"SELECT ?, id, name, ?, ? " +
						"FROM products " +
						"WHERE id = ?";

		try (PreparedStatement ps =
					 con.prepareStatement(query)) {

			for (OrderItem item : items) {

				ps.setInt(1, orderId);

				ps.setDouble(2, item.getPrice());

				ps.setInt(3, item.getQuantity());

				ps.setInt(4, item.getProductId());

				ps.addBatch();
			}

			ps.executeBatch();

		} catch (Exception e) {

			throw new RuntimeException(
					"Failed to create order items",
					e
			);
		}
	}


	// =========================================================
	// 3. REDUCE PRODUCT STOCK
	// =========================================================

	@Override
	public boolean reduceStock(
			Connection con,
			int productId,
			int quantity) {

		String sql =
				"UPDATE products " +
						"SET quantity = quantity - ? " +
						"WHERE id = ? " +
						"AND is_active = true " +
						"AND quantity >= ?";

		try (PreparedStatement ps =
					 con.prepareStatement(sql)) {

			ps.setInt(1, quantity);
			ps.setInt(2, productId);
			ps.setInt(3, quantity);

			int rowsUpdated = ps.executeUpdate();

			return rowsUpdated > 0;

		} catch (Exception e) {

			throw new RuntimeException(
					"Failed to reduce stock for product ID: "
							+ productId,
					e
			);
		}
	}


	// =========================================================
	// 4. GET ORDERS BY USER
	// =========================================================

	@Override
	public List<Order> getOrdersByUser(int userId) {

		List<Order> orders = new ArrayList<>();

		String query =
				"SELECT * " +
						"FROM orders " +
						"WHERE user_id = ? " +
						"ORDER BY order_date DESC";

		try (Connection con = DBConnection.getConnection();
		     PreparedStatement ps = con.prepareStatement(query)) {

			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					Order o = new Order();

					o.setId(rs.getInt("id"));

					o.setUserId(
							rs.getInt("user_id")
					);

					o.setOrderDate(
							rs.getTimestamp("order_date")
					);

					o.setTotalAmount(
							rs.getDouble("total_amount")
					);

					o.setStatus(
							rs.getString("status")
					);

					// Fulfillment details
					o.setFulfillmentType(
							rs.getString("fulfillment_type")
					);

					o.setDeliveryAddress(
							rs.getString("delivery_address")
					);

					int pickupStoreId =
							rs.getInt("pickup_store_id");

					if (!rs.wasNull()) {
						o.setPickupStoreId(pickupStoreId);
					}

					o.setPickupDate(
							rs.getDate("pickup_date")
					);

					o.setPickupSlot(
							rs.getString("pickup_slot")
					);

					// Order items
					o.setItems(
							getOrderItems(o.getId())
					);

					orders.add(o);
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return orders;
	}


	// =========================================================
	// 5. GET ORDER ITEMS
	// =========================================================

	@Override
	public List<OrderItem> getOrderItems(int orderId) {

		List<OrderItem> items = new ArrayList<>();

		String query = """
                SELECT
                    oi.id,
                    oi.order_id,
                    oi.product_id,
                    oi.product_name,
                    oi.price,
                    oi.quantity,
                    p.image_url
                FROM order_items oi
                JOIN products p
                    ON oi.product_id = p.id
                WHERE oi.order_id = ?
                """;

		try (Connection con = DBConnection.getConnection();
		     PreparedStatement ps = con.prepareStatement(query)) {

			ps.setInt(1, orderId);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					OrderItem item = new OrderItem();

					item.setId(
							rs.getInt("id")
					);

					item.setOrderId(
							rs.getInt("order_id")
					);

					item.setProductId(
							rs.getInt("product_id")
					);

					item.setPrice(
							rs.getDouble("price")
					);

					item.setQuantity(
							rs.getInt("quantity")
					);

					item.setProductName(
							rs.getString("product_name")
					);

					item.setImageUrl(
							rs.getString("image_url")
					);

					items.add(item);
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return items;
	}


	// =========================================================
	// 6. UPDATE ORDER STATUS
	// =========================================================

	@Override
	public void updateOrderStatus(
			int orderId,
			String status) {

		String sql =
				"UPDATE orders " +
						"SET status = ? " +
						"WHERE id = ?";

		try (Connection con = DBConnection.getConnection();
		     PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, status);

			ps.setInt(2, orderId);

			ps.executeUpdate();

		} catch (Exception e) {

			throw new RuntimeException(
					"Failed to update order status",
					e
			);
		}
	}


	// =========================================================
	// 7. GET ORDER AMOUNT
	// =========================================================

	@Override
	public double getOrderAmount(int orderId) {

		double amount = 0;

		String sql =
				"SELECT total_amount " +
						"FROM orders " +
						"WHERE id = ?";

		try (Connection con = DBConnection.getConnection();
		     PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, orderId);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {

					amount =
							rs.getDouble("total_amount");
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return amount;
	}


	// =========================================================
	// 8. GET PURCHASED ITEMS
	// =========================================================
	// Kept because it is required by the existing DAO interface.
	// For the D-Mart project, order history is handled through
	// getOrdersByUser() instead.

	@Override
	public List<OrderItem> getPurchasedGames(int userId) {

		List<OrderItem> items = new ArrayList<>();

		String query = """
                SELECT DISTINCT
                    p.id AS product_id,
                    p.name,
                    p.image_url,
                    oi.price
                FROM orders o
                JOIN order_items oi
                    ON o.id = oi.order_id
                JOIN products p
                    ON oi.product_id = p.id
                JOIN payments pay
                    ON o.id = pay.order_id
                WHERE o.user_id = ?
                  AND pay.status = 'SUCCESS'
                """;

		try (Connection con = DBConnection.getConnection();
		     PreparedStatement ps = con.prepareStatement(query)) {

			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					OrderItem item = new OrderItem();

					item.setProductId(
							rs.getInt("product_id")
					);

					item.setProductName(
							rs.getString("name")
					);

					item.setImageUrl(
							rs.getString("image_url")
					);

					item.setPrice(
							rs.getDouble("price")
					);

					items.add(item);
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return items;
	}


	// =========================================================
	// 9. GET ALL ORDERS FOR ADMIN
	// =========================================================

	@Override
	public List<Order> getAllAdminOrders() {

		List<Order> orders = new ArrayList<>();

		String sql = """
                SELECT
                    o.*,
                    u.name AS user_name
                FROM orders o
                JOIN users u
                    ON o.user_id = u.id
                ORDER BY o.order_date DESC
                """;

		try (Connection con = DBConnection.getConnection();
		     PreparedStatement ps = con.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {

				Order o = new Order();

				o.setId(
						rs.getInt("id")
				);

				o.setUserId(
						rs.getInt("user_id")
				);

				o.setUserName(
						rs.getString("user_name")
				);

				o.setTotalAmount(
						rs.getDouble("total_amount")
				);

				o.setStatus(
						rs.getString("status")
				);

				o.setOrderDate(
						rs.getTimestamp("order_date")
				);

				// Fulfillment details
				o.setFulfillmentType(
						rs.getString("fulfillment_type")
				);

				o.setDeliveryAddress(
						rs.getString("delivery_address")
				);

				int pickupStoreId =
						rs.getInt("pickup_store_id");

				if (!rs.wasNull()) {
					o.setPickupStoreId(pickupStoreId);
				}

				o.setPickupDate(
						rs.getDate("pickup_date")
				);

				o.setPickupSlot(
						rs.getString("pickup_slot")
				);

				// Fetch order items
				o.setItems(
						getOrderItems(o.getId())
				);

				orders.add(o);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return orders;
	}
}