/** 
 * Author: Haritha
 * This admin controller will handles the managing admin ,student, faculty details and display the courses and prepare timetable
 * assign the course to the faculty, student and   create the schedule for the exams send the important notification to the student 
 * and faculty and atlast publish the student results.
 */

package controller;

import java.util.Scanner;
import service.AdminService;
import service.StudentService;
import service.FacultyService;

public class AdminController {

    private AdminService adminService = new AdminService();
    private StudentService studentService = new StudentService();
    private FacultyService facultyService = new FacultyService();

    private Scanner input = new Scanner(System.in);
    private int userId; // logged-in admin's userId

    // ✅ Constructor to set logged-in userId
    public AdminController(int userId) {
        this.userId = userId;
    }

    public void showMenu() {

        int choice = 0;

        while (choice != 11) {

            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Manage Admin");
            System.out.println("2. Manage Student");
            System.out.println("3. Manage Faculty");
            System.out.println("4. Manage Course");
            System.out.println("5. Manage Timetable");
            System.out.println("6. Schedule exam");
            System.out.println("7. Send notification");
            System.out.println("8. Manage Library");
            System.out.println("9. Publish Result");   
            System.out.println("10. View All Results"); 
            System.out.println("11. Logout");

            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:
                    manageAdmin();         // add , update, delete
                    break;
                case 2:
                    manageStudent();      // add ,update , delete
                    break;
                case 3:
                    manageFaculty();      // add ,update, delete
                    break; 
                case 4:
                    manageCourse();       // add , assigning course , enrolling student , delete
                    break;
                case 5:
                    manageTimetable();    // add , update ,delete
                    break;
                case 6:
                    examSchedule();       // add , update , delete
                    break;
                case 7:
                    manageNotification(); // add , delete
                    break;
                case 8:
                    manageLibrary();      // add , update , remove
                    break;
                case 9:
                    publishResult();     // publish student result with student id
                    break;
                case 10:
                    adminService.viewAllResults();
                    break;
                case 11:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    // ================= ADMIN =================
    private void manageAdmin() {

        int choice = 0;

        while (choice != 5) {

            System.out.println("\n--- Manage Admin ---");
            System.out.println("1. Add Admin");
            System.out.println("2. Update Admin");
            System.out.println("3. Delete Admin");
            System.out.println("4. View Admins");
            System.out.println("5. Exit");

            choice = input.nextInt();
            input.nextLine();
             
            if (choice == 1) {     // add admin details

                System.out.print("Enter Email: ");
                String email = input.nextLine();

                System.out.print("Enter Password: ");
                String password = input.nextLine();

                System.out.print("Enter Admin Name: ");
                String name = input.nextLine();

                System.out.print("Enter DOB (yyyy-mm-dd): ");
                String dob = input.nextLine();

                System.out.print("Enter Contact Number: ");
                String contact = input.nextLine();

                adminService.addAdminWithUser(email, password, name, dob, contact);    
            }

            else if (choice == 2) {  //update admin

                System.out.print("Enter Admin ID: ");
                int id = input.nextInt();
                input.nextLine();

                System.out.println("Which field do you want to update?");
                System.out.println("1. Name");
                System.out.println("2. DOB");
                System.out.println("3. Contact");
                System.out.print("Enter choice: ");
                int fieldChoice = input.nextInt();
                input.nextLine();

                String field = "", value = "";

                switch (fieldChoice) {
                    case 1:
                        field = "name";
                        System.out.print("Enter new Name: ");
                        value = input.nextLine();
                        break;
                    case 2:
                        field = "dob";
                        System.out.print("Enter new DOB: ");
                        value = input.nextLine();
                        break;
                    case 3:
                        field = "contact";
                        System.out.print("Enter new Contact: ");
                        value = input.nextLine();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        return;
                }

                adminService.updateAdmin(id, field, value);
            }

            else if (choice == 3) {    //delete admin

                System.out.print("Enter Admin ID: ");
                int id = input.nextInt();

                adminService.deleteAdmin(id);
            }
            else if (choice == 4) adminService.viewAdmins();
        }
    }

    // ================= STUDENT =================
    public void manageStudent() {

        int choice = 0;

        while (choice != 5) {

            System.out.println("\n--- Manage Student ---");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. View Students");
            System.out.println("5. Exit");

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) { // add student

                System.out.print("Enter Email: ");
                String email = input.nextLine();

                System.out.print("Enter Password: ");
                String password = input.nextLine();

                System.out.print("Enter Student Name: ");
                String name = input.nextLine();

                System.out.print("Enter Department: ");
                String dept = input.nextLine();

                System.out.print("Enter DOB (yyyy-mm-dd): ");
                String dob = input.nextLine();

                System.out.print("Enter Contact Number: ");
                String contact = input.nextLine();
                
                System.out.println("Enter Section (A/B/C):");
                String section = input.nextLine();

                studentService.addStudentWithUser(email, password,name, dept, dob, contact,section);
            }

            else if (choice == 2) {    //update student

                System.out.print("Enter Student ID: ");
                int id = input.nextInt();
                input.nextLine();

                System.out.println("Which field do you want to update?");
                System.out.println("1. Name");
                System.out.println("2. Department");
                System.out.println("3. DOB");
                System.out.println("4. Contact");
                System.out.println("5. Section");
                System.out.print("Enter choice: ");
                int fieldChoice = input.nextInt();
                input.nextLine();

                String field = "", value = "";

                switch (fieldChoice) {
                    case 1:
                        field = "name";
                        System.out.print("Enter new Name: ");
                        value = input.nextLine();
                        break;
                    case 2:
                        field = "department";
                        System.out.print("Enter new Department: ");
                        value = input.nextLine();
                        break;
                    case 3:
                        field = "dob";
                        System.out.print("Enter new DOB (yyyy-MM-dd): ");
                        value = input.nextLine();
                        break;
                    case 4:
                        field = "contact";
                        System.out.print("Enter new Contact: ");
                        value = input.nextLine();
                        break;
                    case 5:
                        field = "section";
                        System.out.print("Enter new Section: ");
                        value = input.nextLine();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        return;
                }

                studentService.updateStudent(id, field, value);
            }

            else if (choice == 3) {   //delete student

                System.out.print("Enter Student ID: ");
                int id = input.nextInt();

                studentService.deleteStudent(id);
            }
            else if (choice == 4) studentService.viewStudents();
        }
    }

    // ================= FACULTY =================
    public void manageFaculty() {

        int choice = 0;

        while (choice != 5) {

            System.out.println("\n--- Manage Faculty ---");
            System.out.println("1. Add Faculty");
            System.out.println("2. Update Faculty");
            System.out.println("3. Delete Faculty");
            System.out.println("4. View Faculty");
            System.out.println("5. Exit");

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {  // add faculty

            	System.out.print("Enter Email: ");
                String email = input.nextLine();

                System.out.print("Enter Password: ");
                String password = input.nextLine();

                System.out.print("Enter Faculty Name: ");
                String name = input.nextLine();
                
                System.out.println("Enter the Faculty Department:");
                String dep=input.nextLine();
               
                System.out.print("Enter DOB (yyyy-mm-dd): ");
                String dob = input.nextLine();

                System.out.print("Enter Contact Number: ");
                String contact = input.nextLine();

                facultyService.addFacultyWithUser(email, password, name,dep, dob, contact);
            }

            else if (choice == 2) {    //update faculty

                System.out.print("Enter Faculty ID: ");
                int id = input.nextInt();
                input.nextLine();
                //update the fields by particular selection
                System.out.println("Which field do you want to update?");
                System.out.println("1. Name");
                System.out.println("2. Department");
                System.out.println("3. DOB");
                System.out.println("4. Contact");
                System.out.print("Enter choice: ");
                int fieldChoice = input.nextInt();
                input.nextLine();

                String field = "", value = "";

                switch (fieldChoice) {
                    case 1:
                        field = "name";
                        System.out.print("Enter new Name: ");
                        value = input.nextLine();
                        break;
                    case 2:
                        field = "department";
                        System.out.print("Enter new Department: ");
                        value = input.nextLine();
                        break;
                    case 3:
                        field = "dob";
                        System.out.print("Enter new DOB (yyyy-MM-dd): ");
                        value = input.nextLine();
                        break;
                    case 4:
                        field = "contact";
                        System.out.print("Enter new Contact: ");
                        value = input.nextLine();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        return;
                }

                facultyService.updateFaculty(id, field, value);
            }

            else if (choice == 3) {  //delete faculty

                System.out.print("Enter Faculty ID: ");
                int id = input.nextInt();

                facultyService.deleteFaculty(id);
            }
            else if (choice == 4) facultyService.viewFaculty();
        }
    }
    // ================= COURSE =================
    public void manageCourse() {

        int choice = 0;

        while (choice != 8) {

            System.out.println("\n--- Manage Course ---");
            System.out.println("1. Add Course");
            System.out.println("2. Update Course");
            System.out.println("3. Assign Course to Faculty");
            System.out.println("4. Assign Student to Course");
            System.out.println("5. View Courses");
            System.out.println("6. View Enrollment Details");
            System.out.println("7. Delete Course / Enrollment");
            System.out.println("8. Exit");

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1: // Add Course
                    System.out.print("Enter Course Name: ");
                    String name = input.nextLine();

                    System.out.print("Enter Credits: ");
                    int credits = input.nextInt();
                    input.nextLine();

                    System.out.print("Enter Duration (e.g., 1 semester): ");
                    String duration = input.nextLine();

                    System.out.print("Enter Department: ");
                    String department = input.nextLine();

                    System.out.print("Enter Faculty ID: ");
                    int facultyId = input.nextInt();
                    input.nextLine();

                    System.out.print("Enter Description: ");
                    String description = input.nextLine();
                    
                    System.out.print("Enter Semester: ");
                    int semester = input.nextInt();
                    input.nextLine();

                    adminService.addCourse(name, credits, duration, department, facultyId, description, semester);
                    break;

				case 2: // Update Course
					System.out.print("Enter Course ID to update: ");
					int cId = input.nextInt();
					input.nextLine();

					System.out.println("Which field do you want to update?");
					System.out.println("1. Course Name");
					System.out.println("2. Credits");
					System.out.println("3. Duration");
					System.out.println("4. Department");
					System.out.println("5. Faculty ID");
					System.out.println("6. Description");
					System.out.println("7. Semester");

					int fieldChoice = input.nextInt();
					input.nextLine();
					String newValue = "";
					String field = "";

					switch (fieldChoice) {
					case 1:
						System.out.print("Enter new Course Name: ");
						newValue = input.nextLine();
						field = "course_name";
						break;
					case 2:
						System.out.print("Enter new Credits: ");
						newValue = input.nextLine();
						field = "credits";
						break;
					case 3:
						System.out.print("Enter new Duration: ");
						newValue = input.nextLine();
						field = "duration";
						break;
					case 4:
						System.out.print("Enter new Department: ");
						newValue = input.nextLine();
						field = "department";
						break;
					case 5:
						System.out.print("Enter new Faculty ID: ");
						newValue = input.nextLine();
						field = "faculty_id";
						break;
					case 6:
						System.out.print("Enter new Description: ");
						newValue = input.nextLine();
						field = "description";
						break;
					case 7:
					    System.out.print("Enter new Semester: ");
					    newValue = input.nextLine();
					    field = "semester";
					    break;
					default:
						System.out.println("Invalid choice!");
						break;
					}

					if (!field.isBlank()) {
						adminService.updateCourse(cId, field, newValue);
					}
					break;

                case 3: // Assign Course to Faculty
                    System.out.print("Enter Course ID: ");
                    int assignCourseId = input.nextInt();

                    System.out.print("Enter Faculty ID: ");
                    int assignFacultyId = input.nextInt();

                    adminService.assignCourse(assignCourseId, assignFacultyId);
                    break;

                case 4: // Assign Student to Course
                    System.out.print("Enter Student ID: ");
                    int studentId = input.nextInt();

                    System.out.print("Enter Course ID: ");
                    int assignCourseId2 = input.nextInt();

                    adminService.assignStudentToCourse(studentId, assignCourseId2);
                    break;

                case 5: // View Courses
                    adminService.viewCourses();
                    break;

                case 6: // View Enrollment Details
                    adminService.viewEnrollmentDetails();
                    break;

                case 7: // Delete Course or Enrollment
                    System.out.println("1. Delete Course");
                    System.out.println("2. Delete Student Enrollment");
                    int delChoice = input.nextInt();

                    if (delChoice == 1) {
                        System.out.print("Enter Course ID to delete: ");
                        int delCourseId = input.nextInt();
                        adminService.deleteCourse(delCourseId);
                    } else if (delChoice == 2) {
                        System.out.print("Enter Student ID: ");
                        int delStudentId = input.nextInt();

                        System.out.print("Enter Course ID: ");
                        int delEnrollCourseId = input.nextInt();

                        adminService.deleteEnrollment(delStudentId, delEnrollCourseId);
                    }
                    break;

                case 8: // Exit
                    System.out.println("Exiting Course Management...");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
    // ================= TIMETABLE =================
    public void manageTimetable() {

        int choice = 0;

        while (choice != 6) {

            System.out.println("\n--- TIMETABLE MENU ---");
            System.out.println("1. Add Timetable");
            System.out.println("2. View All");
            System.out.println("3. View by Date & Section");
            System.out.println("4. Delete Timetable");
            System.out.println("5. Update Timetable"); // ✅ NEW
            System.out.println("6. Exit");

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {   // add timetable

                case 1:
                    System.out.print("Day: ");
                    String day = input.nextLine();

                    System.out.print("Period: ");
                    int period = input.nextInt();

                    System.out.print("Room: ");
                    input.nextLine();
                    String room = input.nextLine();

                    System.out.print("Course ID: ");
                    int courseId = input.nextInt();

                    System.out.print("Faculty ID: ");
                    int facultyId = input.nextInt();
                    input.nextLine();

                    System.out.print("Section: ");
                    String section = input.nextLine();

                    adminService.addTimetable(facultyId, day, period, room, courseId, section);
                    break;

                case 2:
                    adminService.viewTimetable();    //view timetables details from database
                    break;

                case 3:
                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String date = input.nextLine();

                    System.out.print("Enter Section: ");
                    String sec = input.nextLine();

                    adminService.viewByDate(date, sec);    // view timetable by particular date and section
                    break;

                case 4:
                    System.out.print("Enter Timetable ID to delete: ");
                    int timetableId = input.nextInt();
                    input.nextLine();

                    adminService.deleteTimetable(timetableId);     // delete timetable
                    break;
                  
                case 5:
                    System.out.print("Enter Timetable ID to update: ");
                    int tId = input.nextInt();
                    input.nextLine();
                    // update the particular field
                    System.out.println("What do you want to update?");
                    System.out.println("1. Day");
                    System.out.println("2. Period");
                    System.out.println("3. Room");
                    System.out.println("4. Course ID");
                    System.out.println("5. Faculty ID");
                    System.out.println("6. Section");

                    int fieldChoice = input.nextInt();
                    input.nextLine();

                    String newValue = "";

                    switch (fieldChoice) {

                        case 1:
                            System.out.print("Enter new Day: ");
                            newValue = input.nextLine();
                            adminService.updateTimetable(tId, "day", newValue);
                            break;

                        case 2:
                            System.out.print("Enter new Period: ");
                            newValue = input.nextLine();
                            adminService.updateTimetable(tId, "period", newValue);
                            break;

                        case 3:
                            System.out.print("Enter new Room: ");
                            newValue = input.nextLine();
                            adminService.updateTimetable(tId, "room", newValue);
                            break;

                        case 4:
                            System.out.print("Enter new Course ID: ");
                            newValue = input.nextLine();
                            adminService.updateTimetable(tId, "course_id", newValue);
                            break;

                        case 5:
                            System.out.print("Enter new Faculty ID: ");
                            newValue = input.nextLine();
                            adminService.updateTimetable(tId, "faculty_id", newValue);
                            break;

                        case 6:
                            System.out.print("Enter new Section: ");
                            newValue = input.nextLine();
                            adminService.updateTimetable(tId, "section", newValue);
                            break;

                        default:
                            System.out.println("Invalid choice");
                    }
                    break;
            }
        }
    }


    // ================= EXAM =================
    public void examSchedule() {

        int choice = 0;

        while (choice != 5) {

            System.out.println("\n--- Manage Exam ---");
            System.out.println("1. Add Exam");
            System.out.println("2. View Exams");
            System.out.println("3. Delete Exam");
            System.out.println("4. Update Exam");
            System.out.println("5. Exit");

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {   // add exam

                System.out.print("Enter Course ID: ");
                int courseId = input.nextInt();
                input.nextLine();

                System.out.print("Enter Exam Date: ");
                String examDate = input.nextLine();

                System.out.println("Enter Max Marks:");
                int maxMark = input.nextInt();

                adminService.scheduleExam( courseId, examDate, maxMark);

            } else if (choice == 2) {

                adminService.viewSchedules();   // view exam from database

            } else if (choice == 3) {

                //  DELETE
                System.out.print("Enter Exam ID to delete: ");
                int examId = input.nextInt();
                input.nextLine();

                adminService.deleteExam(examId);

            } else if (choice == 4) {

                // update particular field
                System.out.print("Enter Exam ID to update: ");
                int examId = input.nextInt();
                input.nextLine();

                System.out.println("What do you want to update?");
                System.out.println("1. Course ID");
                System.out.println("2. Exam Date");
                System.out.println("3. Max Marks");

                int fieldChoice = input.nextInt();
                input.nextLine();

                String value = "";

                switch (fieldChoice) {

                    case 1:
                        System.out.print("Enter new Course ID: ");
                        value = input.nextLine();
                        adminService.updateExam(examId, "course_id", value);
                        break;

                    case 2:
                        System.out.print("Enter new Exam Date: ");
                        value = input.nextLine();
                        adminService.updateExam(examId, "exam_date", value);
                        break;

                    case 3:
                        System.out.print("Enter new Max Marks: ");
                        value = input.nextLine();
                        adminService.updateExam(examId, "max_marks", value);
                        break;

                    default:
                        System.out.println("Invalid choice");
                }
            }
        }
    }

    // ================= PUBLISH RESULT =================
    private void publishResult() {

        System.out.print("Enter Result ID to publish: ");
        int resultId = input.nextInt();

        System.out.print("Enter Extra Marks (optional): ");
        int extraMark = input.nextInt();

        adminService.publishResult(resultId, extraMark);     //publish result based on result stored in the database
    }

    // ================= NOTIFICATION =================
    public void manageNotification() {

        int choice = 0;

        while (choice != 4) {

            System.out.println("\n--- Notification Menu ---");
            System.out.println("1. Add Notification");
            System.out.println("2. View Notifications");
            System.out.println("3. Delete Notifications");
            System.out.println("4. Exit");

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {       // add notification

                System.out.print("Enter Message: ");
                String message = input.nextLine();

                System.out.print("Enter Date (yyyy-mm-dd): ");
                String dateStr = input.nextLine();

                java.util.Date date = java.sql.Date.valueOf(dateStr);

                System.out.print("Enter Role (ALL/STUDENT/FACULTY/ADMIN): ");
                String role = input.nextLine();

                Integer targetId = null;

                if (!role.equalsIgnoreCase("ALL")) {
                    System.out.print("Enter Target ID (or 0 for all in role): ");
                    int temp = input.nextInt();
                    input.nextLine();
                    if (temp != 0) targetId = temp;
                }

                adminService.addNotification( message, date, role, targetId);
            } else if (choice == 2) {
                adminService.viewNotifications();     // view notification from database
            }
            else if ( choice == 3) {
            	System.out.print("Enter the Notification Id to delete: ");
                int id = input.nextInt();
            	adminService.deleteNotification(id);  // delete notification
            }
        }
    }

    // ================= LIBRARY =================
    public void manageLibrary() {

        int choice = 0;

        while (choice != 6) {

            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");  
            System.out.println("3. Remove Book");
            System.out.println("4. View All Books");
            System.out.println("5. View Borrow Records");
            System.out.println("6. Exit");

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1: // Add Book
                    System.out.print("Enter Title: ");
                    String title = input.nextLine();

                    System.out.print("Enter Author: ");
                    String author = input.nextLine();

                    System.out.print("Enter ISBN: ");
                    String isbn = input.nextLine();

                    System.out.print("Enter Total Copies: ");
                    int total = input.nextInt();
                    input.nextLine();

                    adminService.addBook(title, author, isbn, total);
                    break;

				case 2: // Update Book
					System.out.print("Enter Book ID to update: ");
					int bookId = input.nextInt();
					input.nextLine();

					System.out.println("Which field do you want to update?");
					System.out.println("1. Title");
					System.out.println("2. Author");
					System.out.println("3. ISBN");
					System.out.println("4. Total Copies");

					int fieldChoice = input.nextInt();
					input.nextLine();
					String newValue = "";
					String field = "";

					switch (fieldChoice) {
					case 1:
						System.out.print("Enter new Title: ");
						newValue = input.nextLine();
						field = "title";
						break;
					case 2:
						System.out.print("Enter new Author: ");
						newValue = input.nextLine();
						field = "author";
						break;
					case 3:
						System.out.print("Enter new ISBN: ");
						newValue = input.nextLine();
						field = "isbn";
						break;
					case 4:
						System.out.print("Enter new Total Copies: ");
						newValue = input.nextLine();
						field = "total_copies";
						break;
					default:
						System.out.println("Invalid choice!");
						break;
					}

					if (!field.isBlank()) {
						adminService.updateBook(bookId, field, newValue);
					}
					break;

				case 3: // Remove Book
					System.out.print("Enter Book ID to remove: ");
					int removeId = input.nextInt();
					input.nextLine();
					adminService.removeBook(removeId);
					break;

				case 4: // View All Books
					adminService.viewAllBooks();
					break;

				case 5: 
					adminService.viewBorrowRecords();
					break;

				case 6: // Exit
					System.out.println("Exiting Library Menu...");
					break;

				default:
					System.out.println("Invalid choice");
				}
			}
		}
}
