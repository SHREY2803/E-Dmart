package service;

import java.io.IOException;

import business.CartManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	private CartManager cartManager = new CartManager();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(false);
		
		//Check login
		if (session == null || session.getAttribute("userId") == null) {
            res.sendRedirect("login.jsp");
            return;
        }
		//Get logged-in userId
        int userId = (int) session.getAttribute("userId");

        //Load cart from DB EVERY TIME
        
        req.setAttribute("cartItems", cartManager.getUserCart(userId));
        req.getRequestDispatcher("cart.jsp").forward(req, res);
	}
}
