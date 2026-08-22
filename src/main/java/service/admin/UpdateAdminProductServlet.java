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

@WebServlet("/admin/UpdateAdminProduct")
@MultipartConfig
public class UpdateAdminProductServlet extends HttpServlet {
	private ProductDAO productDAO = new ProductDAOImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = req.getSession(false);
		if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
			res.sendRedirect(req.getContextPath() + "/access-denied.jsp");
			return;
		}

		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		double price = Double.parseDouble(req.getParameter("price"));
		String category = req.getParameter("category");
		String description = req.getParameter("description");
		
		Product existing = productDAO.getProductById(id);
        String imageUrl = existing.getImageUrl();
        
        Part imagePart = req.getPart("image");
        
        if (imagePart != null && imagePart.getSize() > 0) {

            // delete old image
            File oldImage = new File(
                getServletContext().getRealPath("/") + imageUrl
            );
            if (oldImage.exists()) oldImage.delete();

            // save new image
            String fileName = System.currentTimeMillis() + "_" + imagePart.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("/") + "assets/images";
            imagePart.write(uploadPath + File.separator + fileName);

            imageUrl = "assets/images/" + fileName;
        }
        
        Product p = new Product();
        p.setId(id);
        p.setName(name);
        p.setPrice(price);
        p.setCategory(category);
        p.setDescription(description);
        p.setImageUrl(imageUrl);
        
        productDAO.updateProduct(p);

        res.sendRedirect(req.getContextPath() + "/admin/AdminProduct");
	}
}
