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

    // ================= ADMIN =================
    public void addAdmin(int id, String name, String password) {
        if(id <= 0 || name == null || name.trim().isEmpty() || password == null || password.trim().isEmpty()) {
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

        adminDao.addAdmin(id, name, password);
        System.out.println("Admin Added Successfully");
    }

    public void updateAdmin(int id, String name, String password) {
        adminDao.updateAdmin(id, name, password);
        System.out.println("Admin Updated Successfully");
    }

    public void deleteAdmin(int id) {
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
    public void addTimetable(int id, int facultyId, String day, String time, String room, int courseId, String section) {
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
    public void scheduleExam(int examId, int courseId, String examDate) {
        examDao.addExam(examId, courseId, examDate);
        System.out.println("Exam Scheduled Successfully");
    }

    public void viewSchedules() {
        List<Exam> exams = examDao.getAllExam();
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
    public void addNotification(int id, String message, Date date) {
        if(message == null || message.trim().isEmpty()) {
            System.out.println("Notification message cannot be empty");
            return;
        }
        Notification notification = new Notification(id, message, date);
        notificationDao.addNotification(notification);
        System.out.println("Notification added successfully");
    }

    public void viewNotifications() {
        List<Notification> list = notificationDao.getAllNotifications();
        if(list.isEmpty()) {
            System.out.println("No notifications available");
            return;
        }
        System.out.println("\n--- Notifications ---");
        for (Notification n : list) {
            System.out.println("ID: " + n.getNotificationId() +
                    " | Message: " + n.getMessage() +
                    " | Date: " + n.getDate());
        }
    }

    // ================= LIBRARY =================
    public void addBook(int id, String title, String author, String isbn) {
        if(title == null || title.trim().isEmpty() || author == null || author.trim().isEmpty()) {
            System.out.println("Invalid book data");
            return;
        }
        Book book = new Book(id, title, author, isbn, true);
        bookDAO.addBook(book);
        System.out.println("Book added successfully");
    }

    public void removeBook(int id) {
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
    public void publishResult(int resultId) {

        boolean found = false;

        for (Result r : resultDAO.getAllResults()) {

            if (r.getResultId() == resultId) {

                r.setPublished(true);
                System.out.println("Result published successfully!");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Result not found!");
        }
    }
    public void viewAllResults() {

        List<Result> results = resultDAO.getAllResults();

        if(results.isEmpty()) {
            System.out.println("No results available");
            return;
        }

        for(Result r : results) {
            System.out.println("ID: " + r.getResultId() +
                    " | Student: " + r.getStudentId() +
                    " | Course: " + r.getCourseId() +
                    " | Marks: " + r.getMarks() +
                    " | Grade: " + r.getGrade() +
                    " | Published: " + r.isPublished());
        }
    }
    
    
}