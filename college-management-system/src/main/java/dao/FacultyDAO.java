package dao;

import java.util.ArrayList;
import java.util.List;
import model.Student;

public class FacultyDAO {

    private List<Student> students = new ArrayList<>();

    public List<Student> getAllStudents() {
        return students;
    }

}