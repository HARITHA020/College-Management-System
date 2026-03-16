package controller;

import java.util.Scanner;
import service.FacultyService;
import service.StudentService;
import service.AdminService;

public class FacultyController {

    private FacultyService facultyService = new FacultyService();
    private StudentService studentService = new StudentService();
    private AdminService adminService = new AdminService();
    private Scanner scanner = new Scanner(System.in);

    public void showMenu() {

        while (true) {

            System.out.println("\n--- Faculty Menu ---");
            System.out.println("1. View Students");
            System.out.println("2. Mark Attendance");
            System.out.println("3. View Marks");
            System.out.println("4. View Timetable");
            System.out.println("5. Logout");

            int choice = scanner.nextInt();

            switch (choice) {

            case 1:
                studentService.viewStudents();
                break;

            case 2:
                System.out.println("Enter Student ID:");
                int studentId = scanner.nextInt();

                System.out.println("Present? (true/false)");
                boolean present = scanner.nextBoolean();

                facultyService.markAttendance(studentId, present);
                break;

            case 3:
                facultyService.viewMarks();
                break;

            case 4:
                adminService.viewTimetable();
                break;

            case 5:
                System.out.println("Logging out...");
                return;

            default:
                System.out.println("Invalid Choice");
            }
        }
    }
}