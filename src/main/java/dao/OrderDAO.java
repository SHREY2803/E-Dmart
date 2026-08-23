package dao;

import java.sql.Connection;
import java.util.List;

import model.Order;
import model.OrderItem;

public interface OrderDAO {

	// Create new order and return generated orderId
	int createOrder(Connection con, Order order);

	// Add items to an order
	void addOrderItems(
			Connection con,
			int orderId,
			List<OrderItem> items);

	// Reduce product stock during checkout
	boolean reduceStock(
			Connection con,
			int productId,
			int quantity);

	// Get all orders of a user
	List<Order> getOrdersByUser(int userId);

	// Get items of a specific order
	List<OrderItem> getOrderItems(int orderId);

	// Update order status
	void updateOrderStatus(
			int orderId,
			String status);

	// Get order amount
	double getOrderAmount(int orderId);

	// Get purchased products
	List<OrderItem> getPurchasedGames(int userId);

	// Get all orders for admin
	List<Order> getAllAdminOrders();
}