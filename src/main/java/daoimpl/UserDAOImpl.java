package daoimpl;

import dao.UserDAO;
import model.User;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

	// Register new user
	@Override
	public boolean registerUser(User user) {

		boolean status = false;
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "INSERT INTO users(name, email, password, role) VALUES (?, ?, ?, ?)";

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getRole());

			status = ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return status;
	}

	// Login user
	@Override
	public User login(String email, String password) {

		User user = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, email);
			ps.setString(2, password);

			rs = ps.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
			}

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

		return user;
	}

	// Check if email already exists
	@Override
	public boolean isEmailExists(String email) {

		boolean exists = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "SELECT id FROM users WHERE email = ?";

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, email);

			rs = ps.executeQuery();
			exists = rs.next();

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

		return exists;
	}

	@Override
	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		User user = null;
		String sql = "SELECT id, name, email FROM users WHERE id = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public boolean updateProfile(int userId, String name, String email) {
		String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
		boolean updated = false;

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, name);
			ps.setString(2, email);
			ps.setInt(3, userId);

			updated = ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return updated;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub

		List<User> users = new ArrayList<>();

		String sql = "SELECT id, name, email, role, created_at FROM users ORDER BY created_at DESC";

		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setName(rs.getString("name"));
				u.setEmail(rs.getString("email"));
				u.setRole(rs.getString("role"));
				users.add(u);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
}
