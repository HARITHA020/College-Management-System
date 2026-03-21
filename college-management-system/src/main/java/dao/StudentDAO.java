package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Administrator;
import model.Student;

public class StudentDAO {
	
	private List <Student> students = new ArrayList<>();
	
	public void addStudent(Student student) {
		students.add(student);
		System.out.println("Student added: " + student.getName());
	}
	
	public List<Student> getAllStudents(){
	    students.add(new Student(101,"Balamurugan","CSE","2000-05-10","9876543210",100001)); // include DOB & contact
	    return students;
	}

	public void updateStudent(int id, String name, String department, String dob, String contact) {
	    for(Student student : students) {
	        if(student.getId() == id) {
	            student.setName(name);
	            student.setDepartment(department);
	            student.setDob(dob);          // NEW
	            student.setContact(contact); 
	            
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
	
	public Student getStudentByUserId(int userId) {
	    for (Student student : students) {
	        if (student.getUserId() == userId) return student;
	    }
	    return null;
	}
	
	
	public int getUserIdByStudentId(int id) {

	    try {
	        Connection con = DBConnection.getConnection();

	        String query = "SELECT user_id FROM students WHERE student_id=?";
	        PreparedStatement ps = con.prepareStatement(query);

	        ps.setInt(1, id);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getInt("user_id");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return -1;
	}
	
}
