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
    private LibraryService libraryService;
    private EnrollmentDAO enrollmentDao;
    public StudentService() {
        this.studentDAO = new StudentDAO();
        this.courseDAO = new CourseDAO();
        this.timetableDAO = new TimetableDAO();
        this.examDAO = new ExamDAO();
        this.bookDAO = new BookDAO();
        this.borrowRecordDAO = new BorrowRecordDAO();
        this.notificationDAO = new NotificationDAO();
        this.libraryService = new LibraryService();
        this.enrollmentDao=new EnrollmentDAO();
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
   public void viewMyCourses(int studentId) {
	    for (Enrollment e : enrollmentDao.getEnrollmentsByStudent(studentId)) {
	        for (Course c : courseDAO.getAllCourses()) {
	            if (c.getcourseId() == e.getCourseId()) {
	                System.out.println("Course ID: " + c.getcourseId() +
	                        " | Name: " + c.getcourseName() +
	                        " |FacultyId: "+c.getFacultyid());
	            }
	        }
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
        libraryService.searchBook(keyword);
    }

    // 📚 Borrow Book
    public void borrowBook(int studentId, int bookId) {
        libraryService.borrowBook(studentId, "student", bookId);
    }

    // 🔁 Return Book
    public void returnBook(int recordId) {
        libraryService.returnBook(recordId);
    }
}