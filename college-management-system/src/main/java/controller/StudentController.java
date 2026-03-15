package controller;

import java.util.Scanner;
import service.StudentService;

public class StudentController {
	
	private StudentService studentService;
	private Scanner scanner;

	public StudentController() {
        this.studentService = new StudentService();
        this.scanner        = new Scanner(System.in);
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
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> viewCourses();
                case 2 -> viewTimetable();
                case 3 -> viewMarks();
                case 4 -> viewAttendance();
                case 5 -> viewNotifications();
                case 6 -> searchBook();
                case 7 -> borrowBook();
                case 8 -> returnBook();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }
	
	public void viewCourses() {
        studentService.viewCourses();
    }

    public void viewTimetable() {
        studentService.viewTimetable();
    }

    public void viewMarks() {
        studentService.viewMarks();
    }

    public void viewAttendance() {
        studentService.viewAttendance();
    }

    public void viewNotifications() {
        studentService.viewNotifications();
    }

    public void searchBook() {
        System.out.print("Enter book title or author to search: ");
        String keyword = scanner.nextLine();
        studentService.searchBook(keyword);
    }

    public void borrowBook() {
        System.out.print("Enter your Student ID: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter Book ID to borrow: ");
        int bookId = scanner.nextInt();
        studentService.borrowBook(studentId, bookId);
    }

    public void returnBook() {
        System.out.print("Enter Borrow Record ID to return: ");
        int recordId = scanner.nextInt();
        studentService.returnBook(recordId);
    }

	
}
