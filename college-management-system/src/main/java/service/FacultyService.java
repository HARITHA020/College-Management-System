package service;

import java.util.List;

import dao.FacultyDAO;
import dao.StudentDAO;
import dao.ExamDAO;

import model.Faculty;
import model.Student;
import model.Exam;

public class FacultyService {

    private FacultyDAO facultyDAO;
    private StudentDAO studentDAO;
    private ExamDAO examDAO;

    public FacultyService() {
        facultyDAO = new FacultyDAO();
        studentDAO = new StudentDAO();
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

    
    // ── Marks ─────────────────────────

    public void viewMarks() {

        List<Exam> exams = examDAO.getAllExam();

        System.out.println("Exam / Marks Records:");

        for (Exam e : exams) {
            System.out.println(
                "Exam Id: " + e.getExamId() +
                " | Course Id: " + e.getCourseId() +
                " | Exam Date: " + e.getExamDate()
            );
        }
    }

   

    
    
}