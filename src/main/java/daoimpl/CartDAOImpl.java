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

	@Override
	public boolean addToCart(int userId, int productId) {
		Connection con = null;
		PreparedStatement ps = null;

		String query = """
				    INSERT INTO cart(user_id, product_id, quantity)
				    VALUES (?, ?, 1)
				    ON DUPLICATE KEY UPDATE quantity = quantity + 1
				""";
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);

			ps.setInt(1, userId);
			ps.setInt(2, productId);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
		}
		return false;
	}

	@Override
	public List<Cart> getCartByUser(int userId) {
		try (Connection con = DBConnection.getConnection()) {
			return getCartByUser(con, userId); // 👈 delegate
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public List<Cart> getCartByUser(Connection con, int userId) {
		// TODO Auto-generated method stub
		List<Cart> list = new ArrayList<Cart>();

		String query = """
				    SELECT c.id, c.product_id, c.quantity, p.name, p.price, p.image_url
				    FROM cart c
				    JOIN products p ON c.product_id = p.id
				    WHERE c.user_id = ?
				""";
		try (PreparedStatement ps = con.prepareStatement(query)) {

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Cart c = new Cart();
				c.setId(rs.getInt("id"));
				c.setProductId(rs.getInt("product_id"));
				c.setQuantity(rs.getInt("quantity"));
				c.setProductName(rs.getString("name"));
				c.setPrice(rs.getDouble("price"));
				c.setImageUrl(rs.getString("image_url"));
				list.add(c);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean removeFromCart(int cartId) {

		String getQtySql = "SELECT quantity FROM cart WHERE id = ?";
		String updateSql = "UPDATE cart SET quantity = quantity - 1 WHERE id = ?";
		String deleteSql = "DELETE FROM cart WHERE id = ?";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBConnection.getConnection();

			// 1️⃣ Check current quantity
			ps = con.prepareStatement(getQtySql);
			ps.setInt(1, cartId);
			rs = ps.executeQuery();

			if (rs.next()) {
				int quantity = rs.getInt("quantity");

				// 2️⃣ If quantity > 1 → decrement
				if (quantity > 1) {
					ps = con.prepareStatement(updateSql);
					ps.setInt(1, cartId);
					ps.executeUpdate();
				}
				// 3️⃣ If quantity == 1 → delete row
				else {
					ps = con.prepareStatement(deleteSql);
					ps.setInt(1, cartId);
					ps.executeUpdate();
				}
			}

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	@Override
	public void clearCart(int userId) {
		try (Connection con = DBConnection.getConnection()) {
			clearCart(con, userId); // 👈 delegate
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clearCart(Connection con, int userId) {
		// TODO Auto-generated method stub

		String query = "DELETE FROM cart WHERE user_id = ?";

		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, userId);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
