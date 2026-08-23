package service.admin;

import java.io.IOException;
import java.util.List;

import dao.CategoryDAO;
import dao.ProductDAO;
import daoimpl.CategoryDAOImpl;
import daoimpl.ProductDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Category;
import model.Product;

@WebServlet("/admin/EditAdminProduct")
public class EditAdminProductServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAOImpl();
    private CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    protected void doGet(
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
            // 2. Get product ID
            // ==========================================

            String idParameter = req.getParameter("id");

            if (idParameter == null || idParameter.trim().isEmpty()) {
                res.sendRedirect(
                        req.getContextPath()
                                + "/admin/AdminProduct?error=invalid"
                );
                return;
            }

            int productId = Integer.parseInt(idParameter);


            // ==========================================
            // 3. Get product from database
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
            // 4. Get categories from database
            // ==========================================

            List<Category> categories =
                    categoryDAO.getAllCategories();


            // ==========================================
            // 5. Send data to JSP
            // ==========================================

            req.setAttribute("product", product);
            req.setAttribute("categories", categories);


            // ==========================================
            // 6. Open edit page
            // ==========================================

            req.getRequestDispatcher(
                    "/admin/edit-product.jsp"
            ).forward(req, res);

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