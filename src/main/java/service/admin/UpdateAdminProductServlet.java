package service.admin;

import java.io.File;
import java.io.IOException;

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

@WebServlet("/admin/UpdateAdminProduct")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class UpdateAdminProductServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAOImpl();
    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse res)
            throws ServletException, IOException {

        // ==========================================
        // 1. Check ADMIN access
        // ==========================================

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
            // 2. Read form fields
            // ==========================================

            int id =
                    Integer.parseInt(
                            req.getParameter("id")
                    );

            String name =
                    req.getParameter("name");

            double price =
                    Double.parseDouble(
                            req.getParameter("price")
                    );

            int categoryId =
                    Integer.parseInt(
                            req.getParameter("category")
                    );

            int quantity =
                    Integer.parseInt(
                            req.getParameter("quantity")
                    );

            String description =
                    req.getParameter("description");


            // ==========================================
            // 3. Validate input
            // ==========================================

            if (name == null
                    || name.trim().isEmpty()
                    || description == null
                    || description.trim().isEmpty()
                    || price < 0
                    || quantity < 0) {

                res.sendRedirect(
                        req.getContextPath()
                                + "/admin/AdminProduct?error=invalid"
                );
                return;
            }


            // ==========================================
            // 4. Check category exists
            // ==========================================

            Category category =
                    categoryDAO.getCategoryById(categoryId);

            if (category == null) {

                res.sendRedirect(
                        req.getContextPath()
                                + "/admin/AdminProduct?error=invalid"
                );
                return;
            }


            // ==========================================
            // 5. Get existing product
            // ==========================================

            Product existing =
                    productDAO.getProductById(id);

            if (existing == null) {

                res.sendRedirect(
                        req.getContextPath()
                                + "/admin/AdminProduct?error=notfound"
                );
                return;
            }


            // Keep existing image if no new image uploaded
            String imageUrl =
                    existing.getImageUrl();


            // ==========================================
            // 6. Handle new image
            // ==========================================

            Part imagePart =
                    req.getPart("image");

            if (imagePart != null
                    && imagePart.getSize() > 0) {

                // Delete old image if it exists
                if (imageUrl != null
                        && !imageUrl.trim().isEmpty()) {

                    File oldImage =
                            new File(
                                    getServletContext()
                                            .getRealPath("/")
                                            + imageUrl
                            );

                    if (oldImage.exists()) {
                        oldImage.delete();
                    }
                }


                // Generate new filename
                String fileName =
                        System.currentTimeMillis()
                                + "_"
                                + imagePart.getSubmittedFileName();


                String uploadPath =
                        getServletContext()
                                .getRealPath("/")
                                + "assets/images";


                File uploadDir =
                        new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }


                // Save new image
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
            // 7. Create updated Product object
            // ==========================================

            Product product =
                    new Product();

            product.setId(id);
            product.setName(name.trim());
            product.setPrice(price);
            product.setCategoryId(categoryId);
            product.setQuantity(quantity);
            product.setDescription(description.trim());
            product.setImageUrl(imageUrl);

            // Preserve existing active/inactive status
            product.setActive(
                    existing.isActive()
            );


            // ==========================================
            // 8. Update database
            // ==========================================

            boolean updated =
                    productDAO.updateProduct(product);


            // ==========================================
            // 9. Redirect
            // ==========================================

            if (updated) {

                res.sendRedirect(
                        req.getContextPath()
                                + "/admin/AdminProduct"
                );

            } else {

                res.sendRedirect(
                        req.getContextPath()
                                + "/admin/AdminProduct?error=failed"
                );
            }

        } catch (NumberFormatException e) {

            e.printStackTrace();

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