package controller;

import java.util.Scanner;
import service.FacultyService;

public class FacultyController {

    private FacultyService facultyService = new FacultyService();
    private Scanner scanner = new Scanner(System.in);
    private int userId; // logged-in faculty's userId

    public FacultyController(int userId) {
        this.userId = userId;
    }

    public void showMenu() {

        while (true) {

            System.out.println("\n--- Faculty Menu ---");
            System.out.println("1. View My Students");
            System.out.println("2. View My Courses");
            System.out.println("3. Mark Attendance");
            System.out.println("4. Enter Marks");
            System.out.println("5. View Timetable");
            System.out.println("6. Manage Material");
            System.out.println("7. Manage Assignment");
            System.out.println("8. View Notifications");
            System.out.println("9. Search Book");
            System.out.println("10. Borrow Book");
            System.out.println("11. Return Book");
            System.out.println("12. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    facultyService.viewMyStudents(userId);
                    break;

                case 2:
                    facultyService.viewMyCourses(userId);
                    break;

                case 3:
                    System.out.println("Enter Student ID:");
                    int studentId = scanner.nextInt();

                    System.out.println("Enter Course ID:");
                    int courseId = scanner.nextInt();

                    System.out.println("Present? (true/false):");
                    boolean present = scanner.nextBoolean();
                    scanner.nextLine();

                    facultyService.markAttendance(studentId, courseId, present);
                    break;

                case 4:
                    System.out.println("Enter Student ID:");
                    int sid = scanner.nextInt();

                    System.out.println("Enter Course ID:");
                    int cid = scanner.nextInt();

                    System.out.println("Enter Marks:");
                    int marks = scanner.nextInt();
                    scanner.nextLine();

                    facultyService.addResult(sid, cid, marks);
                    break;

                case 5:
                    facultyService.viewTimetable(userId);
                    break;

                case 6:
                    int choices = 0;

                    while (choices != 4) {

                        System.out.println("\n--- Manage Material ---");
                        System.out.println("1. Upload Material");
                        System.out.println("2. View Material");
                        System.out.println("3. Delete Material");
                        System.out.println("4. Exit");

                        choices = scanner.nextInt();
                        scanner.nextLine();

                        if (choices == 1) {
                            System.out.println("Enter Course ID:");
                            int cId = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println("Enter Material Title:");
                            String title = scanner.nextLine();

                            System.out.println("Enter Content:");
                            String content = scanner.nextLine();

                            facultyService.uploadMaterial(cId, title, content, userId);

                        } else if (choices == 2) {
                            System.out.println("Enter Course ID:");
                            int courseId1 = scanner.nextInt();
                            scanner.nextLine();

                            facultyService.viewMaterials(courseId1, userId, "FACULTY");

                        } else if (choices == 3) {
                        	System.out.println("Enter Material ID to remove:");
                        	int materialId = scanner.nextInt();
                        	scanner.nextLine();

                        	facultyService.deleteMaterial(materialId, userId, "FACULTY");
                        }
                    }
                    break; 

				case 7:
					int choices1 = 0;

					while (choices1 != 4) {

						System.out.println("\n--- Manage Assignments ---");
						System.out.println("1. Upload Assignments");
						System.out.println("2. View Assignments");
						System.out.println("3. Delete Assignments");
						System.out.println("4. Exit");

						choices1 = scanner.nextInt();
						scanner.nextLine();
						if (choices1 == 1) {

							System.out.println("Enter Course ID:");
							int courseId2 = scanner.nextInt();
							scanner.nextLine();

							System.out.println("Enter Assignment Title:");
							String atitle = scanner.nextLine();

							System.out.println("Enter Description:");
							String desc = scanner.nextLine();

							facultyService.createAssignment(courseId2, atitle, desc, userId);
							
						} else if (choices1 == 2) {
							System.out.println("Enter Course ID:");
							int courseId3 = scanner.nextInt();
							scanner.nextLine();

							facultyService.viewAssignments(courseId3, userId, "FACULTY");
							

						} else if (choices1 == 3) {
							System.out.println("Enter Assignment ID to remove:");
							int assignId = scanner.nextInt();
							scanner.nextLine();

							facultyService.deleteAssignment(assignId, userId, "FACULTY");
							
						}
					}
					break;
					
                case 8:
                    facultyService.viewNotification(userId);
                    break;

                case 9:
                    System.out.print("Enter keyword: ");
                    String keyword = scanner.nextLine();
                    facultyService.searchBook(keyword);
                    break;

                case 10:
                    System.out.println("Enter Book ID:");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();

                    facultyService.borrowBook(userId, bookId);
                    break;

                case 11:
                    System.out.print("Enter Book ID to return: ");
                    int book_Id = scanner.nextInt();
                    scanner.nextLine();

                    facultyService.returnBook(book_Id, userId);
                    break;

                case 12: // ✅ FIXED
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}