package dao;

import java.util.ArrayList;
import java.util.List;

import model.Administrator;
import model.Student;

public class StudentDAO {
	
	private List <Student> students = new ArrayList<>();
	
	public void addStudent(Student student) {
		students.add(student);
		System.out.println("Student added: " + student.getName());
	}
	
	public List<Student> getAllStudents(){
		students.add(new Student(101,"Balamurugan","CSE"));
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
	
	public Student getStudentById(int id) {
        for (Student s : students) {
            if (s.getId() == id) return s;
        }
        return null;
    }
	
	
	
}
