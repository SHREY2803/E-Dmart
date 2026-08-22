package service.admin;

import java.io.File;
import java.io.IOException;

import dao.ProductDAO;
import daoimpl.ProductDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.Product;

@WebServlet("/admin/AddAdminProduct")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
		maxFileSize = 1024 * 1024 * 5, // 5 MB
		maxRequestSize = 1024 * 1024 * 10)

public class AddAdminProductServlet extends HttpServlet {

	private ProductDAO productDAO = new ProductDAOImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(false);

		if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
			res.sendRedirect(req.getContextPath() + "/access-denied.jsp");
			return;
		}

		// Read form fields
		String name = req.getParameter("name");
		double price = Double.parseDouble(req.getParameter("price"));
		String category = req.getParameter("category");
		String description = req.getParameter("description");

		// Get uploaded file
		Part imagePart = req.getPart("image");
		String originalFileName = imagePart.getSubmittedFileName();

		// Generate unique filename
		String fileName = System.currentTimeMillis() + "_" + originalFileName;

		// Resolve upload path (assets/images)
		String uploadPath = getServletContext().getRealPath("/") + "assets/images";

		// Resolve upload path (assets/images)
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		// Save image to assets/images
		imagePart.write(uploadPath + File.separator + fileName);

		// Save relative path in DB
		String imageUrl = "assets/images/" + fileName;

		// Create product object
		Product product = new Product();
		product.setName(name);
		product.setPrice(price);
		product.setCategory(category);
		product.setDescription(description);
		product.setImageUrl(imageUrl);
		
		// Insert into DB
        productDAO.addProduct(product);
        
        //Redirect back to admin products page
        res.sendRedirect(req.getContextPath() + "/admin/AdminProduct");
	}
}
