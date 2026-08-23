package business;

import dao.UserDAO;
import daoimpl.UserDAOImpl;
import model.User;

public class RegisterValidator {
	private UserDAO userDAO;

	public RegisterValidator() {
		this.userDAO = new UserDAOImpl();
	}

	public boolean validateAndRegister(String name, String email, String password) {

		// Rule 1: No empty values
		if (name == null || email == null || password == null ||
				name.trim().isEmpty() ||
				email.trim().isEmpty() ||
				password.trim().isEmpty()) {
			return false;
		}

		// Rule 2: Basic email validation
		if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			return false;
		}

		// Rule 3: Basic password validation
		if (password.length() < 6) {
			return false;
		}

		// Rule 4: Email should not already exist
		if (userDAO.isEmailExists(email)) {
			return false;
		}

		// Rule 5: Create user
		User user = new User();

		user.setName(name.trim());
		user.setEmail(email.trim());
		user.setPassword(password);

		// IMPORTANT:
		// Public registration can only create CUSTOMER accounts.
		user.setRole("CUSTOMER");

		// Rule 6: Save user
		return userDAO.registerUser(user);
	}

}
