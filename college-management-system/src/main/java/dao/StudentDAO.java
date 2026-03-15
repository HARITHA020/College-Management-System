package dao;

import java.util.ArrayList;
import java.util.List;
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
		for(Student students : students) {

	        if(students.getId() == id) {

	            students.setName(name);
	            students.setDepartment(department);

	            System.out.println("Student Updated Successfully");
	        }
	    }
	}
	
	public void deleteStudent(int id) {
		for(Student student: students) {
			if(student.getId() ==id) {
				students.remove(id);
			}
		}
	}
	
}
