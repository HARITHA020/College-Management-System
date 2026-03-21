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
	private Scanner scanner = new Scanner(System.in);
	
	public void showMenu() {
		int facultyId=101;
		while (true) {

			System.out.println("\n--- Faculty Menu ---");
			System.out.println("1. View MyStudents");
			System.out.println("2. view Mycourse");
			System.out.println("3. Mark Attendance");
			System.out.println("4. Enter Marks");
			System.out.println("5. View Timetable");
			System.out.println("6. Upload Material");
			System.out.println("7. View Materials");
			System.out.println("8. Create Assignment");
			System.out.println("9. View Assignments");
			System.out.println("10. View Notifications");
			System.out.println("11. Search Book");
			System.out.println("12. Borrow Book");
			System.out.println("13. Return Book");
			System.out.println("0. Logout");

			int choice = scanner.nextInt();
			switch (choice) {

			case 1:
				System.out.println("enter the faculty id to view the student:");
				int facultyIDS=scanner.nextInt();
				facultyService.viewMyStudents(facultyIDS);
				break;

			case 2:
				System.out.println("enter the faculty id to view the course:");
				int facultyIDCourse=scanner.nextInt();
				facultyService.viewMyCourses(facultyIDCourse);
				
			case 3:
			    System.out.println("Enter Student ID:");
			    int studentId = scanner.nextInt();

			    System.out.println("Enter Course ID:");
			    int courseId = scanner.nextInt();

			    System.out.println("Present? (true/false):");
			    boolean present = scanner.nextBoolean();

			    facultyService.markAttendance(studentId, courseId, present);
			    break;

			case 4:
				System.out.println("Enter Student ID:");
				int sid = scanner.nextInt();

				System.out.println("Enter Course ID:");
				int cid = scanner.nextInt();

				System.out.println("Enter Marks:");
				int marks = scanner.nextInt();

				facultyService.addResult(sid, cid, marks);
				break;

			case 5:
				System.out.println("Enter your Faculty ID:");
				int facultyIdtable = scanner.nextInt();
				facultyService.viewTimetable(facultyIdtable);
				break;

			case 6:
			    System.out.println("Enter Course ID:");
			    int cId = scanner.nextInt();
			    scanner.nextLine();

			    System.out.println("Enter Material Title:");
			    String title = scanner.nextLine();

			    System.out.println("Enter Content:");
			    String content = scanner.nextLine();

			    // ✅ pass facultyId
			    facultyService.uploadMaterial(cId, title, content, facultyId);
			    break;

			case 7:
			    System.out.println("Enter Course ID:");
			    int courseId1 = scanner.nextInt();

			    // ✅ pass userId + role
			    facultyService.viewMaterials(courseId1, facultyId, "FACULTY");
			    break;

			case 8:
			    System.out.println("Enter Course ID:");
			    int courseId2 = scanner.nextInt();
			    scanner.nextLine();

			    System.out.println("Enter Assignment Title:");
			    String atitle = scanner.nextLine();

			    System.out.println("Enter Description:");
			    String desc = scanner.nextLine();

			    // ✅ pass facultyId
			    facultyService.createAssignment(courseId2, atitle, desc, facultyId);
			    break;

			case 9:
			    System.out.println("Enter Course ID:");
			    int courseId3 = scanner.nextInt();

			    // ✅ pass userId + role
			    facultyService.viewAssignments(courseId3, facultyId, "FACULTY");
			    break;

			case 10:
				System.out.println("Enter the Faculty ID to send notification for specific:");
				int facultyIdNotify=scanner.nextInt();
				facultyService.viewNotification(facultyIdNotify);
				break;

			case 11:
				System.out.print("Enter keyword: ");
				scanner.nextLine();
				String keyword = scanner.nextLine();
				facultyService.searchBook(keyword);
				break;

			case 12:
				System.out.println("Enter Faculty ID:");
				int fid = scanner.nextInt();

				System.out.println("Enter Book ID:");
				int bookId = scanner.nextInt();

				facultyService.borrowBook(fid, bookId);
				break;

			case 13:
				System.out.println("Enter Record ID:");
				int recordId = scanner.nextInt();

				facultyService.returnBook(recordId);
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