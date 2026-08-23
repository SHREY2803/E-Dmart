package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.CartDAO;
import model.Cart;
import util.DBConnection;

public class CartDAOImpl implements CartDAO {

	// ==========================================
	// Add product to cart
	// ==========================================

	@Override
	public boolean addToCart(int userId, int productId) {

		String stockSql =
				"SELECT quantity FROM products " +
						"WHERE id = ? AND is_active = true";

		String cartSql =
				"SELECT quantity FROM cart " +
						"WHERE user_id = ? AND product_id = ?";

		String insertSql =
				"INSERT INTO cart(user_id, product_id, quantity) " +
						"VALUES (?, ?, 1)";

		String updateSql =
				"UPDATE cart SET quantity = quantity + 1 " +
						"WHERE user_id = ? AND product_id = ?";

		try (Connection con = DBConnection.getConnection()) {

			int stock = 0;

			// ------------------------------------------
			// 1. Get current product stock
			// ------------------------------------------

			try (PreparedStatement ps =
						 con.prepareStatement(stockSql)) {

				ps.setInt(1, productId);

				try (ResultSet rs = ps.executeQuery()) {

					if (!rs.next()) {
						return false;
					}

					stock = rs.getInt("quantity");
				}
			}

			// Product is out of stock
			if (stock <= 0) {
				return false;
			}


			// ------------------------------------------
			// 2. Check current cart quantity
			// ------------------------------------------

			int cartQuantity = 0;

			try (PreparedStatement ps =
						 con.prepareStatement(cartSql)) {

				ps.setInt(1, userId);
				ps.setInt(2, productId);

				try (ResultSet rs = ps.executeQuery()) {

					if (rs.next()) {
						cartQuantity =
								rs.getInt("quantity");
					}
				}
			}


			// ------------------------------------------
			// 3. Don't allow cart quantity > stock
			// ------------------------------------------

			if (cartQuantity >= stock) {
				return false;
			}


			// ------------------------------------------
			// 4. Insert or increase quantity
			// ------------------------------------------

			if (cartQuantity == 0) {

				try (PreparedStatement ps =
							 con.prepareStatement(insertSql)) {

					ps.setInt(1, userId);
					ps.setInt(2, productId);

					return ps.executeUpdate() > 0;
				}

			} else {

				try (PreparedStatement ps =
							 con.prepareStatement(updateSql)) {

					ps.setInt(1, userId);
					ps.setInt(2, productId);

					return ps.executeUpdate() > 0;
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}


	// ==========================================
	// Get cart
	// ==========================================

	@Override
	public List<Cart> getCartByUser(int userId) {

		try (Connection con =
					 DBConnection.getConnection()) {

			return getCartByUser(con, userId);

		} catch (Exception e) {

			e.printStackTrace();

			return new ArrayList<>();
		}
	}


	// ==========================================
	// Get cart using existing connection
	// ==========================================

	@Override
	public List<Cart> getCartByUser(
			Connection con,
			int userId) {

		List<Cart> list =
				new ArrayList<>();

		String query = """
                SELECT c.id,
                       c.user_id,
                       c.product_id,
                       c.quantity,
                       p.name,
                       p.price,
                       p.image_url
                FROM cart c
                JOIN products p
                  ON c.product_id = p.id
                WHERE c.user_id = ?
                """;

		try (PreparedStatement ps =
					 con.prepareStatement(query)) {

			ps.setInt(1, userId);

			try (ResultSet rs =
						 ps.executeQuery()) {

				while (rs.next()) {

					Cart c = new Cart();

					c.setId(
							rs.getInt("id")
					);

					c.setUserId(
							rs.getInt("user_id")
					);

					c.setProductId(
							rs.getInt("product_id")
					);

					c.setQuantity(
							rs.getInt("quantity")
					);

					c.setProductName(
							rs.getString("name")
					);

					c.setPrice(
							rs.getDouble("price")
					);

					c.setImageUrl(
							rs.getString("image_url")
					);

					list.add(c);
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return list;
	}


	// ==========================================
	// Remove/decrease cart item
	// ==========================================

	@Override
	public boolean removeFromCart(int cartId) {

		String getQtySql =
				"SELECT quantity FROM cart WHERE id = ?";

		String updateSql =
				"UPDATE cart " +
						"SET quantity = quantity - 1 " +
						"WHERE id = ?";

		String deleteSql =
				"DELETE FROM cart WHERE id = ?";

		try (Connection con =
					 DBConnection.getConnection()) {

			int quantity;

			// ------------------------------------------
			// 1. Get current quantity
			// ------------------------------------------

			try (PreparedStatement ps =
						 con.prepareStatement(getQtySql)) {

				ps.setInt(1, cartId);

				try (ResultSet rs =
							 ps.executeQuery()) {

					if (!rs.next()) {
						return false;
					}

					quantity =
							rs.getInt("quantity");
				}
			}


			// ------------------------------------------
			// 2. Decrease or remove
			// ------------------------------------------

			if (quantity > 1) {

				try (PreparedStatement ps =
							 con.prepareStatement(updateSql)) {

					ps.setInt(1, cartId);

					return ps.executeUpdate() > 0;
				}

			} else {

				try (PreparedStatement ps =
							 con.prepareStatement(deleteSql)) {

					ps.setInt(1, cartId);

					return ps.executeUpdate() > 0;
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}


	// ==========================================
	// Clear cart
	// ==========================================

	@Override
	public void clearCart(int userId) {

		try (Connection con =
					 DBConnection.getConnection()) {

			clearCart(con, userId);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}


	// ==========================================
	// Clear cart using existing connection
	// ==========================================

	@Override
	public void clearCart(
			Connection con,
			int userId) {

		String query =
				"DELETE FROM cart WHERE user_id = ?";

		try (PreparedStatement ps =
					 con.prepareStatement(query)) {

			ps.setInt(1, userId);

			ps.executeUpdate();

		} catch (Exception e) {

			throw new RuntimeException(
					"Failed to clear cart for user ID: "
							+ userId,
					e
			);
		}
	}
}