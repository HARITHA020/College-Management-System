package service;

import java.util.List;

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

        Student student = new Student(id, name, department, dob, contact, userId);
        studentDAO.addStudent(student);

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
                if (c.getcourseId() == e.getCourseId()) {
                    System.out.println("Course ID: " + c.getcourseId() +
                            " | Name: " + c.getcourseName() +
                            " | Faculty ID: " + c.getFacultyid());
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
        List<Enrollment> enrollments = enrollmentDao.getEnrollmentsByStudent(studentId);

        if (enrollments == null || enrollments.isEmpty()) {
            System.out.println("No enrolled courses for marks");
            return;
        }

        System.out.println("\n--- Marks Report ---");

        for (Enrollment e : enrollments) {
            int courseId = e.getCourseId();
            Course course = null;

            // Find course name
            for (Course c : courseDAO.getAllCourses()) {
                if (c.getcourseId() == courseId) {
                    course = c;
                    break;
                }
            }

            if (course == null) continue;

            List<Exam> courseExams = examDAO.getExamsByCourse(courseId); // assume we add this method
            if (courseExams == null || courseExams.isEmpty()) {
                System.out.println("Course: " + course.getcourseName() + " | No exams yet");
                continue;
            }

            double totalMarks = 0;
            double maxTotal = 0;

            System.out.println("\nCourse: " + course.getcourseName() + " (ID: " + courseId + ")");
            for (Exam ex : courseExams) {
                Integer mark = ex.getStudentMarks(studentId); // assume Exam has a Map<Integer, Integer> of student marks
                if (mark == null) mark = 0;
                System.out.println("Exam ID: " + ex.getExamId() + " | Marks: " + mark + "/" + ex.getMaxMarks());
                totalMarks += mark;
                maxTotal += ex.getMaxMarks();
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
            List<Attendance> records = attendanceDao.getAttendanceByStudentAndCourse(studentId, c.getcourseId());

            int totalClasses = records.size();
            long attendedClasses = records.stream().filter(Attendance::isPresent).count();
            double percentage = totalClasses == 0 ? 0 : ((double) attendedClasses / totalClasses) * 100;

            System.out.println("Course: " + c.getcourseName());
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

    public void returnBook(int recordId) {
        libraryService.returnBook(recordId);
    }
}