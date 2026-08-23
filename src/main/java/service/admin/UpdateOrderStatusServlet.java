package service.admin;

import java.io.IOException;

import dao.OrderDAO;
import daoimpl.OrderDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/update-order-status")
public class UpdateOrderStatusServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAOImpl();

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse res)
            throws ServletException, IOException {

        // ==========================================
        // 1. Check admin session
        // ==========================================

        HttpSession session = req.getSession(false);

        if (session == null
                || !"ADMIN".equals(session.getAttribute("role"))) {

            res.sendRedirect(
                    req.getContextPath()
                            + "/access-denied.jsp"
            );

            return;
        }


        // ==========================================
        // 2. Get request parameters
        // ==========================================

        String orderIdParam =
                req.getParameter("orderId");

        String status =
                req.getParameter("status");


        // ==========================================
        // 3. Validate parameters
        // ==========================================

        if (orderIdParam == null
                || status == null
                || status.trim().isEmpty()) {

            res.sendRedirect(
                    req.getContextPath()
                            + "/admin/ViewAdminOrders"
            );

            return;
        }


        int orderId;

        try {

            orderId =
                    Integer.parseInt(orderIdParam);

        } catch (NumberFormatException e) {

            res.sendRedirect(
                    req.getContextPath()
                            + "/admin/ViewAdminOrders"
            );

            return;
        }


        // ==========================================
        // 4. Update order status
        // ==========================================

        orderDAO.updateOrderStatus(
                orderId,
                status
        );


        // ==========================================
        // 5. Go back to orders page
        // ==========================================

        res.sendRedirect(
                req.getContextPath()
                        + "/admin/ViewAdminOrders"
        );
    }
}