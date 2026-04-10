// AUTHOR: Balamurugan
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
    // Faculty Management (Admin Purpose)
 	public void addFacultyWithUser(String email, String password,String name, String department, String dob,
 			String contact) {

 		if (userDAO.checkEmailExists(email)) {
 			System.out.println("Email already exists");
 			return;
 		}

 		int userId = userDAO.createUser(email, password, "FACULTY");

 		if (userId == -1) {
 			System.out.println("User creation failed");
 			return;
 		}
 		facultyDAO.addFaculty( name, department, dob, contact, userId);
 	}
 	public void updateFaculty(int facultyId, String field, String value) {

 	    if (facultyId <= 0) {
 	        System.out.println("Invalid Faculty ID");
 	        return;
 	    }

 	    boolean updated = facultyDAO.updateFacultyField(facultyId, field, value);

 	    if (updated) {
 	        System.out.println("✅ Faculty updated successfully");
 	    } else {
 	        System.out.println("❌ Update failed or invalid field");
 	    }
 	}

 	public void deleteFaculty(int id) {

 		if (id <= 0) {
 			System.out.println("Invalid Faculty ID");
 			return;
 		}

 		int userId = facultyDAO.getUserIdByFacultyId(id);

 		if (userId == -1) {
 			System.out.println("Faculty not found");
 			return;
 		}

 		userDAO.deleteUser(userId);

 		System.out.println("✅ Faculty deleted successfully (CASCADE)");
 	}

 	public void viewFaculty() {

 	    List<Faculty> faculties = facultyDAO.getAllFaculty();

 	    if (faculties == null || faculties.isEmpty()) {
 	        System.out.println("No faculty available");
 	        return;
 	    }

 	    System.out.println("\n---------------------- FACULTY LIST ----------------------");

 	    System.out.printf("%-5s %-20s %-15s %-12s %-15s\n",
 	            "ID", "Name", "Department", "DOB", "Contact");

 	    System.out.println("----------------------------------------------------------");

 	    for (Faculty f : faculties) {
 	        System.out.printf("%-5d %-20s %-15s %-12s %-15s\n",
 	                f.getId(),
 	                f.getName(),
 	                f.getDepartment(),
 	                f.getDob(),
 	                f.getContact());
 	    }
 	}

   
    // VIEW STUDENTS 
 	public void viewMyStudents(int facultyId) {
 	    boolean found = false;

 	    System.out.println("\n------------------- MY STUDENTS -------------------");

 	    System.out.printf("%-12s %-20s %-20s\n", 
 	            "Student ID", "Name", "Course");

 	    System.out.println("---------------------------------------------------");

 	    for (Course c : courseDAO.getAllCourses()) {

 	        if (c.getFacultyId() == facultyId) {

 	            List<Enrollment> enrollments = enrollmentDao.getEnrollmentsByCourse(c.getCourseId());

 	            for (Enrollment e : enrollments) {

 	                Student s = studentDAO.getStudentById(e.getStudentId());

 	                if (s != null) {
 	                    System.out.printf("%-12d %-20s %-20s\n",
 	                            s.getId(),
 	                            s.getName(),
 	                            c.getCourseName());

 	                    found = true;
 	                }
 	            }
 	        }
 	    }

 	    if (!found) {
 	        System.out.println("No students found for your courses");
 	    }

 	    System.out.println("---------------------------------------------------");
 	}

    // VIEW COURSES 
 	public void viewMyCourses(int facultyId) {
 	    boolean found = false;

 	    System.out.println("\n---------- MY COURSES -------------");

 	    System.out.printf("%-12s %-25s\n",
 	            "Course ID", "Course Name");

 	    System.out.println("--------------------------------------");

 	    for (Course c : courseDAO.getAllCourses()) {

 	        if (c.getFacultyId() == facultyId) {

 	            System.out.printf("%-12d %-25s\n",
 	                    c.getCourseId(),
 	                    c.getCourseName());

 	            found = true;
 	        }
 	    }

 	    if (!found) {
 	        System.out.println("No courses assigned");
 	    }

 	    System.out.println("--------------------------------------");
 	}

    // MARK ATTENDANCE 
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

        boolean enrolled = enrollmentDao.isStudentEnrolled(studentId, courseId);
        if (!enrolled) {
            System.out.println("❌ Student is not enrolled in this course");
            return;
        }

        Date today = new Date();
        Attendance attendance = new Attendance(studentId, courseId, today, present);

        attendanceDao.markAttendance(attendance);

        System.out.println("Attendance marked for " + s.getName() +
                " in " + c.getCourseName() +
                " as " + (present ? "PRESENT" : "ABSENT"));
    }

    // ADD MARKS
    public void addResult(int facultyId,int studentId, int courseId, int marks) {

        if (marks < 0 || marks > 100) {
            System.out.println("Invalid marks");
            return;
        }
     // student  VALIDATION
        if (!studentDAO.isStudentExists(studentId)) {
            System.out.println("❌ Student does not exist");
            return;
        }
        // USE YOUR EXISTING METHOD HERE
      
        if (!courseDAO.isCourseAlreadyAssigned(courseId, facultyId)) {
            System.out.println("❌ You are not allowed to enter marks for this course");
            return;
        }

        int examId = resultDAO.getExamIdByCourse(courseId);
        if (examId == -1) {
            System.out.println("No exam found");
            return;
        }

        // CHECK DUPLICATE
        if (resultDAO.isResultAlreadyExists(studentId, examId)) {
            System.out.println("Marks already entered for this student and course");
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

 // VIEW TIME by period

    public String getTimeByPeriod(int period) {

        switch (period) {
            case 1: return "9:00 - 10:00";
            case 2: return "10:00 - 11:00";
            case 3: return "11:00 - 12:00";
            case 4: return "12:00 - 1:00";
            case 5: return "2:00 - 3:00";
            case 6: return "3:00 - 4:00";
            default: return "Invalid";
        }
    }

 
 // VIEW FULL TIMETABLE
    public void viewTimetable(int facultyId) {
        List<Timetable> list = timetableDAO.getAllTimetables();

        if (list.isEmpty()) {
            System.out.println("No timetable available");
            return;
        }
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        System.out.printf("%-10s", "Day");
        for (int p = 1; p <= 6; p++) {
            System.out.printf(" | %-25s", "P" + p + " (" + getTimeByPeriod(p) + ")");
        }
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------------");

        for (String day : days) {
            System.out.printf("%-10s", day);

            for (int p = 1; p <= 6; p++) {
                Timetable tt = null;
                for (Timetable t : list) {
                    if (t.getFacultyId() == facultyId
                        && t.getDay().equalsIgnoreCase(day)
                        && t.getPeriod() == p) {
                        tt = t;
                        break;
                    }
                }                String courseDisplay;
                if (tt != null) {
                    Course c = courseDAO.getCourseById(tt.getCourseId());
                    courseDisplay = "C" + tt.getCourseId() + ":" + c.getCourseName() + "(" + tt.getSection() + ")";
                } else {
                    courseDisplay = " - ";
                }

                System.out.printf(" | %-25s", courseDisplay);
            }

            System.out.println();
        }
    }
    // UPLOAD MATERIALS
    public void uploadMaterial(int courseId, String title, String content, int facultyId) {

        // 1. Validate course
        Course course = courseDAO.getCourseById(courseId);

        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        // 2. Check faculty assignment
        if (course.getFacultyId() != facultyId) {
            System.out.println("You are not assigned to this course");
            return;
        }

        // 3. Duplicate check 
        for (Material m : materialDAO.getAllMaterials()) {
            if (m.getCourseId() == courseId &&
                m.getTitle().equalsIgnoreCase(title.trim())) {

                System.out.println("Material already exists");
                return;
            }
        }

        // 4. Create Material
        Material m = new Material(0, courseId, title.trim(), content.trim());

        materialDAO.addMaterial(m);

        System.out.println("Material uploaded successfully");
    }
    // view materials

    public void viewMaterials(int courseId, int facultyId, String role) {
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
        System.out.println("\n------------------- MATERIALS -------------------");
        System.out.printf("%-12s %-25s %-35s\n",
                "Material ID", "Title", "Content");

        System.out.println("----------------------------------------------------");

        for (Material m : materialDAO.getAllMaterials()) {

            if (m.getCourseId() == courseId) {
                String content = m.getContent();
                if (content.length() > 30) {
                    content = content.substring(0, 30) + "...";
                }
                System.out.printf("%-12d %-25s %-35s\n",
                        m.getId(),
                        m.getTitle(),
                        content);

                found = true;
            }
        }

        if (!found) {
            System.out.println("No materials found");
        }

        System.out.println("-----------------------------------------------------");
    }
    // delete material
    public void deleteMaterial(int materialId, int facultyId, String role) {
        if (!role.equalsIgnoreCase("FACULTY")) {
            System.out.println("Access denied");
            return;
        }

        boolean deleted = materialDAO.deleteMaterialById(materialId);

        if (deleted) {
            System.out.println("✅ Material deleted successfully");
        } else {
            System.out.println("❌ No material found with this ID");
        }
    }
    // CREATE ASSIGNMENT
    public void createAssignment(int courseId, String title, String desc, int facultyId) {
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
    // view assignment
    public void viewAssignments(int courseId, int facultyId, String role) {

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

        System.out.println("\n------------------- ASSIGNMENTS -------------------");
        System.out.printf("%-15s %-25s %-35s\n",
                "Assignment ID", "Title", "Description");

        System.out.println("------------------------------------------------------------------------------");

        for (Assignment a : assignmentDAO.getAllAssignments()) {

            if (a.getCourseId() == courseId) {
                String desc = a.getDescription();
                if (desc.length() > 30) {
                    desc = desc.substring(0, 30) + "...";
                }
                System.out.printf("%-15d %-25s %-35s\n",
                        a.getId(),
                        a.getTitle(),
                        desc);

                found = true;
            }
        }

        if (!found) {
            System.out.println("No assignments");
        }

        System.out.println("------------------------------------------------------------------------------");
    }
    //delete assignment
    public void deleteAssignment(int AssignmentId, int facultyId, String role) {

        if (!role.equalsIgnoreCase("FACULTY")) {
            System.out.println("Access denied");
            return;
        }

        boolean deleted = assignmentDAO.deleteAssignment(AssignmentId);

        if (deleted) {
            System.out.println("✅ Assignment deleted successfully");
        } else {
            System.out.println("❌ No assignment found to delete");
        }
    }
    // VIEW NOTIFICATION
    public void viewNotification(int facultyId) {

        List<Notification> list = notificationDAO.getAllNotifications();

        boolean found = false;

        System.out.println("\n------------------- NOTIFICATIONS ----------------------------------");
        System.out.printf("%-15s %-20s %-40s\n",
                "ID", "Role", "Message");

        System.out.println("---------------------------------------------------------------------------");

        for (Notification n : list) {

            if (n.getTargetRole().equalsIgnoreCase("ALL") ||
                (n.getTargetRole().equalsIgnoreCase("FACULTY") &&
                 (n.getTargetId() == null || n.getTargetId() == facultyId))) {
                String msg = n.getMessage();
                if (msg.length() > 35) {
                    msg = msg.substring(0, 35) + "...";
                }

                System.out.printf("%-15d %-20s %-40s\n",
                        n.getNotificationId(),
                        n.getTargetRole(),
                        msg);

                found = true;
            }
        }

        if (!found) {
            System.out.println("No notifications available");
        }

        System.out.println("----------------------------------------------------------------------------");
    }
    // LIBRARY 
    public void searchBook(String keyword) {
        libraryService.searchBook(keyword);
    }

    public void borrowBook(int facultyId, int bookId) {
        libraryService.borrowBook(facultyId, "faculty", bookId);
    }

    public void returnBook(int bookId, int facultyId) {
        libraryService.returnBook(facultyId, "faculty", bookId);
    }
}