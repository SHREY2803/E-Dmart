package daoimpl;

import dao.ProductDAO;
import model.Product;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

	@Override
	public boolean addProduct(Product product) {

		String sql = "INSERT INTO products " +
				"(name, description, category_id, price, quantity, image_url, is_active) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection con = DBConnection.getConnection();
		     PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, product.getName());
			ps.setString(2, product.getDescription());
			ps.setInt(3, product.getCategoryId());
			ps.setDouble(4, product.getPrice());
			ps.setInt(5, product.getQuantity());
			ps.setString(6, product.getImageUrl());
			ps.setBoolean(7, product.isActive());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Product> getAllProducts() {

		List<Product> products = new ArrayList<>();

		String sql = "SELECT id, name, description, category_id, " +
				"price, quantity, image_url, is_active " +
				"FROM products ORDER BY id DESC";

		try (Connection con = DBConnection.getConnection();
		     PreparedStatement ps = con.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {

				Product product = new Product();

				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setCategoryId(rs.getInt("category_id"));
				product.setPrice(rs.getDouble("price"));
				product.setQuantity(rs.getInt("quantity"));
				product.setImageUrl(rs.getString("image_url"));
				product.setActive(rs.getBoolean("is_active"));

				products.add(product);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return products;
	}

	@Override
	public Product getProductById(int id) {

		String sql = "SELECT id, name, description, category_id, " +
				"price, quantity, image_url, is_active " +
				"FROM products WHERE id = ?";

		try (Connection con = DBConnection.getConnection();
		     PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {

					Product product = new Product();

					product.setId(rs.getInt("id"));
					product.setName(rs.getString("name"));
					product.setDescription(rs.getString("description"));
					product.setCategoryId(rs.getInt("category_id"));
					product.setPrice(rs.getDouble("price"));
					product.setQuantity(rs.getInt("quantity"));
					product.setImageUrl(rs.getString("image_url"));
					product.setActive(rs.getBoolean("is_active"));

					return product;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean updateProduct(Product product) {

		String sql = "UPDATE products SET " +
				"name = ?, description = ?, category_id = ?, " +
				"price = ?, quantity = ?, image_url = ?, is_active = ? " +
				"WHERE id = ?";

		try (Connection con = DBConnection.getConnection();
		     PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, product.getName());
			ps.setString(2, product.getDescription());
			ps.setInt(3, product.getCategoryId());
			ps.setDouble(4, product.getPrice());
			ps.setInt(5, product.getQuantity());
			ps.setString(6, product.getImageUrl());
			ps.setBoolean(7, product.isActive());
			ps.setInt(8, product.getId());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateStock(int productId, int quantity) {

		String sql = "UPDATE products SET quantity = ? WHERE id = ?";

		try (Connection con = DBConnection.getConnection();
		     PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, quantity);
			ps.setInt(2, productId);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Product> searchProducts(String keyword) {

		List<Product> products = new ArrayList<>();

		String sql = "SELECT id, name, description, category_id, " +
				"price, quantity, image_url, is_active " +
				"FROM products " +
				"WHERE is_active = true " +
				"AND (name LIKE ? OR description LIKE ?) " +
				"ORDER BY name";

		try (Connection con = DBConnection.getConnection();
		     PreparedStatement ps = con.prepareStatement(sql)) {

			String searchKeyword = "%" + keyword + "%";

			ps.setString(1, searchKeyword);
			ps.setString(2, searchKeyword);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					Product product = new Product();

					product.setId(rs.getInt("id"));
					product.setName(rs.getString("name"));
					product.setDescription(rs.getString("description"));
					product.setCategoryId(rs.getInt("category_id"));
					product.setPrice(rs.getDouble("price"));
					product.setQuantity(rs.getInt("quantity"));
					product.setImageUrl(rs.getString("image_url"));
					product.setActive(rs.getBoolean("is_active"));

					products.add(product);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return products;
	}

	@Override
	public boolean deleteProduct(int productId) {

		String sql = "DELETE FROM products WHERE id = ?";

		try (Connection con = DBConnection.getConnection();
		     PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, productId);

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Product> getProductsByCategory(int categoryId) {

		List<Product> products = new ArrayList<>();

		String sql = "SELECT id, name, description, category_id, " +
				"price, quantity, image_url, is_active " +
				"FROM products " +
				"WHERE category_id = ? AND is_active = true " +
				"ORDER BY name";

		try (Connection con = DBConnection.getConnection();
		     PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, categoryId);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					Product product = new Product();

					product.setId(rs.getInt("id"));
					product.setName(rs.getString("name"));
					product.setDescription(rs.getString("description"));
					product.setCategoryId(rs.getInt("category_id"));
					product.setPrice(rs.getDouble("price"));
					product.setQuantity(rs.getInt("quantity"));
					product.setImageUrl(rs.getString("image_url"));
					product.setActive(rs.getBoolean("is_active"));

					products.add(product);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return products;
	}

}