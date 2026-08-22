package service;

import java.io.IOException;

import business.RegisterValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private RegisterValidator registerValidator;

	@Override
	public void init() {
		registerValidator = new RegisterValidator();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Read form data
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");

		// Call business logic
		boolean status = registerValidator.validateAndRegister(name, email, password);

		if (status) {
			// registration success → go to login
			res.sendRedirect("login.jsp?msg=registered");
		} else {
			res.sendRedirect("register.jsp?error=failed");
		}
	}
}
