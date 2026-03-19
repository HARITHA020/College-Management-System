package service;

import java.util.List;
import java.util.Date;

import dao.FacultyDAO;
import dao.NotificationDAO;
import dao.ResultDAO;
import dao.StudentDAO;
import dao.ExamDAO;
import dao.CourseDAO;
import dao.BookDAO;
import dao.BorrowRecordDAO;
import dao.TimetableDAO;

import model.Faculty;
import model.Notification;
import model.Result;
import model.Student;
import model.Timetable;
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
    private NotificationDAO notificationDAO;
    private TimetableDAO timetableDAO=new TimetableDAO();
    private ResultDAO resultDAO = new ResultDAO();

    public FacultyService() {
        facultyDAO = new FacultyDAO();
        studentDAO = new StudentDAO();
        examDAO = new ExamDAO();
        courseDAO = new CourseDAO();
        bookDAO = new BookDAO();
        borrowRecordDAO = new BorrowRecordDAO();
        notificationDAO=new NotificationDAO();
        
        
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
    public void addResult(int studentId, int courseId, int marks) {

        if(studentId <= 0 || courseId <= 0) {
            System.out.println("Invalid data");
            return;
        }

        if(marks < 0 || marks > 100) {
            System.out.println("Invalid marks");
            return;
        }

        String grade;

        if(marks >= 90) grade = "A";
        else if(marks >= 75) grade = "B";
        else if(marks >= 50) grade = "C";
        else grade = "Fail";

        Result result = new Result(
                resultDAO.getAllResults().size() + 1,
                studentId,
                courseId,
                marks,
                grade
        );

        resultDAO.addResult(result);

        System.out.println("Marks Entered by Faculty (Not Published)");
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
                0,             
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
    
    public void viewTimetable(int facultyId) {

        List<Timetable> list = timetableDAO.getAllTimetables();

        boolean found = false;

        for (Timetable t : list) {

            if (t.getFacultyId() == facultyId) {

                System.out.println(
                    t.getDay() + " " + t.getTime() +
                    " → Course ID: " + t.getCourseId() +
                    " → Class: " + t.getSection() +
                    " → Room: " + t.getRoom()
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No timetable assigned");
        }
    }


}