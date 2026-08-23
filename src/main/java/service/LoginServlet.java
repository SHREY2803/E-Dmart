package service;

import business.LoginValidator;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private LoginValidator loginValidator;

    @Override
    public void init() {
        loginValidator = new LoginValidator();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Read form data
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 2. Validate login using business layer
        User user = loginValidator.validateLogin(email, password);

        // 3. If login is successful
        if (user != null) {

            // Create session
            HttpSession session = request.getSession();

            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("role", user.getRole());

            // 4. Role-based redirection
            String role = user.getRole();

            if ("ADMIN".equalsIgnoreCase(role)) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/admin/admin-dashboard.jsp"
                );

            } else if ("STAFF".equalsIgnoreCase(role)) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/staff/staff-dashboard.jsp"
                );

            } else if ("CUSTOMER".equalsIgnoreCase(role)) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/customer/dashboard.jsp"
                );

            } else {

                // Unknown/invalid role
                session.invalidate();

                response.sendRedirect(
                        request.getContextPath()
                                + "/error.jsp"
                );
            }

        } else {

            // 5. Invalid login
            response.sendRedirect(
                    request.getContextPath()
                            + "/error.jsp"
            );
        }
    }
}