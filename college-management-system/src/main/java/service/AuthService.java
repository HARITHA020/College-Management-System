package service;

import dao.UserDAO;

public class AuthService {

    private UserDAO userDAO = new UserDAO();

    public String checkRole(String email, String password) {

        // 🔹 1. Null or empty check
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Email cannot be empty");
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            System.out.println("Password cannot be empty");
            return null;
        }

        // 🔹 2. Basic email format check
        if (!email.contains("@")) {
            System.out.println("Invalid email format");
            return null;
        }

        // 🔹 3. Check user in DAO
        String role = userDAO.checkUser(email, password);

        // 🔹 4. Invalid credentials check
        if (role == null) {
            System.out.println("Invalid email or password");
            return null;
        }

        // 🔹 5. Success
        return role;
    }
}