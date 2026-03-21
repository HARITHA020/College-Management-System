package service;

import dao.UserDAO;
import model.User;

public class AuthService {

    private UserDAO userDAO = new UserDAO();

    public User login(String email, String password) {

        if (email == null || email.trim().isEmpty()) {
            System.out.println("Email cannot be empty");
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            System.out.println("Password cannot be empty");
            return null;
        }

        if (!email.contains("@")) {
            System.out.println("Invalid email format");
            return null;
        }

        User user = userDAO.checkUser(email, password);

        if (user == null) {
            System.out.println("Invalid email or password");
            return null;
        }

        return user;
    }
}