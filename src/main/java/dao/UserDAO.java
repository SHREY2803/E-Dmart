package dao;

import java.util.List;

import model.User;

public interface UserDAO {

    // Register new user
    boolean registerUser(User user);

    // Login user
    User login(String email, String password);

    // Check if email already exists
    boolean isEmailExists(String email);
    
    User getUserById(int userId);

    boolean updateProfile(int userId, String name, String email);
    
    List<User> getAllUsers();
}
