package controller;

import java.util.Scanner;
import service.FacultyService;
import service.LibraryService;
import service.StudentService;
import service.AdminService;

public class FacultyController {

    private FacultyService facultyService = new FacultyService();
    private StudentService studentService = new StudentService();
    private AdminService adminService = new AdminService();
    private LibraryService libraryService=new LibraryService();
    private Scanner scanner = new Scanner(System.in);

    public void showMenu() {

        while (true) {

            System.out.println("\n--- Faculty Menu ---");
            System.out.println("1. View Students");
            System.out.println("2. Mark Attendance");
            System.out.println("3. Enter Marks");
            System.out.println("4. View Timetable");
            System.out.println("5. View Notifications");
            System.out.println("6. Search Book");
            System.out.println("7. Borrow Book");
            System.out.println("8. Return Book");
            System.out.println("0. Logout");

            int choice = scanner.nextInt();
            switch (choice) {
            case 1:
            	int facultyId1=101;
                facultyService.viewMyStudents(facultyId1);
                break;

            case 2:
                System.out.println("Enter Student ID:");
                int studentId = scanner.nextInt();

                System.out.println("Present? (true/false)");
                boolean present = scanner.nextBoolean();

                facultyService.markAttendance(studentId, present);
                break;

            case 3:
                System.out.println("Enter Student ID:");
                int sid = scanner.nextInt();

                System.out.println("Enter Course ID:");
                int cid = scanner.nextInt();

                System.out.println("Enter Marks:");
                int marks = scanner.nextInt();

                facultyService.addResult(sid, cid, marks);
                break;

            case 4:
            	System.out.println("Enter your Faculty ID:");
            	int facultyId=scanner.nextInt();
                facultyService.viewTimetable(facultyId);
                break;
                
            case 5:
                facultyService.viewNotification();
                break;

            case 6:
                System.out.print("Enter keyword: ");
                scanner.nextLine(); // fix buffer
                String keyword = scanner.nextLine();
                libraryService.searchBook(keyword);
                break;

            case 7:
                System.out.println("Enter Faculty ID:");
                int fid = scanner.nextInt();

                System.out.println("Enter Book ID:");
                int bookId = scanner.nextInt();

                libraryService.borrowBook(fid, "faculty" ,bookId);
                break;

            case 8:
                System.out.println("Enter Record ID:");
                int recordId = scanner.nextInt();

                libraryService.returnBook(recordId);
                break;

            case 0:
                System.out.println("Logging out...");
                return;

            default:
                System.out.println("Invalid Choice");
            }
        }
    }
}