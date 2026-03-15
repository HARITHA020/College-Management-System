package service;

import java.util.List;

import dao.BookDAO;
import dao.BorrowRecordDAO;
import dao.CourseDAO;
import dao.ExamDAO;
import dao.StudentDAO;
import dao.TimetableDAO;
import model.Student;


public class StudentService {
	private StudentDAO studentDAO = new StudentDAO();
	private CourseDAO courseDAO;
    private TimetableDAO timetableDAO;
    private ExamDAO examDAO;
    private BookDAO bookDAO;
    private BorrowRecordDAO borrowRecordDAO;
    
    public StudentService() {
        this.studentDAO      = new StudentDAO();
        this.courseDAO       = new CourseDAO();
        this.timetableDAO    = new TimetableDAO();
        this.examDAO         = new ExamDAO();
        this.bookDAO         = new BookDAO();
        this.borrowRecordDAO = new BorrowRecordDAO();
    }
    
 // ── Courses ──────────────────────────────────────────
    public void viewCourses() {
        List<Course> courses = courseDAO.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            System.out.println("\n--- Courses ---");
            courses.forEach(c ->
                System.out.println("ID: " + c.getCourseId() + " | " + c.getCourseName()));
        }
    }

    // ── Timetable ─────────────────────────────────────────
    public void viewTimetable() {
        List<Timetable> timetables = timetableDAO.getTimetable();
        if (timetables.isEmpty()) {
            System.out.println("No timetable entries found.");
        } else {
            System.out.println("\n--- Timetable ---");
            timetables.forEach(t ->
                System.out.println(t.getDay() + " | " + t.getTime() + " | Room: " + t.getRoom()));
        }
    }

    // ── Marks ─────────────────────────────────────────────
    public void viewMarks() {
        List<Exam> exams = examDAO.getAllExams();
        if (exams.isEmpty()) {
            System.out.println("No exam records found.");
        } else {
            System.out.println("\n--- Exam / Marks Records ---");
            exams.forEach(e ->
                System.out.println("Exam ID: " + e.getExamId()
                    + " | Course ID: " + e.getCourseId()
                    + " | Date: " + e.getExamDate()));
        }
    }

    // ── Library ───────────────────────────────────────────
    public void searchBook(String keyword) {
        List<Book> books = bookDAO.getAllBooks();
        System.out.println("\n--- Search Results for: " + keyword + " ---");
        books.stream()
             .filter(b -> b.getTitle().toLowerCase().contains(keyword.toLowerCase())
                       || b.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
             .forEach(b -> System.out.println("ID: " + b.getBookId()
                 + " | " + b.getTitle()
                 + " by " + b.getAuthor()
                 + " | Available: " + b.isAvailability()));
    }

    public void borrowBook(int studentId, int bookId) {
        Book book = bookDAO.getAllBooks().stream()
                           .filter(b -> b.getBookId() == bookId)
                           .findFirst().orElse(null);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        if (!book.isAvailability()) {
            System.out.println("Book is currently unavailable.");
            return;
        }
        book.setAvailability(false);
        BorrowRecord record = new BorrowRecord(
            borrowRecordDAO.getAllRecords().size() + 1,
            studentId, bookId,
            new java.util.Date(), null
        );
        borrowRecordDAO.borrowBook(record);
        System.out.println("Book borrowed successfully: " + book.getTitle());
    }

    public void returnBook(int recordId) {
        borrowRecordDAO.returnBook(recordId);
        System.out.println("Book returned successfully. Record ID: " + recordId);
    }
}
