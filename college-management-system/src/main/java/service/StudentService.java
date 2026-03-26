package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.*;
import model.*;

public class StudentService {

    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private TimetableDAO timetableDAO;
    private ExamDAO examDAO;
    private NotificationDAO notificationDAO;
    private LibraryService libraryService;
    private EnrollmentDAO enrollmentDao;
    private MaterialDAO materialDAO;
    private AssignmentDAO assignmentDAO;
    private AttendanceDAO attendanceDao;
    private ResultDAO resultDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
        this.courseDAO = new CourseDAO();
        this.timetableDAO = new TimetableDAO();
        this.examDAO = new ExamDAO();
        this.notificationDAO = new NotificationDAO();
        this.libraryService = new LibraryService();
        this.enrollmentDao = new EnrollmentDAO();
        this.materialDAO = new MaterialDAO();
        this.assignmentDAO = new AssignmentDAO();
        this.attendanceDao= new AttendanceDAO();
        this.resultDAO=new ResultDAO();
    }
    

    // ===================== STUDENT CRUD =====================
    private UserDAO userDAO = new UserDAO();

    public void addStudentWithUser(String email, String password, int id, String name, String department, String dob, String contact) {

        if (userDAO.checkEmailExists(email)) {
            System.out.println("Email already exists");
            return;
        }

        int userId = userDAO.createUser(email, password, "STUDENT");

        if (userId == -1) {
            System.out.println("User creation failed");
            return;
        }

        for (Student s : studentDAO.getAllStudents()) {
            if (s.getId() == id) {
                System.out.println("Student already exists");
                return;
            }
        }
        studentDAO.addStudent(name, department, dob, contact, userId);

        System.out.println("✅ Student added successfully");
    }

    public void updateStudent(int id, String name, String department, String dob, String contact) {

        boolean exists = false;

        for (Student s : studentDAO.getAllStudents()) {
            if (s.getId() == id) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            System.out.println("Student not found");
            return;
        }

        studentDAO.updateStudent(id, name, department, dob, contact);
        System.out.println("✅ Student updated successfully");
    }

    public void deleteStudent(int id) {

        if (id <= 0) {
            System.out.println("Invalid Student ID");
            return;
        }

        int userId = studentDAO.getUserIdByStudentId(id);

        if (userId == -1) {
            System.out.println("Student not found");
            return;
        }

        userDAO.deleteUser(userId);

        System.out.println("✅ Student deleted successfully (CASCADE)");
    }
    
    public void viewStudents() {

        List<Student> students = studentDAO.getAllStudents();

        if (students == null || students.isEmpty()) {
            System.out.println("No students available");
            return;
        }

        System.out.println("\n--- Student List ---");

        for (Student s : students) {
            System.out.println(
                "ID: " + s.getId() +
                ", Name: " + s.getName() +
                ", Dept: " + s.getDepartment() +
                ", DOB: " + s.getDob() +
                ", Contact: " + s.getContact()
            );
        }
    }
    public int getStudentIdByUserId(int userId) {

        Student s = studentDAO.getStudentByUserId(userId);

        if (s == null) {
            System.out.println("Student not found for this user");
            return -1;
        }

        return s.getId();
    }
    // ===================== COURSES =====================
    public void viewMyCourses(int studentId) {
        if (studentId <= 0) {
            System.out.println("Invalid Student ID");
            return;
        }
        List<Enrollment> enrollments = enrollmentDao.getEnrollmentsByStudent(studentId);
        if (enrollments == null || enrollments.isEmpty()) {
            System.out.println("No enrolled courses found");
            return;
        }
        for (Enrollment e : enrollments) {
            for (Course c : courseDAO.getAllCourses()) {
                if (c.getCourseId() == e.getCourseId()) {
                    System.out.println("Course ID: " + c.getCourseId() +
                            " | Name: " + c.getCourseName() +
                            " | Faculty ID: " + c.getFacultyId());
                }
            }
        }
    }

    // ===================== TIMETABLE =====================
    public void viewTimetable(int studentId) {
        List<Enrollment> enrollments = enrollmentDao.getEnrollmentsByStudent(studentId);
        if (enrollments == null || enrollments.isEmpty()) {
            System.out.println("No enrolled courses found for timetable");
            return;
        }
        for (Enrollment e : enrollments) {
            for (Timetable t : timetableDAO.getAllTimetables()) {
                if (t.getCourseId() == e.getCourseId()) {
                    System.out.println("Day: " + t.getDay() + " | Time: " + t.getTime() +
                            " | Room: " + t.getRoom() + " | Section: " + t.getSection());
                }
            }
        }
    }

    // ===================== MARKS / EXAMS =====================
    public void viewMarks(int studentId) {
        List<Result> results = resultDAO.getResultsByStudent(studentId);

        if (results == null || results.isEmpty()) {
            System.out.println("No results available for this student.");
            return;
        }

        System.out.println("\n--- Marks Report ---");

        // Group results by course
        Map<Integer, List<Result>> courseResultsMap = new HashMap<>();

        for (Result r : results) {
            if (!r.isPublished()) continue; // show only published results

            Exam exam = examDAO.getExamById(r.getExamId()); // get exam from examId
            if (exam == null) continue;

            int courseId = exam.getCourseId(); // get courseId from exam
            courseResultsMap.computeIfAbsent(courseId, k -> new ArrayList<>()).add(r);
        }

        for (Map.Entry<Integer, List<Result>> entry : courseResultsMap.entrySet()) {
            int courseId = entry.getKey();
            List<Result> courseResults = entry.getValue();

            Course course = courseDAO.getCourseById(courseId);
            String courseName = (course != null) ? course.getCourseName() : "Unknown Course";

            double totalMarks = 0;
            double maxTotal = 0;

            System.out.println("\nCourse: " + courseName + " (ID: " + courseId + ")");

            for (Result r : courseResults) {
                Exam exam = examDAO.getExamById(r.getExamId());
                int maxMarks = (exam != null) ? exam.getMaxMarks() : 100;

                System.out.println("Exam ID: " + r.getExamId() +
                                   " | Marks: " + r.getMarks() + "/" + maxMarks +
                                   " | Grade: " + r.getGrade());

                totalMarks += r.getMarks();
                maxTotal += maxMarks;
            }

            System.out.println("Total: " + totalMarks + "/" + maxTotal);
            double percentage = (maxTotal > 0) ? (totalMarks / maxTotal * 100) : 0;
            System.out.printf("Percentage: %.2f%%\n", percentage);
        }
    }
    // ===================== ATTENDANCE =====================
    public void viewAttendance(int studentId) {
        Student s = studentDAO.getStudentById(studentId);
        if (s == null) {
            System.out.println("Student not found");
            return;
        }

        System.out.println("Attendance Report for: " + s.getName());
        List<Course> courses = courseDAO.getAllCourses();
        for (Course c : courses) {
            List<Attendance> records = attendanceDao.getAttendanceByStudentAndCourse(studentId, c.getCourseId());

            int totalClasses = records.size();
            long attendedClasses = records.stream().filter(Attendance::isPresent).count();
            double percentage = totalClasses == 0 ? 0 : ((double) attendedClasses / totalClasses) * 100;

            System.out.println("Course: " + c.getCourseName());
            System.out.println("Total Classes: " + totalClasses);
            System.out.println("Attended: " + attendedClasses);
            System.out.printf("Attendance Percentage: %.2f%%\n", percentage);
            System.out.println("-------------------------");
        }
    }

    // ===================== NOTIFICATIONS =====================
    public void viewNotifications(int studentId) {
        List<Notification> list = notificationDAO.getAllNotifications();
        boolean found = false;
        for (Notification n : list) {
            if (n.getTargetRole().equalsIgnoreCase("ALL") ||
                (n.getTargetRole().equalsIgnoreCase("STUDENT") &&
                 (n.getTargetId() == null || n.getTargetId() == studentId))) {
                System.out.println("ID: " + n.getNotificationId() +
                        " | Message: " + n.getMessage() +
                        " | Date: " + n.getDate());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No notifications available for you");
        }
    }

    // ===================== MATERIALS =====================
    public void viewMaterials(int courseId, int studentId, String role) {
        // Validate enrollment
        boolean enrolled = false;
        for (Enrollment e : enrollmentDao.getEnrollmentsByStudent(studentId)) {
            if (e.getCourseId() == courseId) {
                enrolled = true;
                break;
            }
        }
        if (!enrolled) {
            System.out.println("You are not enrolled in this course");
            return;
        }

        List<Material> materials = materialDAO.getAllMaterials();
        boolean found = false;
        for (Material m : materials) {
            if (m.getCourseId() == courseId) {
                System.out.println("\nTitle: " + m.getTitle());
                System.out.println("Content: " + m.getContent());
                System.out.println("------------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No materials found for this course");
        }
    }

    // ===================== ASSIGNMENTS =====================
    public void viewAssignments(int courseId, int studentId, String role) {
        // Validate enrollment
        boolean enrolled = false;
        for (Enrollment e : enrollmentDao.getEnrollmentsByStudent(studentId)) {
            if (e.getCourseId() == courseId) {
                enrolled = true;
                break;
            }
        }
        if (!enrolled) {
            System.out.println("You are not enrolled in this course");
            return;
        }

        List<Assignment> assignments = assignmentDAO.getAllAssignments();
        boolean found = false;
        for (Assignment a : assignments) {
            if (a.getCourseId() == courseId) {
                System.out.println("\nTitle: " + a.getTitle());
                System.out.println("Description: " + a.getDescription());
                System.out.println("------------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No assignments found for this course");
        }
    }

    // ===================== LIBRARY =====================
    public void searchBook(String keyword) {
        libraryService.searchBook(keyword);
    }

    public void borrowBook(int studentId, int bookId) {
        libraryService.borrowBook(studentId, "STUDENT", bookId);
    }

    public void returnBook(int bookId, int studentId) {
        libraryService.returnBook(studentId, "student", bookId);
    }
}