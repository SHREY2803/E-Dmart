package service.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import dao.CategoryDAO;
import dao.ProductDAO;
import daoimpl.CategoryDAOImpl;
import daoimpl.ProductDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import model.Category;
import model.Product;

@WebServlet("/admin/AddAdminProduct")
@MultipartConfig(
		fileSizeThreshold = 1024 * 1024,
		maxFileSize = 1024 * 1024 * 5,
		maxRequestSize = 1024 * 1024 * 10
)
public class AddAdminProductServlet extends HttpServlet {

	private ProductDAO productDAO = new ProductDAOImpl();
	private CategoryDAO categoryDAO = new CategoryDAOImpl();

	// ==========================================
	// GET → Open Add Product page
	// ==========================================
	@Override
	protected void doGet(
			HttpServletRequest req,
			HttpServletResponse res)
			throws ServletException, IOException {

		// Check ADMIN access
		HttpSession session = req.getSession(false);

		if (session == null ||
				!"ADMIN".equalsIgnoreCase(
						(String) session.getAttribute("role"))) {

			res.sendRedirect(
					req.getContextPath() + "/access-denied.jsp"
			);
			return;
		}

		// Get categories dynamically from database
		List<Category> categories =
				categoryDAO.getAllCategories();

		req.setAttribute("categories", categories);

		// Open Add Product page
		req.getRequestDispatcher(
				"/admin/add-product.jsp"
		).forward(req, res);
	}


	// ==========================================
	// POST → Add Product
	// ==========================================
	@Override
	protected void doPost(
			HttpServletRequest req,
			HttpServletResponse res)
			throws ServletException, IOException {

		// Check ADMIN access
		HttpSession session = req.getSession(false);

		if (session == null ||
				!"ADMIN".equalsIgnoreCase(
						(String) session.getAttribute("role"))) {

			res.sendRedirect(
					req.getContextPath() + "/access-denied.jsp"
			);
			return;
		}

		try {

			// ==========================================
			// 1. Read form fields
			// ==========================================

			String name = req.getParameter("name");
			String priceParameter = req.getParameter("price");
			String categoryParameter = req.getParameter("category");
			String quantityParameter = req.getParameter("quantity");
			String description = req.getParameter("description");


			// ==========================================
			// 2. Basic validation
			// ==========================================

			if (name == null || name.trim().isEmpty()
					|| priceParameter == null
					|| categoryParameter == null
					|| quantityParameter == null
					|| description == null
					|| description.trim().isEmpty()) {

				res.sendRedirect(
						req.getContextPath()
								+ "/admin/AddAdminProduct?error=invalid"
				);
				return;
			}


			double price =
					Double.parseDouble(priceParameter);

			int categoryId =
					Integer.parseInt(categoryParameter);

			int quantity =
					Integer.parseInt(quantityParameter);


			// ==========================================
			// 3. Validate values
			// ==========================================

			if (price < 0 || quantity < 0) {

				res.sendRedirect(
						req.getContextPath()
								+ "/admin/AddAdminProduct?error=invalid"
				);
				return;
			}


			// Make sure selected category actually exists
			Category category =
					categoryDAO.getCategoryById(categoryId);

			if (category == null) {

				res.sendRedirect(
						req.getContextPath()
								+ "/admin/AddAdminProduct?error=invalid"
				);
				return;
			}


			// ==========================================
			// 4. Handle image
			// ==========================================

			Part imagePart =
					req.getPart("image");

			String imageUrl = null;

			if (imagePart != null
					&& imagePart.getSize() > 0) {

				String originalFileName =
						imagePart.getSubmittedFileName();

				String fileName =
						System.currentTimeMillis()
								+ "_"
								+ originalFileName;

				String uploadPath =
						getServletContext()
								.getRealPath("/")
								+ "assets/images";

				File uploadDir =
						new File(uploadPath);

				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}

				imagePart.write(
						uploadPath
								+ File.separator
								+ fileName
				);

				imageUrl =
						"assets/images/"
								+ fileName;
			}


			// ==========================================
			// 5. Create Product
			// ==========================================

			Product product =
					new Product();

			product.setName(name.trim());
			product.setPrice(price);
			product.setCategoryId(categoryId);
			product.setQuantity(quantity);
			product.setDescription(description.trim());
			product.setImageUrl(imageUrl);

			// Newly added products are active
			product.setActive(true);


			// ==========================================
			// 6. Save product
			// ==========================================

			boolean added =
					productDAO.addProduct(product);


			// ==========================================
			// 7. Redirect
			// ==========================================

			if (added) {

				res.sendRedirect(
						req.getContextPath()
								+ "/admin/AdminProduct"
				);

			} else {

				res.sendRedirect(
						req.getContextPath()
								+ "/admin/AddAdminProduct?error=failed"
				);
			}

		} catch (NumberFormatException e) {

			e.printStackTrace();

			res.sendRedirect(
					req.getContextPath()
							+ "/admin/AddAdminProduct?error=invalid"
			);

		} catch (Exception e) {

			e.printStackTrace();

			res.sendRedirect(
					req.getContextPath()
							+ "/admin/AddAdminProduct?error=true"
			);
		}
	}
}