package service.admin;

import java.io.File;
import java.io.IOException;

import dao.ProductDAO;
import daoimpl.ProductDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Product;

@WebServlet("/admin/DeleteAdminProduct")
public class DeleteAdminProductServlet extends HttpServlet {

	private ProductDAO productDAO =
			new ProductDAOImpl();

	@Override
	protected void doPost(
			HttpServletRequest req,
			HttpServletResponse res)
			throws ServletException, IOException {

		// ==========================================
		// 1. Check ADMIN access
		// ==========================================

		HttpSession session =
				req.getSession(false);

		if (session == null ||
				!"ADMIN".equalsIgnoreCase(
						(String) session.getAttribute("role"))) {

			res.sendRedirect(
					req.getContextPath()
							+ "/access-denied.jsp"
			);
			return;
		}


		try {

			// ==========================================
			// 2. Get product ID
			// ==========================================

			String idParameter =
					req.getParameter("id");

			if (idParameter == null ||
					idParameter.trim().isEmpty()) {

				res.sendRedirect(
						req.getContextPath()
								+ "/admin/AdminProduct?error=invalid"
				);
				return;
			}

			int productId =
					Integer.parseInt(idParameter);


			// ==========================================
			// 3. Find product
			// ==========================================

			Product product =
					productDAO.getProductById(productId);

			if (product == null) {

				res.sendRedirect(
						req.getContextPath()
								+ "/admin/AdminProduct?error=notfound"
				);
				return;
			}


			// ==========================================
			// 4. Delete product from database
			// ==========================================

			boolean deleted =
					productDAO.deleteProduct(productId);


			// ==========================================
			// 5. Delete image only if DB deletion
			//    was successful
			// ==========================================

			if (deleted) {

				String imageUrl =
						product.getImageUrl();

				if (imageUrl != null &&
						!imageUrl.trim().isEmpty()) {

					File imageFile =
							new File(
									getServletContext()
											.getRealPath("/")
											+ imageUrl
							);

					if (imageFile.exists()) {
						imageFile.delete();
					}
				}


				// ==========================================
				// 6. Redirect after successful deletion
				// ==========================================

				res.sendRedirect(
						req.getContextPath()
								+ "/admin/AdminProduct"
				);

			} else {

				// Database deletion failed

				res.sendRedirect(
						req.getContextPath()
								+ "/admin/AdminProduct?error=failed"
				);
			}


		} catch (NumberFormatException e) {

			res.sendRedirect(
					req.getContextPath()
							+ "/admin/AdminProduct?error=invalid"
			);

		} catch (Exception e) {

			e.printStackTrace();

			res.sendRedirect(
					req.getContextPath()
							+ "/admin/AdminProduct?error=true"
			);
		}
	}
}