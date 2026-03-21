package controller;

import service.AuthService;
import model.User;
import model.Administrator;
import model.Student;
import model.Faculty;
import dao.AdminDAO;
import dao.StudentDAO;
import dao.FacultyDAO;

public class LoginController {

    private AuthService authService = new AuthService();
    private AdminDAO adminDAO = new AdminDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private FacultyDAO facultyDAO = new FacultyDAO();

    public void login(String email, String password) {

        User user = authService.login(email, password);

        if (user == null) {
            System.out.println("Login Failed");
            return;
        }

        int userId = user.getUserId();
        String role = user.getRole();

        if ("ADMIN".equals(role)) {
            Administrator admin = adminDAO.getAdminByUserId(userId);
            if(admin != null) {
                System.out.println("Admin Login Successful: " + admin.getName());
                new AdminController().showMenu();
            }
        } 
        else if ("FACULTY".equals(role)) {
            Faculty faculty = facultyDAO.getFacultyByUserId(userId);
            if(faculty != null) {
                System.out.println("Faculty Login Successful: " + faculty.getName());
                new FacultyController().showMenu();
            }
        } 
        else if ("STUDENT".equals(role)) {
            Student student = studentDAO.getStudentByUserId(userId);
            if(student != null) {
                System.out.println("Student Login Successful: " + student.getName());
                new StudentController().showMenu();
            }
        }
    }
}