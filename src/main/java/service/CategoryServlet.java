package service;

import dao.CategoryDAO;
import daoimpl.CategoryDAOImpl;
import model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/categories")
public class CategoryServlet extends HttpServlet {

    private CategoryDAO categoryDAO;

    @Override
    public void init() {
        categoryDAO = new CategoryDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Check login + ADMIN role
        if (!isAdmin(request, response)) {
            return;
        }

        // Get all categories
        List<Category> categories = categoryDAO.getAllCategories();

        request.setAttribute("categories", categories);

        request.getRequestDispatcher(
                "/admin/categories.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Check login + ADMIN role
        if (!isAdmin(request, response)) {
            return;
        }

        // Read form data
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        // Basic validation
        if (name == null || name.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/categories?error=invalid"
            );

            return;
        }

        // Create Category object
        Category category = new Category();

        category.setName(name.trim());
        category.setDescription(
                description == null ? "" : description.trim()
        );

        // Save category
        boolean added = categoryDAO.addCategory(category);

        if (added) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/categories?success=added"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/categories?error=failed"
            );
        }
    }

    // Checks whether the current user is an ADMIN
    private boolean isAdmin(HttpServletRequest request,
                            HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        // Not logged in
        if (session == null ||
                session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );

            return false;
        }

        // Check role
        String role = (String) session.getAttribute("role");

        if (!"ADMIN".equalsIgnoreCase(role)) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Access Denied"
            );

            return false;
        }

        return true;
    }
}