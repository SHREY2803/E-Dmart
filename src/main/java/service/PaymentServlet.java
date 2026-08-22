package service;

import java.io.IOException;

import dao.CardDAO;
import dao.CartDAO;
import dao.OrderDAO;
import daoimpl.CardDAOImpl;
import daoimpl.CartDAOImpl;
import daoimpl.OrderDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Card;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/process-payment")
public class PaymentServlet extends HttpServlet {

	private CardDAO cardDAO = new CardDAOImpl();
	private OrderDAO orderDAO = new OrderDAOImpl();
	private CartDAO cartDAO = new CartDAOImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			res.sendRedirect("login.jsp");
			return;
		}

		int userId = (int) session.getAttribute("userId");
		int orderId = Integer.parseInt(req.getParameter("orderId"));

		// Card details from UI
		String cardNumber = req.getParameter("cardNumber");
		String cardHolder = req.getParameter("cardHolder");
		String expiry = req.getParameter("expiry");
		String cvv = req.getParameter("cvv");

		String expiryMonth = expiry.split("/")[0];
		String expiryYear = expiry.split("/")[1];

		// 1️⃣ Validate card
		Card card = cardDAO.validateCard(userId, cardNumber, cardHolder, expiryMonth, expiryYear, cvv);

		if (card == null) {
			orderDAO.updateOrderStatus(orderId, "PAYMENT_FAILED");
			savePayment(orderId, "FAILED", "INVALID_CARD");
			res.sendRedirect("payment-failed.jsp?reason=invalid_card");
			return;
		}

		// 2️⃣ Fetch order amount
		double orderAmount = orderDAO.getOrderAmount(orderId);

//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps =
//                     con.prepareStatement("SELECT total_amount FROM orders WHERE id = ?")) {
//
//            ps.setInt(1, orderId);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                orderAmount = rs.getDouble("total_amount");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

		// 3️⃣ Check balance
		if (card.getBalance() < orderAmount) {
			orderDAO.updateOrderStatus(orderId, "PAYMENT_FAILED");
			savePayment(orderId, "FAILED", "INSUFFICIENT_BALANCE");
			res.sendRedirect("payment-failed.jsp?reason=insufficient_balance");
			return;
		}

		// 4️⃣ SUCCESS FLOW
		cardDAO.deductBalance(card.getId(), orderAmount);
		orderDAO.updateOrderStatus(orderId, "PAID");
		cartDAO.clearCart(userId);
		savePayment(orderId, "SUCCESS", null);

		// Save payment record
		
//        try (Connection con = DBConnection.getConnection();
//             PreparedStatement ps =
//                     con.prepareStatement(
//                             "INSERT INTO final_payments(order_id, status) VALUES (?, ?)")) {
//
//            ps.setInt(1, orderId);
//            ps.setString(2, "SUCCESS");
//            ps.executeUpdate();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

		res.sendRedirect("order-success.jsp?orderId=" + orderId);
	}

	private void savePayment(int orderId, String status, String reason) {
		String sql = "INSERT INTO final_payments(order_id, status, failure_reason) VALUES (?, ?, ?)";
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, orderId);
			ps.setString(2, status);
			ps.setString(3, reason);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
