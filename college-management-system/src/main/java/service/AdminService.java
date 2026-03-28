package service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dao.*;
import model.*;

public class AdminService {

    private AdminDAO adminDao = new AdminDAO();
    private CourseDAO courseDao = new CourseDAO();
    private TimetableDAO timetableDao = new TimetableDAO();
    private ExamDAO examDao = new ExamDAO();
    private NotificationDAO notificationDao=new NotificationDAO();
    private BookDAO bookDAO = new BookDAO();
    private BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
    private ResultDAO resultDAO = new ResultDAO();
    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private StudentDAO studentDao= new StudentDAO();
    private FacultyDAO facultyDao= new FacultyDAO();
    private UserDAO userDAO=new UserDAO();

    // ================= ADMIN =================
    public void addAdminWithUser(String email, String password, String name, String dob, String contact) {

        if (email == null || email.trim().isEmpty()) {
            System.out.println("Email cannot be empty");
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            System.out.println("Password cannot be empty");
            return;
        }

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Admin name cannot be empty");
            return;
        }

        if (userDAO.checkEmailExists(email)) {
            System.out.println("Email already exists");
            return;
        }

        // 🔹 Create user
        int userId = userDAO.createUser(email, password, "ADMIN");

        if (userId == -1) {
            System.out.println("User creation failed");
            return;
        }

        // 🔹 Create admin
        adminDao.addAdmin(name, dob, contact, userId);

        
    }

    // ✅ UPDATE ADMIN
    public void updateAdmin(int id, String name, String dob, String contact) {

        if (id <= 0) {
            System.out.println("Invalid Admin ID");
            return;
        }

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Admin name cannot be empty");
            return;
        }

        boolean exists = false;

        for (Administrator admin : adminDao.getAllAdmins()) {
            if (admin.getId() == id) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            System.out.println("Admin not found");
            return;
        }

        adminDao.updateAdmin(id, name, dob, contact);
        System.out.println("✅ Admin updated successfully");
    }

    // ✅ DELETE ADMIN
    public void deleteAdmin(int id) {

        if (id <= 0) {
            System.out.println("Invalid Admin ID");
            return;
        }

        // 🔹 1. Get user_id from admin table
        int userId = adminDao.getUserIdByAdminId(id);

        if (userId == -1) {
            System.out.println("Admin not found");
            return;
        }

        // 🔹 2. Delete USER (not admin)
        userDAO.deleteUser(userId);

        System.out.println("✅ Admin deleted successfully (CASCADE)");
    }
    // ✅ VIEW ADMIN
    public void viewAdmins() {

        List<Administrator> admins = adminDao.getAllAdmins();

        if (admins.isEmpty()) {
            System.out.println("No admins available");
            return;
        }
        
        System.out.printf("%-5s %-20s %-15s %-15s\n",
                "ID", "Name", "DOB", "Contact");

        System.out.println("------------------------------------------------------------");

        for (Administrator admin : admins) {
            System.out.printf("%-5d %-20s %-15s %-15s\n",
                    admin.getId(),
                    admin.getName(),
                    admin.getDob(),
                    admin.getContact());
        }
    }


    // ================= COURSE =================
    public void addCourse(String courseName, int facultyId) {
        courseDao.addCourse(facultyId, courseName);
    }

    public void assignCourse(int courseId, int facultyId) {

        // 🔹 Step 1: Validate course exists
        boolean courseExists = false;
        for (Course c : courseDao.getAllCourses()) {
            if (c.getCourseId() == courseId) {
                courseExists = true;
                break;
            }
        }

        if (!courseExists) {
            System.out.println("Error: Course ID " + courseId + " does not exist.");
            return;
        }

        // 🔹 Step 2: Validate faculty exists
        boolean facultyExists = false;
        for (Faculty f : facultyDao.getAllFaculty()) {
            if (f.getId() == facultyId) {
                facultyExists = true;
                break;
            }
        }

        if (!facultyExists) {
            System.out.println("Error: Faculty ID " + facultyId + " does not exist.");
            return;
        }

        // 🔹 Step 3: Assign course to faculty
        courseDao.assignCourse(courseId, facultyId);

        System.out.println("Course Assigned Successfully");
    }
    
    public void assignStudentToCourse(int studentId, int courseId) {
        
        // 🔹 Step 1: Validate student exists
        boolean studentExists = false;
        for (Student s : studentDao.getAllStudents()) {
            if (s.getId() == studentId) {
                studentExists = true;
                break;
            }
        }

        if (!studentExists) {
            System.out.println("Error: Student ID " + studentId + " does not exist.");
            return;
        }

        // 🔹 Step 2: Validate course exists
        boolean courseExists = false;
        for (Course c : courseDao.getAllCourses()) {
            if (c.getCourseId() == courseId) {
                courseExists = true;
                break;
            }
        }

        if (!courseExists) {
            System.out.println("Error: Course ID " + courseId + " does not exist.");
            return;
        }

        // 🔹 Step 3: If both exist, enroll student
        enrollmentDAO.enrollStudent(studentId, courseId);
    }

    public void viewCourses() {

        List<Course> courses = courseDao.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses available");
            return;
        }
        System.out.printf("%-10s %-25s %-15s %-20s\n",
                "Course ID", "Course Name", "Faculty ID", "FacultyName");

        System.out.println("----------------------------------------------------------------");

        for (Course c : courses) {
        	Faculty f = facultyDao.getFacultyById(c.getFacultyId());
        	System.out.printf("%-10d %-25s %-15d %-20s\n",
        	        c.getCourseId(),
        	        c.getCourseName(),
        	        c.getFacultyId(),
        	        f.getName());
        }
    }
    
    public void viewEnrollmentDetails() {
    	enrollmentDAO.viewEnrollmentDetails();
    }
    
    public void DeleteEnrollments(int studentId,int courseId) {
    	enrollmentDAO.deleteEnrollment(studentId, courseId);
    }

    // ================= TIMETABLE =================
    public void addTimetable(int facultyId, String day, int period, String room, int courseId, String section) {

        if (period < 1 || period > 6) {
            System.out.println("Invalid period");
            return;
        }

        for (Timetable t : timetableDao.getAllTimetables()) {

            if (t.getFacultyId() == facultyId &&
                t.getDay().equalsIgnoreCase(day) &&
                t.getPeriod() == period) {
                System.out.println("Faculty busy");
                return;
            }

            if (t.getRoom().equalsIgnoreCase(room) &&
                t.getDay().equalsIgnoreCase(day) &&
                t.getPeriod() == period) {
                System.out.println("Room occupied");
                return;
            }

            if (t.getSection().equalsIgnoreCase(section) &&
                t.getDay().equalsIgnoreCase(day) &&
                t.getPeriod() == period) {
                System.out.println("Section already has class");
                return;
            }
        }

        timetableDao.addTimetable(facultyId, day, period, room, courseId, section);
    }

    // VIEW ALL
    public void viewTimetable() {

        List<Timetable> list = timetableDao.getAllTimetables();

        if (list.isEmpty()) {
            System.out.println("No timetable");
            return;
        }

        System.out.printf("%-5s %-10s %-7s %-10s %-10s %-10s\n",
                "ID", "Day", "Period", "Room", "Course", "Section");

        System.out.println("-----------------------------------------------------");

        for (Timetable t : list) {
            System.out.printf("%-5d %-10s %-7d %-10s %-10d %-10s\n",
                    t.gettimetableId(),
                    t.getDay(),
                    t.getPeriod(),
                    t.getRoom(),
                    t.getCourseId(),
                    t.getSection());
        }
    }

    // 🔥 VIEW BY DATE + SECTION
    public void viewByDate(String dateInput, String section) {

        try {
            LocalDate date = LocalDate.parse(dateInput);
            String day = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

            List<Timetable> list = timetableDao.getAllTimetables();

            System.out.println("\nDate: " + dateInput + " (" + day + ")");
            System.out.println("Section: " + section);

            boolean found = false;

            for (Timetable t : list) {

                if (t.getDay().equalsIgnoreCase(day) &&
                    t.getSection().equalsIgnoreCase(section)) {

                    System.out.println("Period: " + t.getPeriod() +
                            ", Room: " + t.getRoom() +
                            ", Course: " + t.getCourseId() +
                            ", Faculty: " + t.getFacultyId());

                    found = true;
                }
            }

            if (!found) {
                System.out.println("No data found");
            }

        } catch (Exception e) {
            System.out.println("Invalid date format (YYYY-MM-DD)");
        }
    }

    // ================= EXAM =================
    public void scheduleExam(int examId, int courseId, String examDate, int maxMark) {

        // 🔹 1. Validate exam ID
        if (examId <= 0) {
            System.out.println("Invalid Exam ID");
            return;
        }

        // 🔹 2. Validate course exists
        boolean courseExists = false;
        for (Course c : courseDao.getAllCourses()) {
            if (c.getCourseId() == courseId) {
                courseExists = true;
                break;
            }
        }

        if (!courseExists) {
            System.out.println("Course does not exist");
            return;
        }

        // 🔹 3. Validate exam date
        if (examDate == null || examDate.trim().isEmpty()) {
            System.out.println("Exam date cannot be empty");
            return;
        }

        // Format: YYYY-MM-DD
        if (!examDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invalid date format (Use YYYY-MM-DD)");
            return;
        }

        // 🔹 4. Check duplicate exam ID
        for (Exam e : examDao.getAllExams()) {
            if (e.getExamId() == examId) {
                System.out.println("Exam ID already exists");
                return;
            }
        }

        // 🔹 5. Conflict check (VERY IMPORTANT 🔥)

        for (Exam e : examDao.getAllExams()) {

            // Same course already has exam on same date
            if (e.getCourseId() == courseId &&
                e.getExamDate().equals(examDate)) {

                System.out.println("Exam already scheduled for this course on this date");
                return;
            }

            // 
            if (e.getCourseId() == courseId) {
                System.out.println("Exam already exists for this course");
                return;
            }
        }

        // 🔹 6. All valid → schedule exam
        examDao.scheduleExam( courseId, examDate, maxMark);

        System.out.println("Exam Scheduled Successfully");
    }

    public void viewSchedules() {
        List<Exam> exams = examDao.getAllExams();
        if(exams.isEmpty()) {
            System.out.println("No exams scheduled");
            return;
        }
        for (Exam e : exams) {
            System.out.println("Exam ID: " + e.getExamId() +
                    ", Course ID: " + e.getCourseId() +
                    ", Date: " + e.getExamDate());
        }
    }

    // ================= NOTIFICATION =================
    public void addNotification(String message, Date date, String role, Integer targetId) {

        if (message == null || message.trim().isEmpty()) {
            System.out.println("Message cannot be empty");
            return;
        }
        if (date == null) {
            System.out.println("Date cannot be null");
            return;
        }

        role = role.toUpperCase();
        if (!(role.equals("ALL") || role.equals("STUDENT") || role.equals("FACULTY") || role.equals("ADMIN"))) {
            System.out.println("Invalid role");
            return;
        }

        Notification notification = new Notification(message, date, role, targetId);
        notificationDao.addNotification(notification);
    }

    // View notifications
    public void viewNotifications() {
        List<Notification> list = notificationDao.getAllNotifications();
        if (list.isEmpty()) {
            System.out.println("No notifications available");
            return;
        }

        System.out.println("\n--- All Notifications (Admin View) ---");
        for (Notification n : list) {
            System.out.println(n.toString());
        }
    }

    // Delete notification
    public void deleteNotification(int notificationId) {
        notificationDao.deleteNotification(notificationId);
    }
    

    // ================= LIBRARY =================
    public void addBook(String title, String author, String isbn) {
        // 🔹 1. Validate Title
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Title cannot be empty");
            return;
        }

        // 🔹 2. Validate Author
        if (author == null || author.trim().isEmpty()) {
            System.out.println("Author cannot be empty");
            return;
        }

        // 🔹 3. Validate ISBN
        if (isbn == null || isbn.trim().isEmpty()) {
            System.out.println("ISBN cannot be empty");
            return;
        }

        // 🔹 4. ISBN format check (basic)
        if (!isbn.matches("\\d{10}|\\d{13}")) {
            System.out.println("Invalid ISBN (must be 10 or 13 digits)");
            return;
        }

        // 🔹 5. Duplicate ISBN check (VERY IMPORTANT 🔥)
        for (Book b : bookDAO.getAllBooks()) {
            if (b.getIsbn().equals(isbn)) {
                System.out.println("Book with same ISBN already exists");
                return;
            }
        }

        // 🔹 6. Trim clean data
        title = title.trim();
        author = author.trim();
        isbn = isbn.trim();

        // 🔹 7. Create Book

        bookDAO.addBook(title, author, isbn);

        System.out.println("Book added successfully");
    }

    public void removeBook(int id) {

        // 🔹 1. Validate ID
        if (id <= 0) {
            System.out.println("Invalid Book ID");
            return;
        }

        // 🔹 2. Check if book exists
        Book foundBook = null;

        for (Book b : bookDAO.getAllBooks()) {
            if (b.getBookId() == id) {
                foundBook = b;
                break;
            }
        }

        if (foundBook == null) {
            System.out.println("Book not found");
            return;
        }

        // 🔹 3. Optional: Check if book is issued
        if (!foundBook.isAvailable()) {
            System.out.println("Cannot remove: Book is currently issued");
            return;
        }

        // 🔹 4. Remove
        bookDAO.removeBook(id);

    }

    public void viewAllBooks() {
        List<Book> books = bookDAO.getAllBooks();
        if(books.isEmpty()) {
            System.out.println("No books available");
            return;
        }
        for (Book b : books) {
            System.out.println("ID: " + b.getBookId() +
                    " | Title: " + b.getTitle() +
                    " | Author: " + b.getAuthor() +
                    " | ISBN: " + b.getIsbn() +
                    " | Available: " + b.isAvailable());
        }
    }

    public void viewBorrowRecords() {
        List<BorrowRecord> records = borrowRecordDAO.getActiveRecords();
        if(records.isEmpty()) {
            System.out.println("No borrow records");
            return;
        }
        for (BorrowRecord r : records) {
            System.out.println("Record ID: " + r.getRecordId() +
                    " | Student ID: " + r.getStudentId() +
                    " | Book ID: " + r.getBookId() +
                    " | Borrow Date: " + r.getBorrowDate() +
                    " | Return Date: " + r.getReturnDate());
        }
    }
    
    //=================Result==================
 // Publish result with extra marks
    public void publishResult(int resultId, int extraMarks) {

        Result r = resultDAO.getResultById(resultId);
        if (r == null) {
            System.out.println("Result not found!");
            return;
        }

        // Add extra marks
        r.setMarks(r.getMarks() + extraMarks);

        // Update grade
        r.setGrade(calculateGrade(r.getMarks()));

        // Set published
        r.setPublished(true);

        // ✅ VERY IMPORTANT: SAVE TO DB
        resultDAO.updateResult(r);

        System.out.println("Result published successfully for Student ID " + r.getStudentId() +
                " in Exam ID " + r.getExamId() +
                " with marks: " + r.getMarks() +
                " and Grade: " + r.getGrade());
    }
    // View all results
    public void viewAllResults() {
        List<Result> results = resultDAO.getAllResults();

        if (results.isEmpty()) {
            System.out.println("No results available");
            return;
        }

        System.out.println("All Results:");
        System.out.println("---------------------------------------------------");
        for (Result r : results) {
            Student s = studentDao.getStudentById(r.getStudentId());

            // Get course via exam
            Exam e = examDao.getExamById(r.getExamId());
            Course c = (e != null) ? courseDao.getCourseById(e.getCourseId()) : null;

            String studentName = (s != null) ? s.getName() : "Unknown";
            String courseName = (c != null) ? c.getCourseName() : "Unknown";

            System.out.println("Result ID: " + r.getResultId() +
                    " | Student: " + studentName +
                    " | Course: " + courseName +
                    " | Marks: " + r.getMarks() +
                    " | Grade: " + r.getGrade() +
                    " | Published: " + (r.isPublished() ? "Yes" : "No"));
        }
        System.out.println("---------------------------------------------------");
    }

    // Grade calculation helper
    private String calculateGrade(int marks) {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B+";
        else if (marks >= 60) return "B";
        else if (marks >= 50) return "C";
        else return "F";
    }
	
}
   






    
    
