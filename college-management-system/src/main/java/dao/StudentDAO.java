package dao;

import java.util.ArrayList;
import java.util.List;

import model.Administrator;
import model.Student;

public class StudentDAO {
	
	private List <Student> students = new ArrayList<>();
	
	public void addStudent(Student student) {
		students.add(student);
	}
	
	public List<Student> getAllStudents(){
		return students;
	}

	public void updateStudent(int id, String name, String department) {
		for(Student student : students) {

	        if(student.getId() == id) {

	            student.setName(name);
	            student.setDepartment(department);

	            System.out.println("Student Updated Successfully");
	        }
	    }
	}
	
	public void deleteStudent(int id) {
		students.removeIf(student -> student.getId() == id);
	}
	
}
