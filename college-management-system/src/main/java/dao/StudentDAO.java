/*
 * Student DAO
 * Author: Jerishwin Joseph
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Student;

public class StudentDAO {

    // Add Student
	public void addStudent(String name, String dob, String contact, String department, String section, int userId) {

	    String query = "INSERT INTO students(name, dob, contact, department, section, user_id) VALUES (?, ?, ?, ?, ?, ?)";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(query)) {

	        ps.setString(1, name);
	        ps.setDate(2, Date.valueOf(dob));
	        ps.setString(3, contact);
	        ps.setString(4, department);
	        ps.setString(5, section); 
	        ps.setInt(6, userId);

	        int rows = ps.executeUpdate();

	        if (rows > 0) {
	            System.out.println("✅ Student added successfully");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

    // 🔹 UPDATE STUDENT
	public boolean updateStudentField(int studentId, String field, String value) {

	    // ⚠️ Allow only valid fields to prevent SQL injection
	    if (!(field.equals("name") || field.equals("dob") || field.equals("contact") || 
	          field.equals("department") || field.equals("section"))) {
	        System.out.println("Invalid field: " + field);
	        return false;
	    }

	    String sql = "UPDATE students SET " + field + " = ? WHERE student_id = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        // Handle DOB as Date
	        if (field.equals("dob")) {
	            ps.setDate(1, Date.valueOf(value)); // value must be in yyyy-MM-dd format
	        } else {
	            ps.setString(1, value);
	        }

	        ps.setInt(2, studentId);

	        return ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return false;
	}

    // Delete Student
    public void deleteStudent(int id) {

        String query = "DELETE FROM students WHERE student_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Student deleted successfully");
            } else {
                System.out.println("⚠️ Student not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get All Students
    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        String query = "SELECT * FROM students";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

            	Student s = new Student(
            	        rs.getInt("student_id"),
            	        rs.getString("name"),
            	        rs.getString("dob"),
            	        rs.getString("contact"),
            	        rs.getInt("user_id"),
            	        rs.getString("department"),
            	        rs.getString("section")  
            	       
            	);
                students.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    // Get Student by ID
    public Student getStudentById(int id) {

        String query = "SELECT * FROM students WHERE student_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            	return new Student(
            			 rs.getInt("student_id"),
             	        rs.getString("name"),
             	        rs.getString("dob"),
             	        rs.getString("contact"),
             	        rs.getInt("user_id"),
             	        rs.getString("department"),
             	        rs.getString("section")  
            	);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    // Get Student ID by User ID
    public Student getStudentIdByUserId(int userId) {

        Student student = null;

        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT * FROM students WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                student = new Student(
                		 rs.getInt("student_id"),
             	        rs.getString("name"),
             	        rs.getString("dob"),
             	        rs.getString("contact"),
             	        rs.getInt("user_id"),
             	        rs.getString("department"),
             	        rs.getString("section")  
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return student;
    }

    // get Student by User ID
    public Student getStudentByUserId(int userId) {

        String query = "SELECT * FROM students WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Student(
                		 rs.getInt("student_id"),
             	        rs.getString("name"),
             	        rs.getString("dob"),
             	        rs.getString("contact"),
             	        rs.getInt("user_id"),
             	        rs.getString("department"),
             	        rs.getString("section")  
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Get User ID by Student ID
    public int getUserIdByStudentId(int studentId) {

        String query = "SELECT user_id FROM students WHERE student_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    public boolean isStudentExists(int studentId) {
        String query = "SELECT COUNT(*) FROM students WHERE student_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}