package controller;

import service.AuthService;

public class LoginController {
	AuthService authservice=new AuthService();

	public void login(String email, String password) {
		String role=authservice.checkrole(email,password);
		
		 if(role.equals("ADMIN")) {
	            System.out.println("Admin Login Successful");
	            AdminContoller adminController = new AdminContoller();
	            adminController.adminDashboard();
	        }

	        else if(role.equals("FACULTY")) {
	            System.out.println("Faculty Login Successful");
	            FacultyController facultyController = new FacultyController();
	            facultyController.facultyDashboard();
	        }

	        else if(role.equals("STUDENT")) {
	            System.out.println("Student Login Successful");
	            StudentController studentController = new StudentController();
	            studentController.studentDashboard();
	        }

	        else {
	            System.out.println("Invalid Login Credentials");
	        }
		
	}
	
}
