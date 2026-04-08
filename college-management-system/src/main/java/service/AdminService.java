/*
 * Author : Haritha
 * This service method will helps to send the input details to the dao which is to store the details in the database
 */
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
        //validating the all input fields
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Email cannot be empty");
            return;
      
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	    if (!email.matches(emailRegex)) {
	        System.out.println("Invalid email format");
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
        // getting the user id from the user data which match the particular email and password and role
        int userId = userDAO.createUser(email, password, "ADMIN");

        if (userId == -1) {
            System.out.println("User creation failed");
            return;
        }
         // sending the admin details to DAO for storing the admin details in database
        adminDao.addAdmin(name, dob, contact, userId);

        
    }

    // UPDATE ADMIN 
    public void updateAdmin(int adminId, String field, String value) {
        // validating the admin id
        if (adminId <= 0) {
            System.out.println("Invalid Admin ID");
            return;
        }

        boolean updated = adminDao.updateAdminField(adminId, field, value);

        if (updated) {
            System.out.println("✅ Admin updated successfully");
        } else {
            System.out.println("❌ Update failed");
        }
    }

    // DELETE ADMIN
    public void deleteAdmin(int id) {

        if (id <= 0) {
            System.out.println("Invalid Admin ID");
            return;
        }

        //  1. Get user_id from admin table
        int userId = adminDao.getUserIdByAdminId(id);

        if (userId == -1) {
            System.out.println("Admin not found");
            return;
        }

        //  2. Delete USER (not admin)
        userDAO.deleteUser(userId);

    }
    //  VIEW ADMIN
    public void viewAdmins() {
        // getting all the admin details from the backend 
        List<Administrator> admins = adminDao.getAllAdmins();

        if (admins.isEmpty()) {
            System.out.println("No admins available");
            return;
        }
        
        System.out.printf("%-5s %-20s %-15s %-15s\n",
                "ID", "Name", "DOB", "Contact");

        System.out.println("------------------------------------------------------------");
        // printing the admin details
        for (Administrator admin : admins) {
            System.out.printf("%-5d %-20s %-15s %-15s\n",
                    admin.getId(),
                    admin.getName(),
                    admin.getDob(),
                    admin.getContact());
        }
    }


    // ================= COURSE =================
    //ADD COURSE
	public void addCourse(String name, int credits, String duration, String department, int facultyId,
			String description, int semester) {

       //Empty validation
		if (name == null || name.trim().isEmpty()) {
			System.out.println("Course name cannot be empty");
			return;
		}

      //Duplicate check
		if (courseDao.isCourseExists(name, department, semester)) {
			System.out.println("Course already exists for this department and semester");
			return;
		}

       //Insert if valid
		courseDao.addCourse(name, credits, duration, department, facultyId, description, semester);

		System.out.println("Course added successfully");
	}

    // UPDATE COURSE
    public void updateCourse(int courseId, String field, String value) {

        // Validate course ID
        Course existing = courseDao.getCourseById(courseId);
        if (existing == null) {
            System.out.println("❌ Course ID " + courseId + " does not exist.");
            return;
        }

        boolean updated = courseDao.updateCourseField(courseId, field, value);

        if (updated) {
            System.out.println("✅ Course updated successfully");
        } else {
            System.out.println("❌ Update failed");
        }
    }

    //DELETE COURSE 
    public void deleteCourse(int courseId) {

        //check if course exists first
        Course existing = courseDao.getCourseById(courseId);
        if (existing == null) {
            System.out.println("❌ Course ID " + courseId + " does not exist.");
            return;
        }

        // Delete course
        courseDao.deleteCourse(courseId);
    }

    // DELETE ENROLLMENT
    public void deleteEnrollment(int studentId, int courseId) {

        // validate student and course
        Student s = studentDao.getStudentById(studentId);
        Course c = courseDao.getCourseById(courseId);

        if (s == null) {
            System.out.println("❌ Student ID " + studentId + " does not exist.");
            return;
        }

        if (c == null) {
            System.out.println("❌ Course ID " + courseId + " does not exist.");
            return;
        }

        // Call DAO to remove enrollment
        enrollmentDAO.deleteEnrollment(studentId, courseId);
    }

    public void assignCourse(int courseId, int facultyId) {

        // ✅ Validate course exists
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
        if (courseDao.isCourseAlreadyAssigned(courseId, facultyId)) {
            System.out.println("Error: Course already assigned to this faculty.");
            return;
        }
        courseDao.assignCourse(courseId, facultyId);
        System.out.println("Course Assigned Successfully");
    }
    
    public void assignStudentToCourse(int studentId, int courseId) {

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

        if (enrollmentDAO.isAlreadyEnrolled(studentId, courseId)) {
            System.out.println("Error: Student already enrolled in this course.");
            return;
        }

        enrollmentDAO.enrollStudent(studentId, courseId);
        System.out.println("Student enrolled successfully");
    }

    public void viewCourses() {
       // get course from the dao
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
    public String getTimeByPeriod(int period) {
        // setting the peroids time which calls based on the iteration
        switch (period) {
            case 1: return "9:00 - 10:00";
            case 2: return "10:00 - 11:00";
            case 3: return "11:00 - 12:00";
            case 4: return "12:00 - 1:00";
            case 5: return "2:00 - 3:00";
            case 6: return "3:00 - 4:00";
            default: return "Invalid";
        }
    }
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

        System.out.printf("%-5s %-10s %-7s %-15s %-10s %-10s %-10s\n",
                "ID", "Day", "Period", "Time", "Room", "Course", "Section");

        System.out.println("---------------------------------------------------------------------");

        for (Timetable t : list) {
            System.out.printf("%-5d %-10s %-7d %-15s %-10s %-10d %-10s\n",
                    t.gettimetableId(),
                    t.getDay(),
                    t.getPeriod(),
                    getTimeByPeriod(t.getPeriod()), // ✅ ADDED
                    t.getRoom(),
                    t.getCourseId(),
                    t.getSection());
        }
    }

    //VIEW BY DATE + SECTION
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

                    System.out.println(
                            "Period: " + t.getPeriod() +
                            " (" + getTimeByPeriod(t.getPeriod()) + ")" + 
                            ", Room: " + t.getRoom() +
                            ", Course: " + t.getCourseId() +
                            ", Faculty: " + t.getFacultyId()
                    );

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
    public void updateTimetable(int timetableId, String field, String value) {

        if (timetableId <= 0) {
            System.out.println("Invalid Timetable ID");
            return;
        }

        boolean updated = timetableDao.updateTimetableField(timetableId, field, value);

        if (updated) {
            System.out.println("✅ Timetable updated successfully");
        } else {
            System.out.println("❌ Update failed");
        }
    }
    public void deleteTimetable(int timetableId) {

        if (timetableId <= 0) {
            System.out.println("Invalid Timetable ID");
            return;
        }

        boolean deleted = timetableDao.deleteTimetableById(timetableId);

        if (deleted) {
            System.out.println("✅ Timetable deleted successfully");
        } else {
            System.out.println("❌ Timetable not found");
        }
    }

    // ================= EXAM =================
    public void scheduleExam(int courseId, String examDate, int maxMark) {


        //  Validate course exists
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

        //  Validate exam date
        if (examDate == null || examDate.trim().isEmpty()) {
            System.out.println("Exam date cannot be empty");
            return;
        }

        // Format: YYYY-MM-DD
        if (!examDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invalid date format (Use YYYY-MM-DD)");
            return;
        }

      
        // Conflict check (VERY IMPORTANT 🔥)

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

        // All valid → schedule exam
        examDao.scheduleExam( courseId, examDate, maxMark);

        System.out.println("Exam Scheduled Successfully");
    }
    
     // DELETE
    public void deleteExam(int examId) {

        if (examId <= 0) {
            System.out.println("Invalid Exam ID");
            return;
        }

        boolean deleted = examDao.deleteExamById(examId);

        if (deleted) {
            System.out.println("✅ Exam deleted successfully");
        } else {
            System.out.println("❌ Exam not found");
        }
    }


    // UPDATE
    public void updateExam(int examId, String field, String value) {

        if (examId <= 0) {
            System.out.println("Invalid Exam ID");
            return;
        }

        boolean updated = examDao.updateExamField(examId, field, value);

        if (updated) {
            System.out.println("✅ Exam updated successfully");
        } else {
            System.out.println("❌ Update failed");
        }
    }

    public void viewSchedules() {

        List<Exam> exams = examDao.getAllExams();

        if (exams.isEmpty()) {
            System.out.println("No exams scheduled");
            return;
        }

        System.out.println("\n--- Exam Schedule ---");
        System.out.printf("%-10s %-15s %-25s %-25s\n",
                "Exam ID", "Course ID","Course Name", "Exam Date");
        System.out.println("------------------------------------------------------------------");
        for (Exam e : exams) {
            Course c = courseDao.getCourseById(e.getCourseId()); 
            System.out.printf("%-10d %-15d %-25s %-25s\n",
                    e.getExamId(),
                    e.getCourseId(),
                    c.getCourseName(),  
                    e.getExamDate());
        }
        System.out.println("------------------------------------------------------------------");
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
        System.out.printf("%-5s %-30s %-15s %-10s %-20s\n",
                "ID", "Message", "Role", "TargetID", "Date");

        System.out.println("------------------------------------------------------------------------------------------");
        for (Notification n : list) {
            // trim long message
            String msg = n.getMessage();
            if (msg.length() > 28) {
                msg = msg.substring(0, 28) + "...";
            }

            System.out.printf("%-5d %-30s %-15s %-10d %-20s\n",
                    n.getNotificationId(),
                    msg,
                    n.getTargetRole(),
                    n.getTargetId(),
                    n.getDate());
        }
    }

    // Delete notification
    public void deleteNotification(int notificationId) {
        notificationDao.deleteNotification(notificationId);
    }
   
    // ================= LIBRARY =================
    public void addBook(String title, String author, String isbn, int totalCopies ) {
        //Validate Title
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Title cannot be empty");
            return;
        }

        // Validate Author
        if (author == null || author.trim().isEmpty()) {
            System.out.println("Author cannot be empty");
            return;
        }

        // Validate ISBN
        if (isbn == null || isbn.trim().isEmpty()) {
            System.out.println("ISBN cannot be empty");
            return;
        }

        //  ISBN format check (basic)
        if (!isbn.matches("\\d{10}|\\d{13}")) {
            System.out.println("Invalid ISBN (must be 10 or 13 digits)");
            return;
        }

        // Duplicate ISBN check (VERY IMPORTANT 🔥)
        for (Book b : bookDAO.getAllBooks()) {
            if (b.getIsbn().equals(isbn)) {
                System.out.println("Book with same ISBN already exists");
                return;
            }
        }

        //  Trim clean data
        title = title.trim();
        author = author.trim();
        isbn = isbn.trim();

        //available check 
        if (totalCopies <= 0) {
            System.out.println("Invalid copies");
            return;
        }

        bookDAO.addBook(title, author, isbn,totalCopies, totalCopies);

        System.out.println("Book added successfully");
    }

    public void updateBook(int bookId, String field, String value) {

        boolean updated = bookDAO.updateBookField(bookId, field, value);

        if (updated) {
            System.out.println("✅ Book updated successfully");
        } else {
            System.out.println("❌ Update failed");
        }
    }
    public void removeBook(int id) {

        // Validate ID
        if (id <= 0) {
            System.out.println("Invalid Book ID");
            return;
        }

        // Check if book exists
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

        bookDAO.removeBook(id);

    }

    public void viewAllBooks() {

        List<Book> books = bookDAO.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books available");
            return;
        }

        System.out.println("\n--- Library Books ---");
        System.out.printf("%-5s %-25s %-20s %-20s %-10s %-10s\n",
                "ID", "Title", "Author", "ISBN", "Total", "Available");

        System.out.println("-----------------------------------------------------------------------------------------------");
        for (Book b : books) {
            String title = b.getTitle();
            if (title.length() > 10) {
                title = title.substring(0, 10) + "...";
            }
            System.out.printf("%-5d %-25s %-20s %-20s %-10d %-10d\n",
                    b.getBookId(),
                    title,   
                    b.getAuthor(),
                    b.getIsbn(),
                    b.getTotalCopies(),
                    b.getAvailableCopies()
            );
        }
        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    public void viewBorrowRecords() {

        List<BorrowRecord> records = borrowRecordDAO.getActiveRecords();

        if (records.isEmpty()) {
            System.out.println("No borrow records");
            return;
        }

        System.out.println("\n--- Borrow Records ---");

        System.out.printf("%-10s %-12s %-12s %-10s %-20s %-20s\n",
                "Record ID", "Student ID", "Faculty ID", "Book ID", "Borrow Date", "Return Date");
        System.out.println("------------------------------------------------------------------------------------------");

        // ✅ Rows
        for (BorrowRecord r : records) {

            String returnDate = (r.getReturnDate() != null)
                    ? r.getReturnDate().toString()
                    : "Not Returned";

            System.out.printf("%-10d %-12s %-12s %-10d %-20s %-20s\n",
                    r.getRecordId(),
                    (r.getStudentId() != null ? r.getStudentId() : "-"),
                    (r.getFacultyId() != null ? r.getFacultyId() : "-"),
                    r.getBookId(),
                    r.getBorrowDate(),
                    returnDate
            );
        }
    }
    
    //=================Result==================

    public void publishResult(int resultId, int extraMarks) {

        Result r = resultDAO.getResultById(resultId);

        if (r == null) {
            System.out.println("Result not found!");
            return;
        }

        //  PREVENT RE-PUBLISH
        if (r.isPublished()) {
            System.out.println("❌ Result is already published");
            return;
        }

        // Add extra marks only if > 0
        if (extraMarks > 0) {
            r.setMarks(r.getMarks() + extraMarks);
        }

        // Update grade
        r.setGrade(calculateGrade(r.getMarks()));

        // Set published
        r.setPublished(true);

        // Save to DB
        resultDAO.updateResult(r);

        System.out.println("✅ Result published successfully for Student ID " + r.getStudentId() +
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

        System.out.println("\n--- All Results ---");
        System.out.printf("%-8s %-20s %-20s %-10s %-8s %-12s\n",
                "ResID", "Student", "Course", "Marks", "Grade", "Published");

        System.out.println("------------------------------------------------------------------------------------------");

        for (Result r : results) {

            Student s = studentDao.getStudentById(r.getStudentId());

            // Get course via exam
            Exam e = examDao.getExamById(r.getExamId());
            Course c = (e != null) ? courseDao.getCourseById(e.getCourseId()) : null;

            String studentName = (s != null) ? s.getName() : "Unknown";
            String courseName = (c != null) ? c.getCourseName() : "Unknown";

            // Trim long names (optional)
            if (studentName.length() > 18) {
                studentName = studentName.substring(0, 18) + "...";
            }
            if (courseName.length() > 18) {
                courseName = courseName.substring(0, 18) + "...";
            }
            System.out.printf("%-8d %-20s %-20s %-10d %-8s %-12s\n",
                    r.getResultId(),
                    studentName,
                    courseName,
                    r.getMarks(),
                    r.getGrade(),
                    (r.isPublished() ? "YES" : "NO"));
        }

        System.out.println("------------------------------------------------------------------------------------------");
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
   






    
    
