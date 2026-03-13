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
	
}
