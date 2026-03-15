package service;

import java.util.List;

import dao.FacultyDAO;
import dao.StudentDAO;
import dao.TimetableDAO;
import dao.ExamDAO;

import model.Faculty;
import model.Student;
import model.Timetable;
import model.Exam;

public class FacultyService {

    private FacultyDAO facultyDAO;
    private StudentDAO studentDAO;
    private TimetableDAO timetableDAO;
    private ExamDAO examDAO;

    public FacultyService() {
        facultyDAO = new FacultyDAO();
        studentDAO = new StudentDAO();
        timetableDAO = new TimetableDAO();
        examDAO = new ExamDAO();
    }

    // ── Faculty Management ─────────────────────────

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

    // ── Students ─────────────────────────

    public void viewStudents() {

        List<Student> students = studentDAO.getAllStudents();

        System.out.println("Student Details:");

        for (Student s : students) {
            System.out.println(
                "Student Id: " + s.getId() +
                "\nStudent Name: " + s.getName() +
                "\nDepartment: " + s.getDepartment()
            );
        }
    }

    // ── Marks ─────────────────────────

    public void viewMarks() {

        List<Exam> exams = examDAO.getAllExams();

        System.out.println("Exam / Marks Records:");

        for (Exam e : exams) {
            System.out.println(
                "Exam Id: " + e.getExamId() +
                " | Course Id: " + e.getCourseId() +
                " | Exam Date: " + e.getExamDate()
            );
        }
    }

    // ── Timetable ─────────────────────────

    public void viewTimeTable() {

        List<Timetable> timetables = timetableDAO.getAllTimetables();

        System.out.println("Faculty Timetable:");

        for (Timetable t : timetables) {
            System.out.println(
                "Day: " + t.getDay() +
                " | Time: " + t.getTime() +
                " | Room: " + t.getRoom()
            );
        }
    }
}