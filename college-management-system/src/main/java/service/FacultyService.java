package service;

import java.util.List;
import java.util.Date;

import dao.*;
import model.*;

public class FacultyService {

    private FacultyDAO facultyDAO;
    private StudentDAO studentDAO;
    private ExamDAO examDAO;
    private CourseDAO courseDAO;
    private NotificationDAO notificationDAO;
    private TimetableDAO timetableDAO = new TimetableDAO();
    private ResultDAO resultDAO = new ResultDAO();
    private EnrollmentDAO enrollmentDao;
    private LibraryService libraryService;
    private MaterialDAO materialDAO;
    private AssignmentDAO assignmentDAO;
    private AttendanceDAO attendanceDao = new AttendanceDAO();
    private UserDAO userDAO = new UserDAO();

    public FacultyService() {
        facultyDAO = new FacultyDAO();
        studentDAO = new StudentDAO();
        examDAO = new ExamDAO();
        courseDAO = new CourseDAO();
        notificationDAO = new NotificationDAO();
        enrollmentDao = new EnrollmentDAO();
        libraryService = new LibraryService();
        materialDAO = new MaterialDAO();
        assignmentDAO = new AssignmentDAO();
    }

    // ✅ Convert userId → facultyId
    private int getFacultyIdFromUserId(int userId) {
        Faculty f = facultyDAO.getFacultyByUserId(userId);
        if (f == null) return -1;
        return f.getId();
    }

    // ================= STUDENTS =================
    public void viewMyStudents(int userId) {

        int facultyId = getFacultyIdFromUserId(userId);

        for (Course c : courseDAO.getAllCourses()) {
            if (c.getFacultyId() == facultyId) {

                for (Enrollment e : enrollmentDao.getAllEnrollments()) {
                    if (e.getCourseId() == c.getCourseId()) {

                        for (Student s : studentDAO.getAllStudents()) {
                            if (s.getId() == e.getStudentId()) {
                                System.out.println("Student ID: " + s.getId() + " | Name: " + s.getName());
                            }
                        }
                    }
                }
            }
        }
    }

    // ================= COURSES =================
    public void viewMyCourses(int userId) {

        int facultyId = getFacultyIdFromUserId(userId);

        boolean found = false;

        System.out.println("\n--- My Courses ---");

        for (Course c : courseDAO.getAllCourses()) {
            if (c.getFacultyId() == facultyId) {
                System.out.println("Course ID: " + c.getCourseId() + " | Course Name: " + c.getCourseName());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No courses assigned");
        }
    }

    // ================= ATTENDANCE =================
    public void markAttendance(int studentId, int courseId, boolean present) {

        Student s = studentDAO.getStudentById(studentId);
        if (s == null) {
            System.out.println("Student not found");
            return;
        }

        Course c = courseDAO.getCourseById(courseId);
        if (c == null) {
            System.out.println("Course not found");
            return;
        }

        Date today = new Date();
        Attendance attendance = new Attendance(studentId, courseId, today, present);

        attendanceDao.markAttendance(attendance);

        System.out.println("Attendance marked for " + s.getName() +
                " in " + c.getCourseName() +
                " as " + (present ? "PRESENT" : "ABSENT"));
    }

    // ================= MARKS =================
    public void addResult(int studentId, int courseId, int marks) {

        if (marks < 0 || marks > 100) {
            System.out.println("Invalid marks");
            return;
        }

        int examId = resultDAO.getExamIdByCourse(courseId);
        if (examId == -1) {
            System.out.println("No exam found");
            return;
        }

        String grade;
        if (marks >= 90) grade = "A";
        else if (marks >= 75) grade = "B";
        else if (marks >= 50) grade = "C";
        else grade = "Fail";

        Result result = new Result(0, studentId, examId, marks, grade);
        result.setPublished(false);

        resultDAO.addResult(result);

        System.out.println("Marks added (Not Published)");
    }

    // ================= TIMETABLE =================
    public void viewTimetable(int userId) {

        int facultyId = getFacultyIdFromUserId(userId);

        boolean found = false;

        for (Timetable t : timetableDAO.getAllTimetables()) {
            if (t.getFacultyId() == facultyId) {
                System.out.println(t.getDay() + " " + t.getTime() +
                        " → Course: " + t.getCourseId() +
                        " → Room: " + t.getRoom());
                found = true;
            }
        }

        if (!found) System.out.println("No timetable assigned");
    }

    // ================= MATERIAL =================
    public void uploadMaterial(int courseId, String title, String content, int userId) {

        int facultyId = getFacultyIdFromUserId(userId);

        Course course = courseDAO.getCourseById(courseId);

        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        if (course.getFacultyId() != facultyId) {
            System.out.println("You are not assigned to this course");
            return;
        }

        int newId = materialDAO.getAllMaterials().size() + 1;

        Material m = new Material(newId, courseId, title, content);
        materialDAO.addMaterial(m);

        System.out.println("Material uploaded successfully");
    }

    public void viewMaterials(int courseId, int userId, String role) {

        int facultyId = getFacultyIdFromUserId(userId);

        Course course = courseDAO.getCourseById(courseId);

        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        if (role.equalsIgnoreCase("FACULTY") && course.getFacultyId() != facultyId) {
            System.out.println("You are not allowed to view materials");
            return;
        }

        boolean found = false;

        for (Material m : materialDAO.getAllMaterials()) {
            if (m.getCourseId() == courseId) {

                // ✅ UPDATED OUTPUT FORMAT
                System.out.println("----------------------------");
                System.out.println("Title   : " + m.getTitle());
                System.out.println("Content : " + m.getContent());

                found = true;
            }
        }

        if (!found) System.out.println("No materials found");
    }

    // ================= ASSIGNMENT =================
    public void createAssignment(int courseId, String title, String desc, int userId) {

        int facultyId = getFacultyIdFromUserId(userId);

        Course course = courseDAO.getCourseById(courseId);

        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        if (course.getFacultyId() != facultyId) {
            System.out.println("You are not assigned");
            return;
        }

        int newId = assignmentDAO.getAllAssignments().size() + 1;

        Assignment a = new Assignment(newId, courseId, title, desc);
        assignmentDAO.addAssignment(a);

        System.out.println("Assignment created");
    }

    public void viewAssignments(int courseId, int userId, String role) {

        int facultyId = getFacultyIdFromUserId(userId);

        Course course = courseDAO.getCourseById(courseId);

        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        if (role.equalsIgnoreCase("FACULTY") && course.getFacultyId() != facultyId) {
            System.out.println("Not allowed");
            return;
        }

        boolean found = false;

        for (Assignment a : assignmentDAO.getAllAssignments()) {
            if (a.getCourseId() == courseId) {
                System.out.println("Title: " + a.getTitle());
                System.out.println("Desc: " + a.getDescription());
                found = true;
            }
        }

        if (!found) System.out.println("No assignments");
    }

    // ================= NOTIFICATION =================
    public void viewNotification(int facultyId) {
        List<Notification> list = notificationDAO.getAllNotifications();
        for (Notification n : list) {
            if (n.getTargetRole().equalsIgnoreCase("ALL") ||
                (n.getTargetRole().equalsIgnoreCase("FACULTY") &&
                 (n.getTargetId() == null || n.getTargetId() == facultyId))) {
                System.out.println(n);
            }
        }
    }

    // ================= LIBRARY =================
    public void searchBook(String keyword) {
        libraryService.searchBook(keyword);
    }

    public void borrowBook(int userId, int bookId) {
        libraryService.borrowBook(userId, "faculty", bookId);
    }

    public void returnBook(int bookId, int userId) {
        libraryService.returnBook(userId, "faculty", bookId);
    }
}