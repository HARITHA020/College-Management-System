/*
 * Student Services
 * Author: Jerishwin Joseph
 * contains all the business logic related to student operations, including 
 * CRUD, course management, timetable, marks, attendance, 
 * notifications, materials, assignments, and library interactions.
 */
package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

		studentDAO.addStudent(name, dob, contact, department, section, userId);
	}
	public void updateStudent(int studentId, String field, String value) {

	    if (studentId <= 0) {
	        System.out.println("Invalid Student ID");
	        return;
	    }

	    boolean updated = studentDAO.updateStudentField(studentId, field, value);

	    if (updated) {
	        System.out.println("✅ Student updated successfully");
	    } else {
	        System.out.println("❌ Update failed or field not valid");
	    }
	}

	public void deleteStudent(int id) {

	    int userId = studentDAO.getUserIdByStudentId(id);

	    if (userId == -1) {
	        System.out.println("Student not found");
	        return;
	    }

	    try {
	        enrollmentDao.deleteByStudentId(id); 
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

        System.out.printf("%-5s %-20s %-15s %-10s %-12s %-15s\n",
                "ID", "Name", "Department", "Section", "DOB", "Contact");

        System.out.println("--------------------------------------------------------------------------------");

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

//🔥 Grid-style Timetable with proper alignment
	public void viewTimetable(int studentId) {

		List<Enrollment> enrollments = enrollmentDao.getEnrollmentsByStudent(studentId);

		if (enrollments == null || enrollments.isEmpty()) {
			System.out.println("No enrolled courses found for timetable");
			return;
		}

		String section = studentDAO.getStudentById(studentId).getSection();
		List<Timetable> timetables = timetableDAO.getAllTimetables();

		String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		int periods = 6;

		int colWidth = 20; // ✅ Increased width for alignment

		// Header
		System.out.printf("%-10s", "Day");
		for (int p = 1; p <= periods; p++) {
			System.out.printf("| P%d (%s) ", p, getTimeByPeriod(p), "");
		}
		System.out.println();

		// Separator
		for (int i = 0; i < 10 + periods * (colWidth + 3); i++)
			System.out.print("-");
		System.out.println();

		// Rows
		for (String day : days) {
			System.out.printf("%-10s", day);

			for (int p = 1; p <= periods; p++) {
				String courseInfo = "-";

				for (Timetable t : timetables) {
					boolean enrolled = enrollments.stream().anyMatch(en -> en.getCourseId() == t.getCourseId());

					if (t.getDay().equalsIgnoreCase(day) && t.getPeriod() == p
							&& t.getSection().equalsIgnoreCase(section) && enrolled) {

						Course c = courseDAO.getCourseById(t.getCourseId());
						courseInfo = "C" + t.getCourseId() + ":" + c.getCourseName();
						break;
					}
				}

				System.out.printf("| %-18s", courseInfo); // ✅ Fixed width
			}
			System.out.println();
		}
	}
    // ===================== MARKS / EXAMS =====================
	public void viewMarks(int studentId) {

	    Scanner scanner = new Scanner(System.in);

	    // ===== GET STUDENT =====
	    Student student = studentDAO.getStudentById(studentId);
	    if (student == null) {
	        System.out.println("❌ Student not found!");
	        return;
	    }

	    List<Result> results = resultDAO.getResultsByStudent(studentId);
	    if (results == null || results.isEmpty()) {
	        System.out.println("❌ No results available for this student.");
	        return;
	    }

	    // ===== ASK SEMESTER =====
	    System.out.print("Enter Semester (1-6): ");
	    int selectedSem = scanner.nextInt();

	    // ✅ SEM VALIDATION
	    if (selectedSem < 1 || selectedSem > 6) {
	        System.out.println("❌ Invalid semester! Please enter between 1 and 6.");
	        return;
	    }

	    String line = "========================================================================================================";

	    // ===== HEADER =====
	    System.out.println(line);
	    System.out.println("Student Id   : " + student.getId());
	    System.out.println("Student Name : " + student.getName());
	    System.out.println("Year         : " + student.getSection());
	    System.out.println("Semester     : " + selectedSem);
	    System.out.println(line);

	    // ===== TABLE HEADER =====
	    System.out.printf("%-10s %-25s %-7s %-10s %-7s %-7s %-10s\n",
	            "Exam ID", "Course Name", "Marks", "Max Marks", "Grade", "Credit", "Status");
	    System.out.println("--------------------------------------------------------------------------------------------------------");

	    double totalMarks = 0;
	    double maxTotal = 0;
	    int totalCredits = 0;
	    double totalGpaPoints = 0;

	    boolean isFail = false;
	    boolean semFound = false;

	    // ===== GPA LOOP (ONLY SELECTED SEM) =====
	    for (Result r : results) {
	        if (!r.isPublished()) continue;

	        Exam exam = examDAO.getExamById(r.getExamId());
	        if (exam == null) continue;

	        Course course = courseDAO.getCourseById(exam.getCourseId());
	        if (course == null) continue;

	        // 👉 FILTER SEMESTER
	        if (course.getSemester() != selectedSem) continue;

	        semFound = true;

	        String courseName = course.getCourseName();
	        int credit = course.getCredits();
	        int maxMarks = exam.getMaxMarks();

	        // ✅ SUBJECT STATUS
	        String subjectStatus = (r.getMarks() >= 40) ? "PASS" : "FAIL";

	        System.out.printf("%-10d %-25s %-7d %-10d %-7s %-7d %-10s\n",
	                r.getExamId(),
	                courseName,
	                r.getMarks(),
	                maxMarks,
	                r.getGrade(),
	                credit,
	                subjectStatus);

	        totalMarks += r.getMarks();
	        maxTotal += maxMarks;
	        totalCredits += credit;

	        // OVERALL FAIL CHECK
	        if (r.getMarks() < 40) {
	            isFail = true;
	        }

	        double gpaPoint = getGpaFromGrade(r.getGrade());
	        totalGpaPoints += gpaPoint * credit;
	    }

	    // ❌ NO DATA FOR SEM
	    if (!semFound) {
	        System.out.println("❌ Semester not found or no results available.");
	        return;
	    }

	    System.out.println("--------------------------------------------------------------------------------------------------------");

	    double gpa = (totalCredits > 0) ? totalGpaPoints / totalCredits : 0;

	    // ===== CGPA LOOP =====
	    double cgpaPoints = 0;
	    int cgpaCredits = 0;

	    for (Result r : results) {
	        if (!r.isPublished()) continue;

	        Exam exam = examDAO.getExamById(r.getExamId());
	        if (exam == null) continue;

	        Course course = courseDAO.getCourseById(exam.getCourseId());
	        if (course == null) continue;

	        if (course.getSemester() > selectedSem) continue;

	        int credit = course.getCredits();
	        double gpaPoint = getGpaFromGrade(r.getGrade());

	        cgpaPoints += gpaPoint * credit;
	        cgpaCredits += credit;
	    }

	    double cgpa = (cgpaCredits > 0) ? cgpaPoints / cgpaCredits : 0;

	    // ===== OVERALL STATUS =====
	    String status = isFail ? "FAIL ❌" : "PASS ✅";

	    // ===== FOOTER =====
	    System.out.println("Total Credits : " + totalCredits);
	    System.out.printf("GPA           : %.2f\n", gpa);
	    System.out.printf("CGPA          : %.2f\n", cgpa);
	    System.out.println("Overall Status: " + status);
	    System.out.println(line);
	}


	// ===== HELPER METHOD =====
	private double getGpaFromGrade(String grade) {
	    switch (grade) {
	        case "A+": return 10;
	        case "A":  return 9;
	        case "B+": return 8;
	        case "B":  return 7;
	        case "C+": return 6;
	        case "C":  return 5;
	        default:   return 0;
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

        // Table Header
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

            // Table Row
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

        // Table Header
        System.out.printf("%-5s %-50s %-20s\n",
                "ID", "Message", "Date");
        System.out.println("--------------------------------------------------------------------------");

        for (Notification n : list) {

            if (n.getTargetRole().equalsIgnoreCase("ALL") ||
                (n.getTargetRole().equalsIgnoreCase("STUDENT") &&
                 (n.getTargetId() == null || n.getTargetId() == studentId))) {

                // Trim long message (optional)
                String msg = n.getMessage();
                if (msg.length() > 45) {
                    msg = msg.substring(0, 45) + "...";
                }

                // Table Row
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

        System.out.println("\n--- Course Materials ---");

        // Table Header
        System.out.printf("%-5s %-25s %-50s\n",
                "ID", "Title", "Content");
        System.out.println("--------------------------------------------------------------------------");

        for (Material m : materials) {
            if (m.getCourseId() == courseId) {

                // Trim long content 
                String content = m.getContent();
                if (content.length() > 45) {
                    content = content.substring(0, 45) + "...";
                }

                // Table Row
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

        System.out.println("\n--- Assignments ---");

        // Table Header
        System.out.printf("%-5s %-25s %-50s\n",
                "ID", "Title", "Description");
        System.out.println("--------------------------------------------------------------------------");

        for (Assignment a : assignments) {
            if (a.getCourseId() == courseId) {

                //  Trim long description (optional)
                String desc = a.getDescription();
                if (desc.length() > 45) {
                    desc = desc.substring(0, 45) + "...";
                }

                // Table Row
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