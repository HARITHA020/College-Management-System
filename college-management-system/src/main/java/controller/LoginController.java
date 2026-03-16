package controller;

import service.AuthService;

public class LoginController {

    private AuthService authService = new AuthService();

    public void login(String email, String password) {

        String role = authService.checkRole(email, password);

        if (role == null) {
            System.out.println("Invalid Login Credentials");
            return;
        }

        if ("ADMIN".equals(role)) {
            System.out.println("Admin Login Successful");
            new AdminController().showMenu();
        } 
        else if ("FACULTY".equals(role)) {
            System.out.println("Faculty Login Successful");
            new FacultyController().showMenu();
        } 
        else if ("STUDENT".equals(role)) {
            System.out.println("Student Login Successful");
            new StudentController().showMenu();
        }
    }
}