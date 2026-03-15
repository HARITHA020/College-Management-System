package service;

import java.util.List;
import dao.StudentDAO;
import model.Student;

public class FacultyService {

    private StudentDAO studentDAO = new StudentDAO();

    public void viewStudents() {

        List<Student> students = studentDAO.getAllStudents();

        for (Student s : students) {
            System.out.println(s.getId() + " " + s.getName() + " " + s.getDepartment());
        }
    }

    public void viewAttendance() {
        System.out.println("Viewing student attendance records");
    }

    public void viewMarks() {
        System.out.println("Viewing student marks");
    }

    public void viewTimeTable() {
        System.out.println("Viewing faculty timetable");
    }
}
