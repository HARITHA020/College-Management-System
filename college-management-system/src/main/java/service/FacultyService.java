package service;

import java.util.List;
import java.util.Date;

import dao.FacultyDAO;
import dao.StudentDAO;
import dao.ExamDAO;
import dao.CourseDAO;
import dao.BookDAO;
import dao.BorrowRecordDAO;

import model.Faculty;
import model.Student;
import model.Exam;
import model.Course;
import model.Book;
import model.BorrowRecord;

public class FacultyService {

    private FacultyDAO facultyDAO;
    private StudentDAO studentDAO;
    private ExamDAO examDAO;
    private CourseDAO courseDAO;
    private BookDAO bookDAO;
    private BorrowRecordDAO borrowRecordDAO;

    public FacultyService() {
        facultyDAO = new FacultyDAO();
        studentDAO = new StudentDAO();
        examDAO = new ExamDAO();
        courseDAO = new CourseDAO();
        bookDAO = new BookDAO();
        borrowRecordDAO = new BorrowRecordDAO();
    }

    // Faculty Management (Admin Purpose)

    public void addFaculty(int id, String name, String department) {

        Faculty faculty = new Faculty(id, name, department);
        facultyDAO.addFaculty(faculty);

        System.out.println("Faculty Added Successfully");
    }

    public void updateFaculty(int id, String name, String department) {

        facultyDAO.updateFaculty(id, name, department);
        System.out.println("Faculty Updated Successfully");
    }

    public void deleteFaculty(int id) {

        facultyDAO.deleteFaculty(id);
        System.out.println("Faculty Deleted Successfully");
    }

    public void viewFaculties() {

        List<Faculty> faculties = facultyDAO.getAllFaculty();

        System.out.println("Faculty Details:");

        for (Faculty faculty : faculties) {
            System.out.println(
                    "Faculty Id: " + faculty.getId() +
                    "\nFaculty Name: " + faculty.getName() +
                    "\nFaculty Department: " + faculty.getDepartment()
            );
        }
    }

    // Students

    public void viewStudents() {

        List<Student> students = studentDAO.getAllStudents();

        System.out.println("Student Details:");

        for (Student s : students) {
            System.out.println(
                    "Student Id: " + s.getId() +
                    "\nStudent Name: " + s.getName() +
                    "\nDepartment: " + s.getDepartment()
            );
        }
    }

    // Courses

    public void viewCourses() {

        List<Course> courses = courseDAO.getAllCourses();

        System.out.println("Course Details:");

        for (Course c : courses) {
            System.out.println(
                    "Course ID: " + c.getcourseId() +
                    " | Course Name: " + c.getcourseName()
            );
        }
    }

    // Attendance

    public void markAttendance(int studentId, boolean present) {

        Student s = studentDAO.getStudentById(studentId);

        if (s == null) {
            System.out.println("Student not found");
            return;
        }

        if (present)
            System.out.println("Attendance: " + s.getName() + " PRESENT");
        else
            System.out.println("Attendance: " + s.getName() + " ABSENT");
    }

    // Marks

    public void enterMarks(int examId, int courseId, String examDate) {

        examDAO.addExam(examId, courseId, examDate);

        System.out.println("Marks Entered Successfully");
    }

    public void viewMarks() {

        List<Exam> exams = examDAO.getAllExam();

        System.out.println("Exam / Marks Records:");

        for (Exam e : exams) {
            System.out.println(
                    "Exam ID: " + e.getExamId() +
                    " | Course ID: " + e.getCourseId() +
                    " | Date: " + e.getExamDate()
            );
        }
    }

    // Notification

    public void viewNotification() {
        System.out.println("No new notifications available.");
    }

    // Library

    public void searchBook(String keyword) {

        List<Book> books = bookDAO.getAllBooks();

        System.out.println("Search Results:");

        for (Book b : books) {

            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                b.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {

                System.out.println(
                        "Book ID: " + b.getBookId() +
                        " | Title: " + b.getTitle() +
                        " | Author: " + b.getAuthor() +
                        " | Available: " + b.isAvailability()
                );
            }
        }
    }

    public void borrowBook(int facultyId, int bookId) {

        BorrowRecord record = new BorrowRecord(
                borrowRecordDAO.getAllRecords().size() + 1,
                facultyId,
                bookId,
                new Date(),
                null
        );

        borrowRecordDAO.borrowBook(record);

        System.out.println("Book Borrowed Successfully");
    }

    public void returnBook(int recordId) {

        List<BorrowRecord> records = borrowRecordDAO.getAllRecords();

        for (BorrowRecord r : records) {

            if (r.getRecordId() == recordId) {

                r.setReturnDate(new Date());

                System.out.println("Book Returned Successfully");
                return;
            }
        }

        System.out.println("Record not found");
    }
}