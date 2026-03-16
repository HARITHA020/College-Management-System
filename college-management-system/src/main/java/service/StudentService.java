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

    public StudentService() {
        this.studentDAO = new StudentDAO();
        this.courseDAO = new CourseDAO();
        this.timetableDAO = new TimetableDAO();
        this.examDAO = new ExamDAO();
        this.bookDAO = new BookDAO();
        this.borrowRecordDAO = new BorrowRecordDAO();
    }

    // STUDENT CRUD
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

    // COURSES
    public void viewCourses() {
        List<Course> courses = courseDAO.getAllCourses();
        courses.forEach(c ->
            System.out.println("ID: " + c.getcourseId() + " | " + c.getcourseId()));
    }

    // TIMETABLE
    public void viewTimetable() {
        List<Timetable> list = timetableDAO.getAllTimetables();
        list.forEach(t ->
            System.out.println(t.getDay() + " | " + t.getTime()));
    }

    // MARKS
    public void viewMarks() {
        List<Exam> exams = examDAO.getAllExam();
        exams.forEach(e ->
            System.out.println("Exam: " + e.getExamId() + " Date: " + e.getExamDate()));
    }

    public void viewAttendance() {
        System.out.println("Attendance feature coming soon...");
    }

    public void viewNotifications() {
        System.out.println("No notifications.");
    }

    // LIBRARY
    public void searchBook(String keyword) {
        List<Book> books = bookDAO.getAllBooks();
        books.stream()
            .filter(b -> b.getTitle().toLowerCase().contains(keyword.toLowerCase()))
            .forEach(b -> System.out.println(b.getTitle()));
    }

    public void borrowBook(int studentId, int bookId) {
        Book book = bookDAO.getAllBooks().stream()
                .filter(b -> b.getBookId() == bookId)
                .findFirst().orElse(null);

        if (book == null) {
            System.out.println("Book not found");
            return;
        }

        book.setAvailability(false);

        BorrowRecord record = new BorrowRecord(
                borrowRecordDAO.getAllRecords().size() + 1,
                studentId, bookId,
                new java.util.Date(), null
        );

        borrowRecordDAO.borrowBook(record);
        System.out.println("Book borrowed");
    }

    public void returnBook(int recordId) {
        borrowRecordDAO.returnBook(recordId);
    }
}