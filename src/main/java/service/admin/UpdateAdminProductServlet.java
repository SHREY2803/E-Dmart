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

        String contextPath = req.getContextPath();

        try {

            // ==========================================
            // 2. Read form fields
            // ==========================================

            String idParameter = req.getParameter("id");
            String name = req.getParameter("name");
            String priceParameter = req.getParameter("price");
            String categoryParameter = req.getParameter("category");
            String quantityParameter = req.getParameter("quantity");
            String description = req.getParameter("description");

            // Basic null validation

            if (idParameter == null
                    || name == null
                    || priceParameter == null
                    || categoryParameter == null
                    || quantityParameter == null
                    || description == null) {

                res.sendRedirect(
                        contextPath
                                + "/admin/AdminProduct?error=invalid"
                );
                return;
            }


            // ==========================================
            // 3. Convert values
            // ==========================================

            int id =
                    Integer.parseInt(idParameter);

            double price =
                    Double.parseDouble(priceParameter);

            int categoryId =
                    Integer.parseInt(categoryParameter);

            int quantity =
                    Integer.parseInt(quantityParameter);


            // ==========================================
            // 4. Validate values
            // ==========================================

            if (name.trim().isEmpty()
                    || description.trim().isEmpty()
                    || price < 0
                    || quantity < 0) {

                res.sendRedirect(
                        contextPath
                                + "/admin/AdminProduct?error=invalid"
                );
                return;
            }


            // ==========================================
            // 5. Check product exists
            // ==========================================

            Product existing =
                    productDAO.getProductById(id);

            if (existing == null) {

                res.sendRedirect(
                        contextPath
                                + "/admin/AdminProduct?error=notfound"
                );
                return;
            }


            // ==========================================
            // 6. Check category exists
            // ==========================================

            Category category =
                    categoryDAO.getCategoryById(categoryId);

            if (category == null) {

                res.sendRedirect(
                        contextPath
                                + "/admin/AdminProduct?error=invalid"
                );
                return;
            }


            // ==========================================
            // 7. Keep existing image by default
            // ==========================================

            String imageUrl =
                    existing.getImageUrl();

            String oldImageUrl =
                    existing.getImageUrl();

            String newImagePath = null;


            // ==========================================
            // 8. Check whether new image was uploaded
            // ==========================================

            Part imagePart =
                    req.getPart("image");

            if (imagePart != null
                    && imagePart.getSize() > 0) {

                String originalFileName =
                        imagePart.getSubmittedFileName();

                if (originalFileName != null
                        && !originalFileName.trim().isEmpty()) {

                    // Generate unique filename

                    String fileName =
                            System.currentTimeMillis()
                                    + "_"
                                    + new File(originalFileName)
                                    .getName();


                    // Upload directory

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

                    newImagePath =
                            uploadPath
                                    + File.separator
                                    + fileName;

                    imagePart.write(newImagePath);


                    // Relative path stored in DB

                    imageUrl =
                            "assets/images/"
                                    + fileName;
                }
            }


            // ==========================================
            // 9. Create updated Product
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

            // Preserve current active status

            product.setActive(
                    existing.isActive()
            );


            // ==========================================
            // 10. Update database
            // ==========================================

            boolean updated =
                    productDAO.updateProduct(product);


            // ==========================================
            // 11. Handle result
            // ==========================================

            if (updated) {

                // If a new image was successfully saved
                // and DB update succeeded, delete old image.

                if (newImagePath != null
                        && oldImageUrl != null
                        && !oldImageUrl.trim().isEmpty()) {

                    File oldImage =
                            new File(
                                    getServletContext()
                                            .getRealPath("/")
                                            + oldImageUrl
                            );

                    if (oldImage.exists()) {
                        oldImage.delete();
                    }
                }


                res.sendRedirect(
                        contextPath
                                + "/admin/AdminProduct"
                );

            } else {

                // DB update failed.
                // Remove newly uploaded image so that
                // we don't leave an unnecessary file.

                if (newImagePath != null) {

                    File newImage =
                            new File(newImagePath);

                    if (newImage.exists()) {
                        newImage.delete();
                    }
                }

                res.sendRedirect(
                        contextPath
                                + "/admin/AdminProduct?error=failed"
                );
            }

        } catch (NumberFormatException e) {

            res.sendRedirect(
                    contextPath
                            + "/admin/AdminProduct?error=invalid"
            );

        } catch (Exception e) {

            e.printStackTrace();

            res.sendRedirect(
                    contextPath
                            + "/admin/AdminProduct?error=true"
            );
        }
    }
}