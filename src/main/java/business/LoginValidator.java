package business;

import dao.UserDAO;
import daoimpl.UserDAOImpl;
import model.User;

public class LoginValidator {

    private UserDAO userDAO;

    // Constructor
    public LoginValidator() {
        this.userDAO = new UserDAOImpl();
    }

    // This method validates login and returns User if valid
    public User validateLogin(String email, String password) {

        // Rule 1: Email or password should not be empty
        if (email == null || password == null ||
            email.trim().isEmpty() || password.trim().isEmpty()) {
            return null;
        }

        // Rule 2: Check user credentials using DAO
        User user = userDAO.login(email, password);

        // Rule 3: If user exists → valid login
        if (user != null) {
            return user;
        }

        // Invalid login
        return null;
    }
}
