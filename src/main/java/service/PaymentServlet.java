package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import dao.CartDAO;
import daoimpl.CartDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.DBConnection;

@WebServlet("/process-payment")
public class PaymentServlet extends HttpServlet {

	private CartDAO cartDAO = new CartDAOImpl();

	@Override
	protected void doPost(
			HttpServletRequest req,
			HttpServletResponse res)
			throws ServletException, IOException {

		// ==========================================
		// 1. Check login
		// ==========================================

		HttpSession session = req.getSession(false);

		if (session == null ||
				session.getAttribute("userId") == null) {

			res.sendRedirect(
					req.getContextPath() + "/login.jsp"
			);

			return;
		}

		int userId = (int) session.getAttribute("userId");


		// ==========================================
		// 2. Read parameters
		// ==========================================

		String orderIdParameter =
				req.getParameter("orderId");

		String paymentMethod =
				req.getParameter("paymentMethod");


		// ==========================================
		// 3. Validate parameters
		// ==========================================

		if (orderIdParameter == null ||
				paymentMethod == null ||
				paymentMethod.trim().isEmpty()) {

			res.sendRedirect(
					req.getContextPath()
							+ "/payment-failed.jsp?reason=invalid_details"
			);

			return;
		}


		int orderId;

		try {

			orderId =
					Integer.parseInt(orderIdParameter);

		} catch (NumberFormatException e) {

			res.sendRedirect(
					req.getContextPath()
							+ "/payment-failed.jsp?reason=invalid_order"
			);

			return;
		}


		paymentMethod =
				paymentMethod.trim();


		// ==========================================
		// 4. Process payment
		// ==========================================

		try (
				Connection con =
						DBConnection.getConnection()
		) {

			con.setAutoCommit(false);


			// ==========================================
			// 5. Verify order belongs to logged-in user
			// ==========================================

			String orderSql =
					"SELECT status, total_amount " +
							"FROM orders " +
							"WHERE id = ? " +
							"AND user_id = ?";

			String currentStatus;
			double totalAmount;


			try (
					PreparedStatement ps =
							con.prepareStatement(orderSql)
			) {

				ps.setInt(1, orderId);
				ps.setInt(2, userId);

				try (
						ResultSet rs =
								ps.executeQuery()
				) {

					if (!rs.next()) {

						con.rollback();

						res.sendRedirect(
								req.getContextPath()
										+ "/payment-failed.jsp?reason=invalid_order"
						);

						return;
					}

					currentStatus =
							rs.getString("status");

					totalAmount =
							rs.getDouble("total_amount");
				}
			}


			// ==========================================
			// 6. Prevent duplicate payment
			// ==========================================

			/*
			 * In our eDMart database, a successfully
			 * paid order moves from PLACED -> CONFIRMED.
			 *
			 * There is no PAID status in orders.
			 */

			if ("CONFIRMED".equalsIgnoreCase(currentStatus)) {

				con.rollback();

				res.sendRedirect(
						req.getContextPath()
								+ "/order-success.jsp?orderId="
								+ orderId
				);

				return;
			}


			// ==========================================
			// 7. Generate transaction reference
			// ==========================================

			String transactionReference =
					"TXN-" +
							UUID.randomUUID()
									.toString()
									.substring(0, 8)
									.toUpperCase();


			// ==========================================
			// 8. Save payment
			// ==========================================

			String paymentSql =
					"INSERT INTO payments " +
							"(order_id, payment_method, status, " +
							"transaction_reference, paid_at) " +
							"VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";


			try (
					PreparedStatement ps =
							con.prepareStatement(paymentSql)
			) {

				ps.setInt(1, orderId);
				ps.setString(2, paymentMethod);
				ps.setString(3, "SUCCESS");
				ps.setString(
						4,
						transactionReference
				);

				ps.executeUpdate();
			}


			// ==========================================
			// 9. Update order status
			// ==========================================

			/*
			 * orders.status does NOT contain PAID.
			 *
			 * Valid status values include:
			 *
			 * PLACED
			 * CONFIRMED
			 * PREPARING
			 * READY_FOR_PICKUP
			 * OUT_FOR_DELIVERY
			 * DELIVERED
			 * COMPLETED
			 * CANCELLED
			 *
			 * After successful payment:
			 *
			 * PLACED -> CONFIRMED
			 */

			String updateOrderSql =
					"UPDATE orders " +
							"SET status = ? " +
							"WHERE id = ? " +
							"AND user_id = ?";


			try (
					PreparedStatement ps =
							con.prepareStatement(
									updateOrderSql
							)
			) {

				ps.setString(1, "CONFIRMED");
				ps.setInt(2, orderId);
				ps.setInt(3, userId);

				int updated =
						ps.executeUpdate();

				if (updated == 0) {

					con.rollback();

					res.sendRedirect(
							req.getContextPath()
									+ "/payment-failed.jsp?reason=order_update_failed"
					);

					return;
				}
			}


			// ==========================================
			// 10. Clear cart after successful payment
			// ==========================================

			cartDAO.clearCart(userId);


			// ==========================================
			// 11. Commit everything
			// ==========================================

			con.commit();


			// ==========================================
			// 12. Redirect to success page
			// ==========================================

			res.sendRedirect(
					req.getContextPath()
							+ "/order-success.jsp?orderId="
							+ orderId
			);


		} catch (Exception e) {

			e.printStackTrace();

			res.sendRedirect(
					req.getContextPath()
							+ "/payment-failed.jsp?reason=payment_error"
			);
		}
	}
}