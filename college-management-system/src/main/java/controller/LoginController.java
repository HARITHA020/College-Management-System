/*
 * Author :Haritha
 * 
 */


package controller;

import dao.UserDAO;
import model.LoginResponse;

public class LoginController {

    private UserDAO userDAO = new UserDAO();

    // returns LoginResponse (role + userId)
    public LoginResponse checkRole(String email, String password) {

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

        LoginResponse response = userDAO.checkUser(email, password);

        if (response == null) {
            System.out.println("Invalid email or password");
            return null;
        }

        return response;
    }

    // Main login method called from MainApp
    public boolean login(String email, String password) {
        LoginResponse response = checkRole(email, password);
        if (response == null) return false;

        String role = response.getRole();
        int userId = response.getUserId();

        switch (role) {
            case "ADMIN":
                System.out.println("Admin Login Successful");
                new AdminController(userId).showMenu();
                break;
            case "FACULTY":
                System.out.println("Faculty Login Successful");
                new FacultyController(userId).showMenu();
                break;
            case "STUDENT":
                System.out.println("Student Login Successful");
                new StudentController(userId).showMenu();
                break;
            default:
                System.out.println("Unknown Role");
                return false;
        }
        return true;
        
    }
}