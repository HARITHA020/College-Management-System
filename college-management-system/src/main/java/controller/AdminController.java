package controller;

import java.util.Scanner;
import service.AdminService;
import service.StudentService;
import service.FacultyService;

public class AdminController {

    private AdminService adminService = new AdminService();
    private StudentService studentService = new StudentService();
    private FacultyService facultyService = new FacultyService();


    Scanner input = new Scanner(System.in);

    public void showMenu() {

        int choice = 0;

        while (choice != 12) {

            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Manage Admin");
            System.out.println("2. Manage Student");
            System.out.println("3. Manage Faculty");
            System.out.println("4. Manage Course");
            System.out.println("5. Manage Timetable");
            System.out.println("6. Schedule exam");
            System.out.println("7. Send notification");
            System.out.println("8. Manage Library");
            System.out.println("9. View Details");
            System.out.println("10. Publish Result");   
            System.out.println("11. View All Results"); 
            System.out.println("12. Logout");

            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:
                    manageAdmin();
                    break;

                case 2:
                    manageStudent();
                    break;

                case 3:
                    manageFaculty();
                    break;

                case 4:
                    manageCourse();
                    break;

                case 5:
                    manageTimetable();
                    break;

                case 6:
                    examSchedule();
                    break;

                case 7:
                    manageNotification();
                    break;

                case 8:
                    manageLibrary();
                    break;

                case 9:
                    viewDetails();
                    break;

                case 10:
                    publishResult(); // NEW
                    break;

                case 11:
                    adminService.viewAllResults(); // NEW
                    break;

                case 12:
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

        while (choice != 4) {

            System.out.println("\n--- Manage Admin ---");
            System.out.println("1. Add Admin");
            System.out.println("2. Update Admin");
            System.out.println("3. Delete Admin");
            System.out.println("4. Exit");

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) { // Add Admin
                System.out.print("Enter Admin ID: ");
                int id = input.nextInt();
                input.nextLine();

                System.out.print("Enter Admin Name: ");
                String name = input.nextLine();

                System.out.print("Enter Password: ");
                String password = input.nextLine();

                System.out.print("Enter DOB (yyyy-mm-dd): ");
                String dob = input.nextLine();

                System.out.print("Enter Contact Number: ");
                String contact = input.nextLine();
                
                System.out.print("Enter User ID: ");
                int userId = input.nextInt();
                input.nextLine();

                adminService.addAdmin(id, name, password, dob, contact,userId);
            }
            else if (choice == 2) { // Update Admin
                System.out.print("Enter Admin ID: ");
                int id = input.nextInt();
                input.nextLine();

                System.out.print("Enter New Name: ");
                String name = input.nextLine();

                System.out.print("Enter New Password: ");
                String password = input.nextLine();

                System.out.print("Enter New DOB (yyyy-mm-dd): ");
                String dob = input.nextLine();

                System.out.print("Enter New Contact Number: ");
                String contact = input.nextLine();
                
                System.out.print("Enter User ID: ");
                int userId = input.nextInt();
                input.nextLine();

                adminService.updateAdmin(id, name, password, dob, contact,userId);
            }

            else if (choice == 3) {

                System.out.print("Enter Admin ID: ");
                int id = input.nextInt();

                adminService.deleteAdmin(id);
            }
        }
    }

    // ================= STUDENT =================
    public void manageStudent() {

        int choice = 0;

        while (choice != 4) {

            System.out.println("\n--- Manage Student ---");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Exit");

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) { // Add Student
                System.out.print("Enter Student ID: ");
                int id = input.nextInt();
                input.nextLine();

                System.out.print("Enter Student Name: ");
                String name = input.nextLine();

                System.out.print("Enter Department: ");
                String dept = input.nextLine();

                System.out.print("Enter DOB (yyyy-mm-dd): ");
                String dob = input.nextLine();

                System.out.print("Enter Contact Number: ");
                String contact = input.nextLine();
                
                System.out.print("Enter User ID: ");
                int userId = input.nextInt();
                input.nextLine();

                studentService.addStudent(id, name, dept, dob, contact,userId);
            }

            else if (choice == 2) { // Update Student
                System.out.print("Enter Student ID: ");
                int id = input.nextInt();
                input.nextLine();

                System.out.print("Enter New Name: ");
                String name = input.nextLine();

                System.out.print("Enter New Department: ");
                String dept = input.nextLine();

                System.out.print("Enter New DOB (yyyy-mm-dd): ");
                String dob = input.nextLine();

                System.out.print("Enter New Contact Number: ");
                String contact = input.nextLine();
                
                System.out.print("Enter User ID: ");
                int userId = input.nextInt();
                input.nextLine();

                studentService.updateStudent(id, name, dept, dob, contact,userId);
            }

            else if (choice == 3) {

                System.out.print("Enter Student ID: ");
                int id = input.nextInt();

                studentService.deleteStudent(id);
            }
        }
    }

    // ================= FACULTY =================
    public void manageFaculty() {

        int choice = 0;

        while (choice != 4) {

            System.out.println("\n--- Manage Faculty ---");
            System.out.println("1. Add Faculty");
            System.out.println("2. Update Faculty");
            System.out.println("3. Delete Faculty");
            System.out.println("4. Exit");

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) { // Add Faculty
                System.out.print("Enter Faculty ID: ");
                int id = input.nextInt();
                input.nextLine();

                System.out.print("Enter Faculty Name: ");
                String name = input.nextLine();

                System.out.print("Enter Faculty Department: ");
                String dept = input.nextLine();

                System.out.print("Enter DOB (yyyy-mm-dd): ");
                String dob = input.nextLine();

                System.out.print("Enter Contact Number: ");
                String contact = input.nextLine();
                
                System.out.print("Enter User ID: ");
                int userId = input.nextInt();
                input.nextLine();

                facultyService.addFaculty(id, name, dept, dob, contact,userId);
            }

            else if (choice == 2) { // Update Faculty
                System.out.print("Enter Faculty ID: ");
                int id = input.nextInt();
                input.nextLine();

                System.out.print("Enter New Name: ");
                String name = input.nextLine();

                System.out.print("Enter New Department: ");
                String dept = input.nextLine();

                System.out.print("Enter New DOB (yyyy-mm-dd): ");
                String dob = input.nextLine();

                System.out.print("Enter New Contact Number: ");
                String contact = input.nextLine();
                
                System.out.print("Enter User ID: ");
                int userId = input.nextInt();
                input.nextLine();

                facultyService.updateFaculty(id, name, dept, dob, contact,userId);
            }

            else if (choice == 3) {

                System.out.print("Enter Faculty ID: ");
                int id = input.nextInt();

                facultyService.deleteFaculty(id);
            }
        }
    }

    // ================= COURSE =================
    public void manageCourse() {

        int choice = 0;

        while (choice != 3) {

            System.out.println("\n--- Manage Course ---");
            System.out.println("1. Add Course");
            System.out.println("2. Assign Course");
            System.out.println("3. Assign Student to Course");
            System.out.println("4. Exit");

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {

                System.out.print("Enter Course ID: ");
                int courseId = input.nextInt();
                input.nextLine();

                System.out.print("Enter Course Name: ");
                String courseName = input.nextLine();

                adminService.addCourse(courseId, courseName);
            }

            else if (choice == 2) {

                System.out.print("Enter Course ID: ");
                int courseId = input.nextInt();

                System.out.print("Enter Faculty ID: ");
                int facultyId = input.nextInt();

                adminService.assignCourse(courseId, facultyId);
            }
            
           

            else if (choice == 3) {
                System.out.print("Enter Student ID: ");
                int studentId = input.nextInt();

                System.out.print("Enter Course ID: ");
                int courseId = input.nextInt();

                adminService.assignStudentToCourse(studentId, courseId);
            }
        }
    }

    // ================= TIMETABLE =================
    public void manageTimetable() {

        System.out.println("\n--- Add Timetable ---");

        System.out.print("Enter Timetable ID: ");
        int id = input.nextInt();
        input.nextLine();

        System.out.print("Enter Day: ");
        String day = input.nextLine();

        System.out.print("Enter Time: ");
        String time = input.nextLine();

        System.out.print("Enter Room Number: ");
        String room = input.nextLine();

        System.out.print("Enter Course ID: ");
        int courseId = input.nextInt();
        
        System.out.print("Enter Faculty ID: ");
        int facultyId = input.nextInt();
        input.nextLine();

        System.out.print("Enter Section : ");
        String section = input.nextLine();

        adminService.addTimetable(id, facultyId, day, time, room, courseId, section);
    }

    // ================= VIEW DETAILS =================
    public void viewDetails() {

        int choice = 0;

        while (choice != 7) {

            System.out.println("\n--- View Details ---");
            System.out.println("1. View Students");
            System.out.println("2. View Faculties");
            System.out.println("3. View Admins");
            System.out.println("4. View Courses");
            System.out.println("5. View Timetable");
            System.out.println("6. View ExamSchedules");
            System.out.println("7. Exit");

            choice = input.nextInt();

            if (choice == 1) studentService.viewStudents();
            else if (choice == 2) facultyService.viewFaculties();
            else if (choice == 3) adminService.viewAdmins();
            else if (choice == 4) adminService.viewCourses();
            else if (choice == 5) adminService.viewTimetable();
            else if (choice == 6) adminService.viewSchedules();
        }
    }

    // ================= EXAM =================
    public void examSchedule() {

        System.out.print("Enter Exam ID: ");
        int examId = input.nextInt();

        System.out.print("Enter Course ID: ");
        int courseId = input.nextInt();
        input.nextLine();

        System.out.print("Enter Exam Date: ");
        String examDate = input.nextLine();
        
        System.out.println("Enter the Max Mark:");
        int maxMark=input.nextInt();

        adminService.scheduleExam(examId, courseId, examDate,maxMark);
    }

    // ================= PUBLISH RESULT =================
    private void publishResult() {

        System.out.print("Enter Result ID to publish: ");
        int resultId = input.nextInt();
        
        System.out.print("Enter the Extra mark(optional):");
        int extraMark=input.nextInt();

        adminService.publishResult(resultId, extraMark);
    }

    // ================= NOTIFICATION =================
    public void manageNotification() {

        int choice = 0;

        while (choice != 3) {

            System.out.println("\n--- Notification Menu ---");
            System.out.println("1. Add Notification");
            System.out.println("2. View Notifications");
            System.out.println("3. Exit");

            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {

                System.out.print("Enter Notification ID: ");
                int id = input.nextInt();
                input.nextLine();

                System.out.print("Enter Message: ");
                String message = input.nextLine();

                System.out.print("Enter Date (yyyy-mm-dd): ");
                String dateStr = input.nextLine();

                java.util.Date date = java.sql.Date.valueOf(dateStr);

                // 🔥 NEW INPUTS
                System.out.print("Enter Role (ALL/STUDENT/FACULTY): ");
                String role = input.nextLine();

                Integer targetId = null;

                if (!role.equalsIgnoreCase("ALL")) {
                    System.out.print("Enter Target ID (or 0 for all in role): ");
                    int temp = input.nextInt();
                    input.nextLine();
                    if (temp != 0) {
                        targetId = temp;
                    }
                }

                adminService.addNotification(id, message, date, role, targetId);
            }

            else if (choice == 2) {
                adminService.viewNotifications();
            }
        }
    }

    // ================= LIBRARY =================
    public void manageLibrary() {

        int choice = 0;

        while (choice != 5) {

            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. View All Books");
            System.out.println("4. View Borrow Records");
            System.out.println("5. Exit");

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter Book ID: ");
                    int id = input.nextInt();
                    input.nextLine();

                    System.out.print("Enter Title: ");
                    String title = input.nextLine();

                    System.out.print("Enter Author: ");
                    String author = input.nextLine();

                    System.out.print("Enter ISBN: ");
                    String isbn = input.nextLine();

                    adminService.addBook(id, title, author, isbn);
                    break;

                case 2:
                    System.out.print("Enter Book ID to remove: ");
                    int removeId = input.nextInt();

                    adminService.removeBook(removeId);
                    break;

                case 3:
                    adminService.viewAllBooks();
                    break;

                case 4:
                    adminService.viewBorrowRecords();
                    break;

                case 5:
                    System.out.println("Exiting Library Menu...");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}