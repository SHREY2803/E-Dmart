package dao;

import java.sql.Connection;
import java.util.List;

import model.Order;
import model.OrderItem;

public interface OrderDAO {
	// Create new order and return generated orderId
	int createOrder(Connection con, Order order);

	// Add items to an order
	void addOrderItems(Connection con, int orderId, List<OrderItem> items);

	// Get all orders of a user
	List<Order> getOrdersByUser(int userId);

	// Get items of a specific order
	List<OrderItem> getOrderItems(int orderId);

	void updateOrderStatus(int orderId, String status);

	double getOrderAmount(int orderId);

	List<OrderItem> getPurchasedGames(int userId);
	
	List<Order> getAllAdminOrders();
}
