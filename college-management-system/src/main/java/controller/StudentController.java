package controller;

import java.util.Scanner;
import service.StudentService;

public class StudentController {

    private StudentService studentService;
    private Scanner scanner;
    private int studentId; // logged-in student ID

    // ✅ Constructor to pass logged-in student ID
    public StudentController(int userId) {
        this.studentService = new StudentService();
        this.scanner = new Scanner(System.in);

        // ✅ Convert userId → studentId
        this.studentId = studentService.getStudentIdByUserId(userId);
    }

    public void showMenu() {
        int choice;
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
                    studentService.viewMyCourses(studentId);
                    break;
                case 2:
                    studentService.viewTimetable(studentId);
                    break;
                case 3:
                    studentService.viewMarks(studentId);
                    break;
                case 4:
                    studentService.viewAttendance(studentId);
                    break;
                case 5:
                    studentService.viewNotifications(studentId);
                    break;
                case 6:
                    searchBook();
                    break;
                case 7:
                    borrowBook();
                    break;
                case 8:
                    returnBook();
                    break;
                case 9:
                    viewMaterials();
                    break;
                case 10:
                    viewAssignments();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 0);
    }

    // -------------------- Book Functions --------------------
    private void searchBook() {
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();
        studentService.searchBook(keyword);
    }

    private void borrowBook() {
        System.out.print("Enter Book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        studentService.borrowBook(studentId, bookId);
    }

    private void returnBook() {
        System.out.print("Enter Book ID to return: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        studentService.returnBook(bookId, studentId); // pass studentId here
    }

    // -------------------- Materials & Assignments --------------------
    private void viewMaterials() {
        System.out.print("Enter Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        studentService.viewMaterials(courseId, studentId, "STUDENT");
    }

    private void viewAssignments() {
        System.out.print("Enter Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        studentService.viewAssignments(courseId, studentId, "STUDENT");
    }
}