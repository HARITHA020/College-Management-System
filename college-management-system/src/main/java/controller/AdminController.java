package controller;

import java.util.Scanner;
import service.AdminService;
import service.StudentService;
import service.FacultyService;

public class AdminController {

    AdminService adminService = new AdminService();
    StudentService studentService=new StudentService();
    FacultyService facultyService=new FacultyService();
    Scanner input = new Scanner(System.in);

    public void showMenu() {
    	
    	System.out.println("1. Manage Admin");
        System.out.println("2. Manage Student");
        System.out.println("3. Manage Faculty");
        System.out.println("4. Manage Course");
        System.out.println("5. Manage Timetable");

        System.out.println("Enter your choice:");
        int choice = input.nextInt();

        switch(choice) {

        case 1:
            manageAdmin();
            break;

        case 2:
            manageStudent();
            break;

        case 3:
            manageFaculty();
            break;

        case 4:
            manageCourse();
            break;
        case 5:
        	manageTimetable();

        default:
            System.out.println("Invalid Choice");
        }
    }

    private void manageAdmin() {

        System.out.println("1. Add Admin");
        System.out.println("2. Update Admin");
        System.out.println("3. Delete Admin");

        int choice = input.nextInt();
        input.nextLine();

        if(choice == 1) {

            System.out.println("Enter Admin ID:");
            int id = input.nextInt();
            input.nextLine();

            System.out.println("Enter Admin Name:");
            String name = input.nextLine();

            System.out.println("Enter Password:");
            String password = input.nextLine();

            adminService.addAdmin(id, name, password);
        }

        else if(choice == 2) {

            System.out.println("Enter Admin ID:");
            int id = input.nextInt();
            input.nextLine();

            System.out.println("Enter New Name:");
            String name = input.nextLine();

            System.out.println("Enter New Password:");
            String password = input.nextLine();

            adminService.updateAdmin(id, name, password);
        }

        else if(choice == 3) {

            System.out.println("Enter Admin ID:");
            int id = input.nextInt();

            adminService.deleteAdmin(id);
        }
    }

	public void manageStudent() {

        System.out.println("1. Add Student");
        System.out.println("2. Update Student");
        System.out.println("3. Delete Student");

        int choice = input.nextInt();
        input.nextLine();

        if(choice == 1) {

            System.out.println("Enter Student ID:");
            int id = input.nextInt();
            input.nextLine();

            System.out.println("Enter Student Name:");
            String name = input.nextLine();

            System.out.println("Enter Department:");
            String dept = input.nextLine();

            studentService.addStudent(id, name, dept);
        }

        else if(choice == 2) {

            System.out.println("Enter Student ID:");
            int id = input.nextInt();
            input.nextLine();

            System.out.println("Enter New Name:");
            String name = input.nextLine();

            System.out.println("Enter New Department:");
            String dept = input.nextLine();

            studentService.updateStudent(id, name, dept);
        }

        else if(choice == 3) {

            System.out.println("Enter Student ID:");
            int id = input.nextInt();

            studentService.deleteStudent(id);
        }
    }

    public void manageFaculty() {

        System.out.println("1. Add Faculty");
        System.out.println("2. Update Faculty");
        System.out.println("3. Delete Faculty");

        int choice = input.nextInt();
        input.nextLine();

        if(choice == 1) {

            System.out.println("Enter Faculty ID:");
            int id = input.nextInt();
            input.nextLine();

            System.out.println("Enter Faculty Name:");
            String name = input.nextLine();

            System.out.println("Enter Faculty Department:");
            String dept = input.nextLine();

            facultyService.addFaculty(id, name, dept);
        }

        else if(choice == 2) {

            System.out.println("Enter Faculty ID:");
            int id = input.nextInt();
            input.nextLine();

            System.out.println("Enter New Name:");
            String name = input.nextLine();

            System.out.println("Enter New Department:");
            String dept = input.nextLine();

            facultyService.updateFaculty(id, name, dept);
        }

        else if(choice == 3) {

            System.out.println("Enter Faculty ID:");
            int id = input.nextInt();

            facultyService.deleteFaculty(id);
        }
    }

    public void manageCourse() {

        System.out.println("1. Add Course");
        System.out.println("2. Assign Course");

        int choice = input.nextInt();
        input.nextLine();

        if(choice == 1) {

            System.out.println("Enter Course ID:");
            int courseId = input.nextInt();
            input.nextLine();

            System.out.println("Enter Course Name:");
            String courseName = input.nextLine();

            adminService.addCourse(courseId, courseName);
        }

        else if(choice == 2) {

            System.out.println("Enter Course ID:");
            int courseId = input.nextInt();

            System.out.println("Enter Faculty ID:");
            int facultyId = input.nextInt();

            adminService.assignCourse(courseId, facultyId);
        }
    }

    public void manageTimetable() {

        System.out.println("Enter Timetable ID:");
        int id = input.nextInt();
        input.nextLine();

        System.out.println("Enter Day:");
        String day = input.nextLine();

        System.out.println("Enter Time:");
        String time = input.nextLine();

        System.out.println("Enter Room Number:");
        String room = input.nextLine();

        System.out.println("Enter Course ID:");
        int courseId = input.nextInt();

        adminService.addTimetable(id, day, time, room, courseId);
    }
}