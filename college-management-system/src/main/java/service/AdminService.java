package service;

import java.util.List;
import dao.AdminDAO;
import dao.BookDAO;
import dao.BorrowRecordDAO;
import dao.CourseDAO;
import dao.ExamDAO;
import dao.NotificationDAO;
import dao.TimetableDAO;
import model.*;

public class AdminService {

    private CourseDAO courseDao = new CourseDAO();
    private TimetableDAO timetableDao = new TimetableDAO();
    private AdminDAO adminDao = new AdminDAO();
    private ExamDAO examDao = new ExamDAO();
    private NotificationDAO  notificationDao=new NotificationDAO();
    private BookDAO bookDAO = new BookDAO();
    private BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();

    public void addAdmin(int id, String name, String password) {
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

    public void addCourse(int id, String name) {
        courseDao.addCourse(id, name);
        System.out.println("Course Added Successfully");
    }

    public void assignCourse(int courseId, int facultyId) {
        courseDao.assignCourse(courseId, facultyId); // FIXED
        System.out.println("Course Assigned Successfully");
    }

    public void addTimetable(int id, String day, String time, String room, int courseId) {
        timetableDao.addTimetable(id, day, time, room, courseId);
        System.out.println("Timetable Added Successfully");
    }

    public void scheduleExam(int examId, int courseId, String examDate) {
        examDao.addExam(examId, courseId, examDate);
        System.out.println("Exam Scheduled Successfully");
    }
    
    public void addNotification(int id, String message, java.util.Date date) {
        Notification notification = new Notification(id, message, date);
        notificationDao.addNotification(notification);
        System.out.println("Notification added successfully");
    }

    public void addBook(int id, String title, String author) {
        Book book = new Book(id, title, author, author, true);
        bookDAO.addBook(book);
        System.out.println("Book added successfully");
    }
    
    public void removeBook(int id) {
        bookDAO.removeBook(id);
        System.out.println("Book removed successfully");
    }
    
    public void viewAdmins() {
        List<Administrator> admins = adminDao.getAllAdmins();
        for (Administrator admin : admins) {
            System.out.println("ID: " + admin.getId() + ", Name: " + admin.getName());
        }
    }

    public void viewCourse() {
        List<Course> courses = courseDao.getAllCourses();
        for (Course c : courses) {
            System.out.println("ID: " + c.getcourseId() +
                    ", Name: " + c.getcourseId() +
                    ", Faculty: " + c.getFacultyid());
        }
    }

    public void viewTimetable() {
        List<Timetable> list = timetableDao.getAllTimetables();
        for (Timetable t : list) {
            System.out.println("ID: " + t.getTime() +
                    ", Day: " + t.getDay() +
                    ", Time: " + t.getTime());
        }
    }

    public void viewSchedules() {
        List<Exam> exams = examDao.getAllExam();
        for (Exam e : exams) {
            System.out.println("Exam ID: " + e.getExamId() +
                    ", Course ID: " + e.getCourseId() +
                    ", Date: " + e.getExamDate());
        }
    }
    
    public void viewNotifications() {
        List<Notification> list = notificationDao.getAllNotifications();

        if (list.isEmpty()) {
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
    
    public void viewAllBooks() {
        List<Book> books = bookDAO.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books available");
            return;
        }

        for (Book b : books) {
            System.out.println("ID: " + b.getBookId() +
                    " | Title: " + b.getTitle() +
                    " | Author: " + b.getAuthor() +
                    " | Available: " + b.isAvailability());
        }
    }
    
    public void viewBorrowRecords() {
        List<BorrowRecord> records = borrowRecordDAO.getAllRecords();

        if (records.isEmpty()) {
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
}