package dao;

import java.util.ArrayList;
import java.util.List;
import model.Student;

public class StudentDAO {
	
	private List <Student> students = new ArrayList<>();
	
	public void addStudent(Student student) {
		students.add(student);
		System.out.println("Student added: " + student.getName());
	}
	
	public List<Student> getAllStudents(){
		return students;
	}
	
	public Student getStudentById(int id) {
        for (Student s : students) {
            if (s.getId() == id) return s;
        }
        return null;
    }
	
	public void updateStudent(Student updated) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == updated.getId()) {
                students.set(i, updated);
                System.out.println("Student updated: " + updated.getName());
                return;
            }
        }
        System.out.println("Student not found with ID: " + updated.getId());
    }
	
	public void deleteStudent(int id) {
        boolean removed = students.removeIf(s -> s.getId() == id);
        if (removed) System.out.println("Student deleted with ID: " + id);
        else System.out.println("Student not found with ID: " + id);
    }
	
}
