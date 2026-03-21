package controller;

import java.util.Scanner;

import service.LibraryService;
import service.StudentService;

public class StudentController {

    private StudentService studentService;
    private Scanner scanner;

    public StudentController() {
        this.studentService = new StudentService();
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        int studentId = 101; // This should ideally come from login session
        do {
            System.out.println("\n========== Student Menu ==========");
            System.out.println("1. View Courses");
            System.out.println("2. View Timetable");
            System.out.println("3. View Marks");
            System.out.println("4. View Attendance");
            System.out.println("5. View Notifications");
            System.out.println("6. Search Book");
            System.out.println("7. Borrow Book");
            System.out.println("8. Return Book");
            System.out.println("9. View Materials");
            System.out.println("10. View Assignments");
            System.out.println("0. Logout");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewCourses(studentId);
                    break;
                case 2:
                    viewTimetable(studentId);
                    break;
                case 3:
                    viewMarks(studentId);
                    break;
                case 4:
                    viewAttendance(studentId);
                    break;
                case 5:
                    viewNotifications(studentId);
                    break;
                case 6:
                    searchBook();
                    break;
                case 7:
                    borrowBook(studentId);
                    break;
                case 8:
                    returnBook();
                    break;
                case 9:
                    viewMaterials(studentId);
                    break;
                case 10:
                    viewAssignments(studentId);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 0);
    }

    // -------------------- Student Functionalities --------------------

    public void viewCourses(int studentId) { 
        studentService.viewMyCourses(studentId); 
    }

    public void viewTimetable(int studentId) { 
        studentService.viewTimetable(studentId); 
    }

    public void viewMarks(int studentId) { 
        studentService.viewMarks(studentId); 
    }

    public void viewAttendance(int studentId) { 
        studentService.viewAttendance(studentId); 
    }

    public void viewNotifications(int studentId) { 
        studentService.viewNotifications(studentId); 
    }

    public void searchBook() {
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();
        studentService.searchBook(keyword);
    }

    public void borrowBook(int studentId) {
        System.out.print("Enter Book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        studentService.borrowBook(studentId, bookId);
    }

    public void returnBook() {
        System.out.print("Enter Record ID: ");
        int recordId = scanner.nextInt();
        scanner.nextLine();

        studentService.returnBook(recordId);
    }

    // -------------------- New: Materials & Assignments --------------------

    public void viewMaterials(int studentId) {
        System.out.print("Enter Course ID to view materials: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();

        // Pass studentId for validation (student must be enrolled)
        studentService.viewMaterials(courseId, studentId, "STUDENT");
    }

    public void viewAssignments(int studentId) {
        System.out.print("Enter Course ID to view assignments: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();

        // Pass studentId for validation (student must be enrolled)
        studentService.viewAssignments(courseId, studentId, "STUDENT");
    }
}