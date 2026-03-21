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
import dao.UserDAO;
import dao.AttendanceDAO;

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
import model.Attendance;
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
    private AttendanceDAO attendanceDao =new AttendanceDAO();
    private UserDAO userDAO=new UserDAO();
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

    

    public void addFacultyWithUser(String email, String password, int id, String name, String department, String dob, String contact) {

        if (userDAO.checkEmailExists(email)) {
            System.out.println("Email already exists");
            return;
        }

        int userId = userDAO.createUser(email, password, "FACULTY");

        if (userId == -1) {
            System.out.println("User creation failed");
            return;
        }

        for (Faculty f : facultyDAO.getAllFaculty()) {
            if (f.getId() == id) {
                System.out.println("Faculty already exists");
                return;
            }
        }

        facultyDAO.addFaculty(id, name, department, dob, contact, userId);

        System.out.println("✅ Faculty added successfully");
    }

    public void updateFaculty(int id, String name, String department, String dob, String contact) {

        facultyDAO.updateFaculty(id, name, department, dob, contact);
        System.out.println("✅ Faculty updated successfully");
    }

    public void deleteFaculty(int id) {

        facultyDAO.deleteFaculty(id);
        System.out.println("✅ Faculty deleted successfully");
    }
    
    public void viewFaculty() {

        List<Faculty> faculties = facultyDAO.getAllFaculty();

        if (faculties == null || faculties.isEmpty()) {
            System.out.println("No faculty available");
            return;
        }

        System.out.println("\n--- Faculty List ---");

        for (Faculty f : faculties) {
            System.out.println(
                "ID: " + f.getId() +
                ", Name: " + f.getName() +
                ", Dept: " + f.getDepartment() +
                ", DOB: " + f.getDob() +
                ", Contact: " + f.getContact()
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

    public void viewMyCourses(int facultyId) {

        // 🔹 1. Validate faculty ID
        if (facultyId <= 0) {
            System.out.println("Invalid Faculty ID");
            return;
        }

        boolean found = false;

        System.out.println("\n--- My Courses ---");

        // 🔹 2. Filter courses by facultyId
        for (Course c : courseDAO.getAllCourses()) {

            if (c.getFacultyid() == facultyId) {

                System.out.println(
                        "Course ID: " + c.getcourseId() +
                        " | Course Name: " + c.getcourseName()
                );

                found = true;
            }
        }

        // 🔹 3. If no course assigned
        if (!found) {
            System.out.println("No courses assigned to this faculty");
        }
    }

    // Attendance

    public void markAttendance(int studentId, int courseId, boolean present) {

        // 1. Validate student
        Student s = studentDAO.getStudentById(studentId);
        if (s == null) {
            System.out.println("Student not found");
            return;
        }

        // 2. Validate course
        Course c = courseDAO.getCourseById(courseId);
        if (c == null) {
            System.out.println("Course not found");
            return;
        }

        // 3. Create attendance record for today
        Date today = new Date();
        Attendance attendance = new Attendance(studentId, courseId, today, present);

        // 4. Store attendance
        attendanceDao.markAttendance(attendance);

        // 5. Confirmation
        System.out.println("Attendance marked for " + s.getName() + " in course " + c.getcourseName() +
                " as " + (present ? "PRESENT" : "ABSENT"));
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
   

    // Notification

    public void viewNotification(int facultyId) {

        List<Notification> list = notificationDAO.getAllNotifications();

        if (list.isEmpty()) {
            System.out.println("No notifications available");
            return;
        }

        boolean found = false;

        for (Notification n : list) {

            // 1. Common notification
            if (n.getTargetRole().equalsIgnoreCase("ALL")) {

                System.out.println(n);
                found = true;
            }

            // 2. Faculty notification
            else if (n.getTargetRole().equalsIgnoreCase("FACULTY")) {

                // If no specific target → all faculty
                if (n.getTargetId() == null) {
                    System.out.println(n);
                    found = true;
                }

                // If specific faculty
                else if (n.getTargetId() == facultyId) {
                    System.out.println(n);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No notifications for this faculty");
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
    public void uploadMaterial(int courseId, String title, String content, int facultyId) {

        // 🔹 1. Validate Course ID
        if (courseId <= 0) {
            System.out.println("Invalid Course ID");
            return;
        }

        // 🔹 2. Check course exists
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        // 🔹 3. Check faculty is assigned to this course (VERY IMPORTANT 🔥)
        if (course.getFacultyid() != facultyId) {
            System.out.println("You are not assigned to this course");
            return;
        }

        // 🔹 4. Validate Title
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Title cannot be empty");
            return;
        }

        // 🔹 5. Validate Content
        if (content == null || content.trim().isEmpty()) {
            System.out.println("Content cannot be empty");
            return;
        }

        // 🔹 6. Length validation
        if (title.length() > 100) {
            System.out.println("Title too long (max 100 characters)");
            return;
        }

        if (content.length() > 1000) {
            System.out.println("Content too long (max 1000 characters)");
            return;
        }

        // 🔹 7. Duplicate material check (same title for same course)
        for (Material m : materialDAO.getAllMaterials()) {
            if (m.getCourseId() == courseId &&
                m.getTitle().equalsIgnoreCase(title.trim())) {

                System.out.println("Material with same title already exists for this course");
                return;
            }
        }

        // 🔹 8. Generate ID safely
        int newId = materialDAO.getAllMaterials().size() + 1;

        // 🔹 9. Create & save
        Material m = new Material(
            newId,
            courseId,
            title.trim(),
            content.trim()
        );

        materialDAO.addMaterial(m);

        System.out.println("Material uploaded successfully");
    }

    public void viewMaterials(int courseId, int userId, String role) {

        // 🔹 1. Validate courseId
        if (courseId <= 0) {
            System.out.println("Invalid Course ID");
            return;
        }

        // 🔹 2. Check course exists
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        // 🔹 3. Access control (VERY IMPORTANT 🔥)

        // If faculty → must be assigned
        if (role.equalsIgnoreCase("FACULTY") &&
            course.getFacultyid() != userId) {

            System.out.println("You are not allowed to view materials of this course");
            return;
        }

        // If student → must be enrolled
        if (role.equalsIgnoreCase("STUDENT")) {

            boolean enrolled = false;

            for (Enrollment e : enrollmentDao.getAllEnrollments()) {
                if (e.getStudentId() == userId &&
                    e.getCourseId() == courseId) {
                    enrolled = true;
                    break;
                }
            }

            if (!enrolled) {
                System.out.println("You are not enrolled in this course");
                return;
            }
        }

        // 🔹 4. Display materials
        boolean found = false;

        for (Material m : materialDAO.getAllMaterials()) {

            if (m.getCourseId() == courseId) {

                System.out.println("\nTitle: " + m.getTitle());
                System.out.println("Content: " + m.getContent());
                System.out.println("------------------------");

                found = true;
            }
        }

        // 🔹 5. No materials case
        if (!found) {
            System.out.println("No materials found for this course");
        }
    }
    
    // Assignments
    public void createAssignment(int courseId, String title, String description, int facultyId) {

        // 🔹 1. Validate courseId
        if (courseId <= 0) {
            System.out.println("Invalid Course ID");
            return;
        }

        // 🔹 2. Check course exists
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        // 🔹 3. Check faculty assigned to course (IMPORTANT 🔥)
        if (course.getFacultyid() != facultyId) {
            System.out.println("You are not assigned to this course");
            return;
        }

        // 🔹 4. Validate title
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Assignment title cannot be empty");
            return;
        }

        // 🔹 5. Validate description
        if (description == null || description.trim().isEmpty()) {
            System.out.println("Assignment description cannot be empty");
            return;
        }

        // 🔹 6. Length validation
        if (title.length() > 100) {
            System.out.println("Title too long (max 100 characters)");
            return;
        }

        if (description.length() > 1000) {
            System.out.println("Description too long (max 1000 characters)");
            return;
        }

        // 🔹 7. Duplicate check (same title in same course)
        for (Assignment a : assignmentDAO.getAllAssignments()) {
            if (a.getCourseId() == courseId &&
                a.getTitle().equalsIgnoreCase(title.trim())) {

                System.out.println("Assignment with same title already exists for this course");
                return;
            }
        }

        // 🔹 8. Generate ID safely
        int newId = assignmentDAO.getAllAssignments().size() + 1;

        // 🔹 9. Create & store
        Assignment a = new Assignment(
            newId,
            courseId,
            title.trim(),
            description.trim()
        );

        assignmentDAO.addAssignment(a);

        System.out.println("Assignment created successfully");
    }

    public void viewAssignments(int courseId, int userId, String role) {

        // 🔹 1. Validate courseId
        if (courseId <= 0) {
            System.out.println("Invalid Course ID");
            return;
        }

        // 🔹 2. Check course exists
        Course course = courseDAO.getCourseById(courseId);
        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        // 🔹 3. Access control 🔥

        // Faculty → must be assigned
        if (role.equalsIgnoreCase("FACULTY") &&
            course.getFacultyid() != userId) {

            System.out.println("You are not allowed to view assignments of this course");
            return;
        }

        // Student → must be enrolled
        if (role.equalsIgnoreCase("STUDENT")) {

            boolean enrolled = false;

            for (Enrollment e : enrollmentDao.getAllEnrollments()) {
                if (e.getStudentId() == userId &&
                    e.getCourseId() == courseId) {
                    enrolled = true;
                    break;
                }
            }

            if (!enrolled) {
                System.out.println("You are not enrolled in this course");
                return;
            }
        }

        // 🔹 4. Display assignments
        boolean found = false;

        for (Assignment a : assignmentDAO.getAllAssignments()) {

            if (a.getCourseId() == courseId) {

                System.out.println("\nTitle: " + a.getTitle());
                System.out.println("Description: " + a.getDescription());
                System.out.println("------------------------");

                found = true;
            }
        }

        // 🔹 5. No data case
        if (!found) {
            System.out.println("No assignments found for this course");
        }
    }
}