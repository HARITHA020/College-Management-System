package controller;

import java.util.Scanner;
import service.AdminService;
import service.StudentService;
import service.FacultyService;

public class AdminController {

    AdminService adminService = new AdminService();
    StudentService studentService = new StudentService();
    FacultyService facultyService = new FacultyService();

    Scanner input = new Scanner(System.in);

    public void showMenu() {

        int choice = 0;

        while (choice != 7) {

            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Manage Admin");
            System.out.println("2. Manage Student");
            System.out.println("3. Manage Faculty");
            System.out.println("4. Manage Course");
            System.out.println("5. Manage Timetable");
            System.out.println("6. View Details");
            System.out.println("7. Exit");

            System.out.println("Enter your choice:");
            choice = input.nextInt();

            switch (choice) {

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
                break;

            case 6:
                viewDetails();
                break;

            case 7:
                System.out.println("Exiting Program...");
                break;

            default:
                System.out.println("Invalid Choice");
            }
        }
    }

    private void manageAdmin() {

        int choice = 0;

        while (choice != 4) {

            System.out.println("\n--- Manage Admin ---");
            System.out.println("1. Add Admin");
            System.out.println("2. Update Admin");
            System.out.println("3. Delete Admin");
            System.out.println("4. Exit");

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {

                System.out.println("Enter Admin ID:");
                int id = input.nextInt();
                input.nextLine();

                System.out.println("Enter Admin Name:");
                String name = input.nextLine();

                System.out.println("Enter Password:");
                String password = input.nextLine();

                adminService.addAdmin(id, name, password);
            }

            else if (choice == 2) {

                System.out.println("Enter Admin ID:");
                int id = input.nextInt();
                input.nextLine();

                System.out.println("Enter New Name:");
                String name = input.nextLine();

                System.out.println("Enter New Password:");
                String password = input.nextLine();

                adminService.updateAdmin(id, name, password);
            }

            else if (choice == 3) {

                System.out.println("Enter Admin ID:");
                int id = input.nextInt();

                adminService.deleteAdmin(id);
            }
        }
    }

    public void manageStudent() {

        int choice = 0;

        while (choice != 4) {

            System.out.println("\n--- Manage Student ---");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Exit");

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {

                System.out.println("Enter Student ID:");
                int id = input.nextInt();
                input.nextLine();

                System.out.println("Enter Student Name:");
                String name = input.nextLine();

                System.out.println("Enter Department:");
                String dept = input.nextLine();

                studentService.addStudent(id, name, dept);
            }

            else if (choice == 2) {

                System.out.println("Enter Student ID:");
                int id = input.nextInt();
                input.nextLine();

                System.out.println("Enter New Name:");
                String name = input.nextLine();

                System.out.println("Enter New Department:");
                String dept = input.nextLine();

                studentService.updateStudent(id, name, dept);
            }

            else if (choice == 3) {

                System.out.println("Enter Student ID:");
                int id = input.nextInt();

                studentService.deleteStudent(id);
            }
        }
    }

    public void manageFaculty() {

        int choice = 0;

        while (choice != 4) {

            System.out.println("\n--- Manage Faculty ---");
            System.out.println("1. Add Faculty");
            System.out.println("2. Update Faculty");
            System.out.println("3. Delete Faculty");
            System.out.println("4. Exit");

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {

                System.out.println("Enter Faculty ID:");
                int id = input.nextInt();
                input.nextLine();

                System.out.println("Enter Faculty Name:");
                String name = input.nextLine();

                System.out.println("Enter Faculty Department:");
                String dept = input.nextLine();

                facultyService.addFaculty(id, name, dept);
            }

            else if (choice == 2) {

                System.out.println("Enter Faculty ID:");
                int id = input.nextInt();
                input.nextLine();

                System.out.println("Enter New Name:");
                String name = input.nextLine();

                System.out.println("Enter New Department:");
                String dept = input.nextLine();

                facultyService.updateFaculty(id, name, dept);
            }

            else if (choice == 3) {

                System.out.println("Enter Faculty ID:");
                int id = input.nextInt();

                facultyService.deleteFaculty(id);
            }
        }
    }

    public void manageCourse() {

        int choice = 0;

        while (choice != 3) {

            System.out.println("\n--- Manage Course ---");
            System.out.println("1. Add Course");
            System.out.println("2. Assign Course");
            System.out.println("3. Exit");

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {

                System.out.println("Enter Course ID:");
                int courseId = input.nextInt();
                input.nextLine();

                System.out.println("Enter Course Name:");
                String courseName = input.nextLine();

                adminService.addCourse(courseId, courseName);
            }

            else if (choice == 2) {

                System.out.println("Enter Course ID:");
                int courseId = input.nextInt();

                System.out.println("Enter Faculty ID:");
                int facultyId = input.nextInt();

                adminService.assignCourse(courseId, facultyId);
            }
        }
    }

    public void manageTimetable() {

        System.out.println("\n--- Add Timetable ---");

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

    public void viewDetails() {

        int choice = 0;

        while (choice != 6) {

            System.out.println("\n--- View Details ---");
            System.out.println("1. View Students");
            System.out.println("2. View Faculties");
            System.out.println("3. View Admins");
            System.out.println("4. View Courses");
            System.out.println("5. View Timetable");
            System.out.println("6. Exit");

            choice = input.nextInt();

            if (choice == 1) {
                studentService.viewStudents();
            }

            else if (choice == 2) {
                facultyService.viewFacultys();
            }

            else if (choice == 3) {
                adminService.viewAdmins();
            }

            else if (choice == 4) {
                adminService.viewCourse();
            }

            else if (choice == 5) {
                adminService.viewTimetable();
            }
        }
    }
}