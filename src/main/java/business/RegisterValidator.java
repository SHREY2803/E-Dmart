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
		if (name == null || email == null || password == null || name.trim().isEmpty() || email.trim().isEmpty()
				|| password.trim().isEmpty()) {
			return false;
		}

		// Rule 2: Email should not already exist
		if (userDAO.isEmailExists(email)) {
			return false;
		}
		
		// Rule 3: Create user object
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("CUSTOMER"); // default role

        // Rule 4: Save user in DB
        return userDAO.registerUser(user);

	}
}
