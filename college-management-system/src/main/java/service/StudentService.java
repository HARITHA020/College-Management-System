package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.*;
import model.*;

public class StudentService {

    private StudentDAO studentDAO;
    private FacultyDAO facultyDAO;
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
    private BorrowRecordDAO borrowDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
        this.facultyDAO=new FacultyDAO();
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
        this.borrowDAO=new BorrowRecordDAO();
    }
    

    // ===================== STUDENT CRUD =====================
    private UserDAO userDAO = new UserDAO();

	public void addStudentWithUser(String email, String password,  String name, String department, String dob,
			String contact, String section) {

		if (userDAO.checkEmailExists(email)) {
			System.out.println("Email already exists");
			return;
		}

		int userId = userDAO.createUser(email, password, "STUDENT");

		if (userId == -1) {
			System.out.println("User creation failed");
			return;
		}


         // ✅ FIXED ORDER + section added
		studentDAO.addStudent(name, dob, contact, department, section, userId);
	}
	public void updateStudent(int id, String name, String department, String dob, String contact, String section) {

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

	    // ✅ UPDATED
	    studentDAO.updateStudent(id, name, dob, contact, department, section);

	}

	public void deleteStudent(int id) {

	    int userId = studentDAO.getUserIdByStudentId(id);

	    if (userId == -1) {
	        System.out.println("Student not found");
	        return;
	    }

	    try {
	        enrollmentDao.deleteByStudentId(id);   // ✅ FIX NAME
	        attendanceDao.deleteByStudentId(id);
	        borrowDAO.deleteByStudentId(id);

	        userDAO.deleteUser(userId);

	    } catch (Exception e) {
	        System.out.println("❌ Delete failed: " + e.getMessage());
	    }
	}
    public void viewStudents() {

        List<Student> students = studentDAO.getAllStudents();

        if (students == null || students.isEmpty()) {
            System.out.println("No students available");
            return;
        }

        System.out.println("\n-------------------- STUDENT LIST --------------------------------------------");

        // ✅ Table Header
        System.out.printf("%-5s %-20s %-15s %-10s %-12s %-15s\n",
                "ID", "Name", "Department", "Section", "DOB", "Contact");

        System.out.println("--------------------------------------------------------------------------------");

        // ✅ Table Rows
        for (Student s : students) {
            System.out.printf("%-5d %-20s %-15s %-10s %-12s %-15s\n",
                    s.getId(),
                    s.getName(),
                    s.getDepartment(),
                    s.getSection(),
                    s.getDob(),
                    s.getContact());
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
        System.out.printf("%-10s %-15s %-20s %-15s %-20s\n",
                "Course ID", "Student Name", "Course Name", "Faculty ID", "Faculty Name");

        System.out.println("-----------------------------------------------------------------------------------");

        for (Enrollment e : enrollments) {

            Course c = courseDAO.getCourseById(e.getCourseId());
            Student s = studentDAO.getStudentById(e.getStudentId());
            Faculty f=facultyDAO.getFacultyById(c.getFacultyId());

            if (c != null && s != null) {
                System.out.printf("%-10d %-15s %-20s %-15d %-20s\n",
                        c.getCourseId(),
                        s.getName(),
                        c.getCourseName(),
                        c.getFacultyId(),
                        f.getName());
            }
        }
    }

 // ================= TIMETABLE =================

 // 🔥 Time mapping
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

	// 🔥 Student Timetable View
	public void viewTimetable(int studentId) {

		List<Enrollment> enrollments = enrollmentDao.getEnrollmentsByStudent(studentId);

		if (enrollments == null || enrollments.isEmpty()) {
			System.out.println("No enrolled courses found for timetable");
			return;
		}

		// ✅ Get student section
		String section = studentDAO.getStudentById(studentId).getSection();

		List<Timetable> list = timetableDAO.getAllTimetables();

		// ✅ Header
		System.out.printf("%-10s %-20s %-10s %-10s\n", "Day", "Period(Time)", "Room", "Course");

		System.out.println("----------------------------------------------------------");

		boolean found = false;

		for (Enrollment e : enrollments) {

			for (Timetable t : list) {

				if (t.getCourseId() == e.getCourseId() && t.getSection().equalsIgnoreCase(section)) {

					System.out.printf("%-10s %-20s %-10s %-10d\n", t.getDay(),
							"P" + t.getPeriod() + " (" + getTimeByPeriod(t.getPeriod()) + ")", // 🔥 FIX
							t.getRoom(), t.getCourseId());

					found = true;
				}
			}
		}

		if (!found) {
			System.out.println("No timetable available for your section");
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

         // Header
         System.out.printf("%-10s %-15s %-10s %-10s\n",
                 "Exam ID", "Marks", "Max Marks", "Grade");
         System.out.println("----------------------------------------------------------");

         for (Result r : courseResults) {
             Exam exam = examDAO.getExamById(r.getExamId());
             int maxMarks = (exam != null) ? exam.getMaxMarks() : 100;

             System.out.printf("%-10d %-15d %-10d %-10s\n",
                     r.getExamId(),
                     r.getMarks(),
                     maxMarks,
                     r.getGrade());

             totalMarks += r.getMarks();
             maxTotal += maxMarks;
         }

         // Footer
         System.out.println("----------------------------------------------------------");
         System.out.printf("TOTAL: %.0f / %.0f\n", totalMarks, maxTotal);
         System.out.printf("PERCENTAGE: %.2f%%\n", (maxTotal > 0 ? (totalMarks / maxTotal * 100) : 0));
        }
    }
    // ===================== ATTENDANCE =====================
    public void viewAttendance(int studentId) {

        Student s = studentDAO.getStudentById(studentId);
        if (s == null) {
            System.out.println("Student not found");
            return;
        }

        System.out.println("\n--- Attendance Report ---");
        System.out.println("Student: " + s.getName());

        List<Course> courses = courseDAO.getAllCourses();

        // ✅ Table Header
        System.out.printf("%-25s %-15s %-15s %-15s\n",
                "Course", "Total Classes", "Attended", "Percentage");
        System.out.println("--------------------------------------------------------------------------");

        boolean found = false;

        for (Course c : courses) {

            List<Attendance> records = attendanceDao.getAttendanceByStudentAndCourse(studentId, c.getCourseId());

            if (records.isEmpty()) continue; 

            int totalClasses = records.size();
            long attendedClasses = records.stream().filter(Attendance::isPresent).count();
            double percentage = totalClasses == 0 ? 0 :
                    ((double) attendedClasses / totalClasses) * 100;

            // ✅ Table Row
            System.out.printf("%-25s %-15d %-15d %-15.2f%%\n",
                    c.getCourseName(),
                    totalClasses,
                    attendedClasses,
                    percentage);

            found = true;
        }

        if (!found) {
            System.out.println("No attendance records available");
        }
    }

    // ===================== NOTIFICATIONS =====================
    public void viewNotifications(int studentId) {

        List<Notification> list = notificationDAO.getAllNotifications();
        boolean found = false;

        System.out.println("\n--- Notifications ---");

        // ✅ Table Header
        System.out.printf("%-5s %-50s %-20s\n",
                "ID", "Message", "Date");
        System.out.println("--------------------------------------------------------------------------");

        for (Notification n : list) {

            if (n.getTargetRole().equalsIgnoreCase("ALL") ||
                (n.getTargetRole().equalsIgnoreCase("STUDENT") &&
                 (n.getTargetId() == null || n.getTargetId() == studentId))) {

                // ✅ Trim long message (optional)
                String msg = n.getMessage();
                if (msg.length() > 45) {
                    msg = msg.substring(0, 45) + "...";
                }

                // ✅ Table Row
                System.out.printf("%-5d %-50s %-20s\n",
                        n.getNotificationId(),
                        msg,
                        n.getDate());

                found = true;
            }
        }

        if (!found) {
            System.out.println("No notifications available for you");
        }
    }

    // ===================== MATERIALS =====================
    public void viewMaterials(int courseId, int studentId, String role) {

        // ✅ Validate enrollment
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

        System.out.println("\n--- Course Materials ---");

        // ✅ Table Header
        System.out.printf("%-5s %-25s %-50s\n",
                "ID", "Title", "Content");
        System.out.println("--------------------------------------------------------------------------");

        for (Material m : materials) {
            if (m.getCourseId() == courseId) {

                // ✅ Trim long content (optional)
                String content = m.getContent();
                if (content.length() > 45) {
                    content = content.substring(0, 45) + "...";
                }

                // ✅ Table Row
                System.out.printf("%-5d %-25s %-50s\n",
                        m.getId(),
                        m.getTitle(),
                        content);

                found = true;
            }
        }

        if (!found) {
            System.out.println("No materials found for this course");
        }
    }

    // ===================== ASSIGNMENTS =====================
    public void viewAssignments(int courseId, int studentId, String role) {

        // ✅ Validate enrollment
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

        System.out.println("\n--- Assignments ---");

        // ✅ Table Header
        System.out.printf("%-5s %-25s %-50s\n",
                "ID", "Title", "Description");
        System.out.println("--------------------------------------------------------------------------");

        for (Assignment a : assignments) {
            if (a.getCourseId() == courseId) {

                // ✅ Trim long description (optional)
                String desc = a.getDescription();
                if (desc.length() > 45) {
                    desc = desc.substring(0, 45) + "...";
                }

                // ✅ Table Row
                System.out.printf("%-5d %-25s %-50s\n",
                        a.getId(),
                        a.getTitle(),
                        desc);

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