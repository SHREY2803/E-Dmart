package daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.ProductDAO;
import model.Product;
import util.DBConnection;

public class ProductDAOImpl implements ProductDAO {

	@Override
	public boolean addProduct(Product product) {
		// TODO Auto-generated method stub
		boolean status = false;
		Connection con = null;
		PreparedStatement ps = null;

		String query = "INSERT INTO products(name, description, category, price, quantity, image_url) VALUES(?,?,?,?,?,?)";

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);

			ps.setString(1, product.getName());
			ps.setString(2, product.getDescription());
			ps.setString(3, product.getCategory());
			ps.setDouble(4, product.getPrice());
			ps.setInt(5, product.getQuantity());
			ps.setString(6, product.getImageUrl());

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

	@Override
	public boolean updateProduct(Product product) {
		// TODO Auto-generated method stub
		boolean status = false;
		Connection con = null;
		PreparedStatement ps = null;

		String query = "UPDATE products SET name=?, description=?, category=?, price=?, quantity=?, image_url=? WHERE id=?";

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);

			ps.setString(1, product.getName());
			ps.setString(2, product.getDescription());
			ps.setString(3, product.getCategory());
			ps.setDouble(4, product.getPrice());
			ps.setInt(5, product.getQuantity());
			ps.setString(6, product.getImageUrl());
			ps.setInt(7, product.getId());

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

	@Override
	public boolean deleteProduct(int productId) {
		// TODO Auto-generated method stub
		boolean status = false;
		Connection con = null;
		PreparedStatement ps = null;

		String query = "DELETE FROM products WHERE id=?";

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);

			ps.setInt(1, productId);

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

	@Override
	public List<Product> getAllProducts() {
		List<Product> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT * FROM products";
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setDescription(rs.getString("description"));
				p.setCategory(rs.getString("category"));
				p.setPrice(rs.getDouble("price"));
				p.setQuantity(rs.getInt("quantity"));
				p.setImageUrl(rs.getString("image_url"));

				list.add(p);
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
		return list;
	}

	@Override
	public Product getProductById(int productId) {
		// TODO Auto-generated method stub
		Product product = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT * FROM products WHERE id=?";

		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);

			ps.setInt(1, productId);

			rs = ps.executeQuery();

			if (rs.next()) {
				product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setCategory(rs.getString("category"));
				product.setPrice(rs.getDouble("price"));
				product.setQuantity(rs.getInt("quantity"));
				product.setImageUrl(rs.getString("image_url"));
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

		return product;
	}

}
