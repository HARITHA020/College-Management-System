package service;

import java.util.Date;
import java.util.List;

import dao.*;
import model.*;

public class AdminService {

    private AdminDAO adminDao = new AdminDAO();
    private CourseDAO courseDao = new CourseDAO();
    private TimetableDAO timetableDao = new TimetableDAO();
    private ExamDAO examDao = new ExamDAO();
    private NotificationDAO notificationDao = new NotificationDAO();
    private BookDAO bookDAO = new BookDAO();
    private BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
    private ResultDAO resultDAO = new ResultDAO();
    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private StudentDAO studentDao= new StudentDAO();
    private FacultyDAO facultyDao= new FacultyDAO();

    // ================= ADMIN =================
    public void addAdmin(int id, String name, String password, String dob, String contact) {
        // Validate basic fields
        if(id <= 0 || name == null || name.trim().isEmpty() || 
           password == null || password.trim().isEmpty() || 
           dob == null || dob.trim().isEmpty() || 
           contact == null || contact.trim().isEmpty()) {
            System.out.println("Invalid Admin data");
            return;
        }

        // check duplicate
        for(Administrator a : adminDao.getAllAdmins()) {
            if(a.getId() == id) {
                System.out.println("Admin with this ID already exists");
                return;
            }
        }

        // call DAO to add admin with new fields
        adminDao.addAdmin(id, name, password, dob, contact);
        System.out.println("Admin Added Successfully");
    }

    public void updateAdmin(int id, String name, String password, String dob, String contact) {
        // 🔹 1. Validate ID
        if (id <= 0) {
            System.out.println("Invalid Admin ID");
            return;
        }

        // 🔹 2. Validate name
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Admin name cannot be empty");
            return;
        }

        // 🔹 3. Validate password
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Password cannot be empty");
            return;
        }

        if (password.length() < 4) {
            System.out.println("Password must be at least 4 characters");
            return;
        }

        // 🔹 4. Validate DOB & Contact
        if (dob == null || dob.trim().isEmpty()) {
            System.out.println("DOB cannot be empty");
            return;
        }

        if (contact == null || contact.trim().isEmpty()) {
            System.out.println("Contact cannot be empty");
            return;
        }

        // 🔹 5. Check if admin exists
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

        // 🔹 6. Update
        adminDao.updateAdmin(id, name, password, dob, contact);
        System.out.println("Admin Updated Successfully");
    }

    public void deleteAdmin(int id) {

        // 🔹 1. Validate ID
        if (id <= 0) {
            System.out.println("Invalid Admin ID");
            return;
        }

        // 🔹 2. Check if admin exists
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

        // 🔹 3. Delete
        adminDao.deleteAdmin(id);
        System.out.println("Admin Deleted Successfully");
    }
    public void viewAdmins() {
        List<Administrator> admins = adminDao.getAllAdmins();
        if(admins.isEmpty()) {
            System.out.println("No admins available");
            return;
        }
        for (Administrator admin : admins) {
            System.out.println("ID: " + admin.getId() + ", Name: " + admin.getName());
        }
    }

    // ================= COURSE =================
    public void addCourse(int id, String name) {
        if(id <= 0 || name == null || name.trim().isEmpty()) {
            System.out.println("Invalid course data");
            return;
        }
        courseDao.addCourse(id, name);
        System.out.println("Course Added Successfully");
    }

    public void assignCourse(int courseId, int facultyId) {

        // 🔹 Step 1: Validate course exists
        boolean courseExists = false;
        for (Course c : courseDao.getAllCourses()) {
            if (c.getcourseId() == courseId) {
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
            if (c.getcourseId() == courseId) {
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
        if(courses.isEmpty()) {
            System.out.println("No courses available");
            return;
        }
        for (Course c : courses) {
            System.out.println("ID: " + c.getcourseId() +
                    ", Name: " + c.getcourseName() +
                    ", Faculty ID: " + c.getFacultyid());
        }
    }

    // ================= TIMETABLE =================
	public void addTimetable(int id, int facultyId, String day, String time, String room, int courseId,
			String section) {

		// 🔹 1. Validate ID
		if (id <= 0) {
			System.out.println("Invalid Timetable ID");
			return;
		}

        // 🔹 2. Validate faculty exists
		boolean facultyExists = false;
		for (Faculty f : facultyDao.getAllFaculty()) {
			if (f.getId() == facultyId) {
				facultyExists = true;
				break;
			}
		}

		if (!facultyExists) {
			System.out.println("Faculty does not exist");
			return;
		}

        // 🔹 3. Validate course exists
		boolean courseExists = false;
		for (Course c : courseDao.getAllCourses()) {
			if (c.getcourseId() == courseId) {
				courseExists = true;
				break;
			}
		}

		if (!courseExists) {
			System.out.println("Course does not exist");
			return;
		}

        // 🔹 4. Validate day
		if (day == null || day.trim().isEmpty()) {
			System.out.println("Day cannot be empty");
			return;
		}

		String d = day.toLowerCase();
		if (!(d.equals("monday") || d.equals("tuesday") || d.equals("wednesday") || d.equals("thursday")
				|| d.equals("friday") || d.equals("saturday"))) {
			System.out.println("Invalid day");
			return;
		}

        // 🔹 5. Validate time
		if (time == null || time.trim().isEmpty()) {
			System.out.println("Time cannot be empty");
			return;
		}

        // (Simple format check: 10:00-11:00)
		if (!time.matches("\\d{2}:\\d{2}-\\d{2}:\\d{2}")) {
			System.out.println("Invalid time format (Use HH:MM-HH:MM)");
			return;
		}

        // 🔹 6. Validate room
		if (room == null || room.trim().isEmpty()) {
			System.out.println("Room cannot be empty");
			return;
		}

        // 🔹 7. Validate section
		if (section == null || section.trim().isEmpty()) {
			System.out.println("Section cannot be empty");
			return;
		}

        // 🔹 8. Check duplicate timetable ID
		for (Timetable t : timetableDao.getAllTimetables()) {
			if (t.gettimetableId() == id) {
				System.out.println("Timetable ID already exists");
				return;
			}
		}

        // 🔹 9. Conflict check (VERY IMPORTANT 🔥)

		for (Timetable t : timetableDao.getAllTimetables()) {

       // Same faculty same time
			if (t.getFacultyId() == facultyId && t.getDay().equalsIgnoreCase(day) && t.getTime().equals(time)) {

				System.out.println("Faculty already has class at this time");
				return;
			}

       // Same room same time
			if (t.getRoom().equalsIgnoreCase(room) && t.getDay().equalsIgnoreCase(day) && t.getTime().equals(time)) {

				System.out.println("Room already occupied at this time");
				return;
			}

       // Same section same time
			if (t.getSection().equalsIgnoreCase(section) && t.getDay().equalsIgnoreCase(day)
					&& t.getTime().equals(time)) {

				System.out.println("Section already has class at this time");
				return;
			}
		}

        // 🔹 10. All valid → add timetable
		timetableDao.addTimetable(id, facultyId, day, time, room, courseId, section);
		System.out.println("Timetable Added Successfully");
}

    public void viewTimetable() {
        List<Timetable> list = timetableDao.getAllTimetables();
        if(list.isEmpty()) {
            System.out.println("No timetable available");
            return;
        }
        for (Timetable t : list) {
            System.out.println("ID: " + t.gettimetableId() +
                    ", Day: " + t.getDay() +
                    ", Time: " + t.getTime() +
                    ", Room: " + t.getRoom() +
                    ", Course ID: " + t.getCourseId());
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
            if (c.getcourseId() == courseId) {
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
        examDao.addExam(examId, courseId, examDate, maxMark);

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
    public void addNotification(int id, String message, Date date, String role, Integer targetId) {

        // 🔹 Validate ID
        if (id <= 0) {
            System.out.println("Invalid ID");
            return;
        }

        // 🔹 Validate message
        if (message == null || message.trim().isEmpty()) {
            System.out.println("Message cannot be empty");
            return;
        }

        // 🔹 Validate date
        if (date == null) {
            System.out.println("Date cannot be null");
            return;
        }

        // 🔹 Validate role
        if (!(role.equalsIgnoreCase("ALL") ||
              role.equalsIgnoreCase("STUDENT") ||
              role.equalsIgnoreCase("FACULTY"))) {
            System.out.println("Invalid role");
            return;
        }

        // 🔹 Duplicate ID check
        for (Notification n : notificationDao.getAllNotifications()) {
            if (n.getNotificationId() == id) {
                System.out.println("Notification ID already exists");
                return;
            }
        }

        Notification notification = new Notification(id, message, date, role, targetId);

        notificationDao.addNotification(notification);

        System.out.println("Notification added successfully");
    }

    public void viewNotifications() {

        List<Notification> list = notificationDao.getAllNotifications();

        if (list.isEmpty()) {
            System.out.println("No notifications available");
            return;
        }

        System.out.println("\n--- All Notifications (Admin View) ---");

        for (Notification n : list) {

            System.out.println(
                "ID: " + n.getNotificationId() +
                " | Message: " + n.getMessage() +
                " | Date: " + n.getDate() +
                " | Role: " + n.getTargetRole() +
                " | Target ID: " + 
                (n.getTargetId() == null ? "ALL" : n.getTargetId())
            );
        }
    }

    // ================= LIBRARY =================
    public void addBook(int id, String title, String author, String isbn) {

        // 🔹 1. Validate ID
        if (id <= 0) {
            System.out.println("Invalid Book ID");
            return;
        }

        // 🔹 2. Check duplicate ID
        for (Book b : bookDAO.getAllBooks()) {
            if (b.getBookId() == id) {
                System.out.println("Book ID already exists");
                return;
            }
        }

        // 🔹 3. Validate Title
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Title cannot be empty");
            return;
        }

        // 🔹 4. Validate Author
        if (author == null || author.trim().isEmpty()) {
            System.out.println("Author cannot be empty");
            return;
        }

        // 🔹 5. Validate ISBN
        if (isbn == null || isbn.trim().isEmpty()) {
            System.out.println("ISBN cannot be empty");
            return;
        }

        // 🔹 6. ISBN format check (basic)
        if (!isbn.matches("\\d{10}|\\d{13}")) {
            System.out.println("Invalid ISBN (must be 10 or 13 digits)");
            return;
        }

        // 🔹 7. Duplicate ISBN check (VERY IMPORTANT 🔥)
        for (Book b : bookDAO.getAllBooks()) {
            if (b.getIsbn().equals(isbn)) {
                System.out.println("Book with same ISBN already exists");
                return;
            }
        }

        // 🔹 8. Trim clean data
        title = title.trim();
        author = author.trim();
        isbn = isbn.trim();

        // 🔹 9. Create Book
        Book book = new Book(id, title, author, isbn, true);

        bookDAO.addBook(book);

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
        if (!foundBook.isAvailability()) {
            System.out.println("Cannot remove: Book is currently issued");
            return;
        }

        // 🔹 4. Remove
        bookDAO.removeBook(id);

        System.out.println("Book removed successfully");
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
                    " | Available: " + b.isAvailability());
        }
    }

    public void viewBorrowRecords() {
        List<BorrowRecord> records = borrowRecordDAO.getAllRecords();
        if(records.isEmpty()) {
            System.out.println("No borrow records");
            return;
        }
        for (BorrowRecord r : records) {
            System.out.println("Record ID: " + r.getRecordId() +
                    " | Student ID: " + r.getStudentId() +
                    " | Faculty ID:  "+r.getFacultyId()+
                    " | Book ID: " + r.getBookId() +
                    " | Borrow Date: " + r.getBorrowDate() +
                    " | Return Date: " + r.getReturnDate());
        }
    }
    
    //=================Result==================
    public void publishResult(int resultId, int extraMarks) {

        Result r = resultDAO.getResultById(resultId);
        if (r == null) {
            System.out.println("Result not found!");
            return;
        }

        // Add extra marks
        r.setMarks(r.getMarks() + extraMarks);

        // Update grade based on new marks
        r.setGrade(calculateGrade(r.getMarks()));

        r.setPublished(true);
        System.out.println("Result published successfully for Student ID " + r.getStudentId() +
                " in Course ID " + r.getCourseId() +
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
            Course c = courseDao.getCourseById(r.getCourseId());
            String studentName = (s != null) ? s.getName() : "Unknown";
            String courseName = (c != null) ? c.getcourseName() : "Unknown";

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
    
    
