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

		while (true) {

			System.out.println("\n--- Faculty Menu ---");
			System.out.println("1. View Students");
			System.out.println("2. Mark Attendance");
			System.out.println("3. Enter Marks");
			System.out.println("4. View Timetable");
			System.out.println("5. Upload Material");
			System.out.println("6. View Materials");
			System.out.println("7. Create Assignment");
			System.out.println("8. View Assignments");
			System.out.println("9. View Notifications");
			System.out.println("10. Search Book");
			System.out.println("11. Borrow Book");
			System.out.println("12. Return Book");
			System.out.println("0. Logout");

			int choice = scanner.nextInt();
			switch (choice) {

			case 1:
				int facultyId1 = 101;
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
				int facultyId = scanner.nextInt();
				facultyService.viewTimetable(facultyId);
				break;

			case 5:
				System.out.println("Enter Course ID:");
				int cId = scanner.nextInt();
				scanner.nextLine();

				System.out.println("Enter Material Title:");
				String title = scanner.nextLine();

				System.out.println("Enter Content:");
				String content = scanner.nextLine();

				facultyService.uploadMaterial(cId, title, content);
				break;

			case 6:
				System.out.println("Enter Course ID:");
				int courseId1 = scanner.nextInt();

				facultyService.viewMaterials(courseId1);
				break;

			case 7:
				System.out.println("Enter Course ID:");
				int courseId2 = scanner.nextInt();
				scanner.nextLine();

				System.out.println("Enter Assignment Title:");
				String atitle = scanner.nextLine();

				System.out.println("Enter Description:");
				String desc = scanner.nextLine();

				facultyService.createAssignment(courseId2, atitle, desc);
				break;

			case 8:
				System.out.println("Enter Course ID:");
				int courseId3 = scanner.nextInt();

				facultyService.viewAssignments(courseId3);
				break;

			case 9:
				facultyService.viewNotification();
				break;

			case 10:
				System.out.print("Enter keyword: ");
				scanner.nextLine();
				String keyword = scanner.nextLine();
				facultyService.searchBook(keyword);
				break;

			case 11:
				System.out.println("Enter Faculty ID:");
				int fid = scanner.nextInt();

				System.out.println("Enter Book ID:");
				int bookId = scanner.nextInt();

				facultyService.borrowBook(fid, bookId);
				break;

			case 12:
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