package service;

import java.util.List;
import dao.*;
import model.*;

public class StudentService {

    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private TimetableDAO timetableDAO;
    private ExamDAO examDAO;
    private BookDAO bookDAO;
    private BorrowRecordDAO borrowRecordDAO;
    private NotificationDAO notificationDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
        this.courseDAO = new CourseDAO();
        this.timetableDAO = new TimetableDAO();
        this.examDAO = new ExamDAO();
        this.bookDAO = new BookDAO();
        this.borrowRecordDAO = new BorrowRecordDAO();
        this.notificationDAO = new NotificationDAO();
    }
   public void addStudent(int id, String name, String department) {
	   Student student = new Student(id, name, department);
	   studentDAO.addStudent(student); 
	   System.out.println("Student added successfully"); 
   }
   
   public void updateStudent(int id, String name, String department) { 
	   studentDAO.updateStudent(id, name, department); 
   }
  
   public void deleteStudent(int id) { 
	   studentDAO.deleteStudent(id); 
   }
   
   public void viewStudents() {
	   
	   List<Student> students = studentDAO.getAllStudents(); 
	   for (Student s : students) {
		   System.out.println(s); 
		   }
	   }
    // ================= COURSES =================
    public void viewCourses() {
        List<Course> courses = courseDAO.getAllCourses();

        for (Course c : courses) {
            System.out.println("ID: " + c.getcourseId() +
                    " | Name: " + c.getcourseName());
        }
    }

    // ================= TIMETABLE =================
    public void viewTimetable() {
        List<Timetable> list = timetableDAO.getAllTimetables();

        for (Timetable t : list) {
            System.out.println(t.getDay() + " | " + t.getTime());
        }
    }

    // ================= MARKS =================
    public void viewMarks() {
        List<Exam> exams = examDAO.getAllExam();

        for (Exam e : exams) {
            System.out.println("Exam: " + e.getExamId() +
                    " | Date: " + e.getExamDate());
        }
    }

    // ================= NOTIFICATIONS =================
    public void viewNotifications() {

        List<Notification> list = notificationDAO.getAllNotifications();

        if (list.isEmpty()) {
            System.out.println("No notifications available");
            return;
        }

        for (Notification n : list) {
            System.out.println("ID: " + n.getNotificationId() +
                    " | Message: " + n.getMessage() +
                    " | Date: " + n.getDate());
        }
    }
    //===========view Attendance=========
    public void viewAttendance() {
    	System.out.println("upcomming features.");
    }

    // ================= LIBRARY =================

    // 🔍 Search Book
    public void searchBook(String keyword) {

        List<Book> books = bookDAO.getAllBooks();
        boolean found = false;

        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                b.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {

                System.out.println("ID: " + b.getBookId() +
                        " | Title: " + b.getTitle() +
                        " | Author: " + b.getAuthor() +
                        " | Available: " + b.isAvailability());

                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found");
        }
    }

    // 📚 Borrow Book
    public void borrowBook(int studentId, int bookId) {

        Book book = null;

        for (Book b : bookDAO.getAllBooks()) {
            if (b.getBookId() == bookId) {
                book = b;
                break;
            }
        }

        if (book == null) {
            System.out.println("Book not found");
            return;
        }

        if (!book.isAvailability()) {
            System.out.println("Book already borrowed");
            return;
        }

        book.setAvailability(false);

        int recordId = borrowRecordDAO.getAllRecords().size() + 1;

        BorrowRecord record = new BorrowRecord(
                recordId,
                studentId,
                bookId,
                new java.util.Date(),
                null
        );

        borrowRecordDAO.borrowBook(record);

        System.out.println("Book borrowed successfully");
    }

    // 🔁 Return Book
    public void returnBook(int recordId) {

        List<BorrowRecord> records = borrowRecordDAO.getAllRecords();

        for (BorrowRecord r : records) {

            if (r.getRecordId() == recordId) {

                if (r.getReturnDate() != null) {
                    System.out.println("Book already returned");
                    return;
                }

                r.setReturnDate(new java.util.Date());

                for (Book b : bookDAO.getAllBooks()) {
                    if (b.getBookId() == r.getBookId()) {
                        b.setAvailability(true);
                        break;
                    }
                }

                System.out.println("Book returned successfully");
                return;
            }
        }

        System.out.println("Record not found");
    }
}