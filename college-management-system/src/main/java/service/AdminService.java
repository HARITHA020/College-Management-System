package service;

import java.util.List;
import dao.AdminDAO;
import dao.CourseDAO;
import dao.ExamDAO;
import dao.TimetableDAO;
import model.*;

public class AdminService {

    private CourseDAO courseDao = new CourseDAO();
    private TimetableDAO timetableDao = new TimetableDAO();
    private AdminDAO adminDao = new AdminDAO();
    private ExamDAO examDao = new ExamDAO();

    public void addAdmin(int id, String name, String password) {
        adminDao.addAdmin(id, name, password);
        System.out.println("Admin Added Successfully");
    }

    public void updateAdmin(int id, String name, String password) {
        adminDao.updateAdmin(id, name, password);
        System.out.println("Admin Updated Successfully");
    }

    public void deleteAdmin(int id) {
        adminDao.deleteAdmin(id);
        System.out.println("Admin Deleted Successfully");
    }

    public void addCourse(int id, String name) {
        courseDao.addCourse(id, name);
        System.out.println("Course Added Successfully");
    }

    public void assignCourse(int courseId, int facultyId) {
        courseDao.assignCourse(courseId, facultyId); // FIXED
        System.out.println("Course Assigned Successfully");
    }

    public void addTimetable(int id, String day, String time, String room, int courseId) {
        timetableDao.addTimetable(id, day, time, room, courseId);
        System.out.println("Timetable Added Successfully");
    }

    public void scheduleExam(int examId, int courseId, String examDate) {
        examDao.addExam(examId, courseId, examDate);
        System.out.println("Exam Scheduled Successfully");
    }

    public void viewAdmins() {
        List<Administrator> admins = adminDao.getAllAdmins();
        for (Administrator admin : admins) {
            System.out.println("ID: " + admin.getId() + ", Name: " + admin.getName());
        }
    }

    public void viewCourse() {
        List<Course> courses = courseDao.getAllCourses();
        for (Course c : courses) {
            System.out.println("ID: " + c.getcourseId() +
                    ", Name: " + c.getcourseId() +
                    ", Faculty: " + c.getFacultyid());
        }
    }

    public void viewTimetable() {
        List<Timetable> list = timetableDao.getAllTimetables();
        for (Timetable t : list) {
            System.out.println("ID: " + t.getTime() +
                    ", Day: " + t.getDay() +
                    ", Time: " + t.getTime());
        }
    }

    public void viewSchedules() {
        List<Exam> exams = examDao.getAllExam();
        for (Exam e : exams) {
            System.out.println("Exam ID: " + e.getExamId() +
                    ", Course ID: " + e.getCourseId() +
                    ", Date: " + e.getExamDate());
        }
    }
}