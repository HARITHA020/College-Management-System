package controller;

import java.util.Scanner;

import service.LibraryService;
import service.StudentService;

public class StudentController {

    private StudentService studentService;
    private LibraryService libraryService;
    private Scanner scanner;

    public StudentController() {
        this.studentService = new StudentService();
        this.libraryService = new LibraryService();
        this.scanner = new Scanner(System.in);
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
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewCourses();
                    break;
                case 2:
                    viewTimetable();
                    break;
                case 3:
                    viewMarks();
                    break;
                case 4:
                    viewAttendance();
                    break;
                case 5:
                    viewNotifications();
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
                case 0:
                    System.out.println("Logging out...");
                     return;
                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 0);
    }

    public void viewCourses() { studentService.viewCourses(); }
    public void viewTimetable() { studentService.viewTimetable(); }
    public void viewMarks() { studentService.viewMarks(); }
    public void viewAttendance() { studentService.viewAttendance(); }
    public void viewNotifications() { studentService.viewNotifications(); }
    

    public void searchBook() {
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();
        libraryService.searchBook(keyword);
    }
    
    public void borrowBook() {
        System.out.print("Enter Student ID: ");
        int studentId = scanner.nextInt();

        System.out.print("Enter Book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        libraryService.borrowBook(studentId,"student", bookId);
    }

    public void returnBook() {
        System.out.print("Enter Record ID: ");
        int recordId = scanner.nextInt();
        scanner.nextLine();

        libraryService.returnBook(recordId);
    }
}