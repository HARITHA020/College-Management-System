package service;

import java.util.List;
import java.util.Date;

import dao.FacultyDAO;
import dao.MaterialDAO;
import dao.NotificationDAO;
import dao.ResultDAO;
import dao.StudentDAO;
import dao.ExamDAO;
import dao.CourseDAO;
import dao.EnrollmentDAO;
import dao.AssignmentDAO;
import dao.BookDAO;
import dao.BorrowRecordDAO;
import dao.TimetableDAO;

import model.Faculty;
import model.Material;
import model.Notification;
import model.Result;
import model.Student;
import model.Timetable;
import model.Exam;
import model.Course;
import model.Assignment;
import model.Book;
import model.BorrowRecord;
import model.Enrollment;
public class FacultyService {

    private FacultyDAO facultyDAO;
    private StudentDAO studentDAO;
    private ExamDAO examDAO;
    private CourseDAO courseDAO;
    private NotificationDAO notificationDAO;
    private TimetableDAO timetableDAO=new TimetableDAO();
    private ResultDAO resultDAO = new ResultDAO();
    private EnrollmentDAO enrollmentDao;
    private LibraryService libraryService; 
    private MaterialDAO materialDAO;
    private AssignmentDAO assignmentDAO;
    public FacultyService() {
        facultyDAO = new FacultyDAO();
        studentDAO = new StudentDAO();
        examDAO = new ExamDAO();
        courseDAO = new CourseDAO();
        notificationDAO=new NotificationDAO();
        enrollmentDao=new EnrollmentDAO();
        libraryService=new LibraryService();
        materialDAO = new MaterialDAO();
        assignmentDAO = new AssignmentDAO();
        
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

    public void viewMyStudents(int facultyId) {

        for (Course c : courseDAO.getAllCourses()) {

            if (c.getFacultyid() == facultyId) {

                for (Enrollment e : enrollmentDao.getAllEnrollments()) {

                    if (e.getCourseId() == c.getcourseId()) {

                        for (Student s : studentDAO.getAllStudents()) {

                            if (s.getId() == e.getStudentId()) {
                                System.out.println("Student ID: " + s.getId() +
                                                   " | Name: " + s.getName());
                            }
                        }
                    }
                }
            }
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
    	//Search  books
    public void searchBook(String keyword) {
        libraryService.searchBook(keyword);
    }

    	//Borrow Book
    public void borrowBook(int facultyId, int bookId) {
        libraryService.borrowBook(facultyId, "faculty", bookId);
    }

    	// Return Book
    public void returnBook(int recordId) {
        libraryService.returnBook(recordId);
    }
    
   //Timetable 
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
    // Materials
    public void uploadMaterial(int courseId, String title, String content) {

        Material m = new Material(
            materialDAO.getAllMaterials().size() + 1,
            courseId,
            title,
            content
        );

        materialDAO.addMaterial(m);
        System.out.println("\nMaterial uploaded successfully");
    }

    public void viewMaterials(int courseId) {

        boolean found = false;

        for (Material m : materialDAO.getAllMaterials()) {

            if (m.getCourseId() == courseId) {

                System.out.println("\nTitle: " + m.getTitle());
                System.out.println("Content: " + m.getContent());
                System.out.println("------------------------");

                found = true;
            }
        }

        if (!found) {
            System.out.println("\nNo materials found");
        }
    }
    
    // Assignments
    public void createAssignment(int courseId, String title, String description) {

        Assignment a = new Assignment(
            assignmentDAO.getAllAssignments().size() + 1,
            courseId,
            title,
            description
        );

        assignmentDAO.addAssignment(a);
        System.out.println("\nAssignment created successfully");
    }

    public void viewAssignments(int courseId) {

        boolean found = false;

        for (Assignment a : assignmentDAO.getAllAssignments()) {

            if (a.getCourseId() == courseId) {

                System.out.println("\nTitle: " + a.getTitle());
                System.out.println("Description: " + a.getDescription());
                System.out.println("------------------------");

                found = true;
            }
        }

        if (!found) {
            System.out.println("\nNo assignments found");
        }
    }

}