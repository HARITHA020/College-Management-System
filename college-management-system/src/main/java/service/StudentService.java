package service;

import java.util.List;
import dao.StudentDAO;
import model.Student;
public class StudentService {
	private StudentDAO studentDAO = new StudentDAO();
	
	public void addStudent(int id, String name, String department) {
		
		if (name == null || name.isEmpty()) {
			System.out.println("Invalid student name");
			return;
		}
		
		Student student = new Student(id, name, department);
		studentDAO.addStudent(student);
		System.out.println("Student added successfully");
	}
	

    public void updateStudent(int id, String name, String department) {

        studentDAO.updateStudent(id, name, department);

        System.out.println("Student Updated Successfully");
    }

    public void deleteStudent(int id) {

        studentDAO.deleteStudent(id);

        System.out.println("Student Deleted Successfully");
    }
	
	public void viewStudents() {
		
		List<Student> students = studentDAO.getAllStudents();
		
		for (Student s : students) {
			System.out.println(s.getId() + " " + s.getName() + " " + s.getDepartment());
		}
	}
}
