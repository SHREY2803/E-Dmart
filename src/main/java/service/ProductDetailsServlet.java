package service;

import java.io.IOException;

import business.ProductManager;
import dao.CategoryDAO;
import daoimpl.CategoryDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Product;
import model.Category;

@WebServlet("/product-details")
public class ProductDetailsServlet extends HttpServlet {

    private ProductManager productManager;
    private CategoryDAO categoryDAO;

    @Override
    public void init() {

        productManager = new ProductManager();
        categoryDAO = new CategoryDAOImpl();
    }

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse res)
            throws ServletException, IOException {

        String idParam =
                req.getParameter("id");

        // No product ID
        if (idParam == null ||
                idParam.trim().isEmpty()) {

            res.sendRedirect(
                    req.getContextPath() + "/products"
            );

            return;
        }

        try {

            int id =
                    Integer.parseInt(idParam);

            // Get product
            Product product =
                    productManager.getProductById(id);

            // Product doesn't exist
            if (product == null) {

                res.sendRedirect(
                        req.getContextPath()
                                + "/noproduct.jsp"
                );

                return;
            }

            // Get category dynamically
            Category category =
                    categoryDAO.getCategoryById(
                            product.getCategoryId()
                    );

            // Send data to JSP
            req.setAttribute(
                    "product",
                    product
            );

            req.setAttribute(
                    "category",
                    category
            );

            // Open product details page
            req.getRequestDispatcher(
                    "product-details.jsp"
            ).forward(req, res);

        } catch (NumberFormatException e) {

            res.sendRedirect(
                    req.getContextPath()
                            + "/products"
            );
        }
    }
}