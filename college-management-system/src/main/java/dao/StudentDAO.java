package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Student;

public class StudentDAO {

    // 🔹 ADD STUDENT (AUTO_INCREMENT → no ID)
	public void addStudent(String name, String dob, String contact, String department, String section, int userId) {

	    String query = "INSERT INTO students(name, dob, contact, department, section, user_id) VALUES (?, ?, ?, ?, ?, ?)";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(query)) {

	        ps.setString(1, name);
	        ps.setDate(2, Date.valueOf(dob));
	        ps.setString(3, contact);
	        ps.setString(4, department);
	        ps.setString(5, section);   // ✅ NEW
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
	public void updateStudent(int id, String name, String dob, String contact, String department, String section) {

	    String query = "UPDATE students SET name=?, dob=?, contact=?, department=?, section=? WHERE student_id=?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(query)) {

	        ps.setString(1, name);
	        ps.setDate(2, Date.valueOf(dob));
	        ps.setString(3, contact);
	        ps.setString(4, department);
	        ps.setString(5, section);  // ✅ NEW
	        ps.setInt(6, id);

	        int rows = ps.executeUpdate();

	        if (rows > 0) {
	            System.out.println("✅ Student updated successfully");
	        } else {
	            System.out.println("⚠️ Student not found");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

    // 🔹 DELETE STUDENT
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

    // 🔹 GET ALL STUDENTS
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
            	        rs.getString("department"),
            	        rs.getString("dob"),
            	        rs.getString("contact"),
            	        rs.getString("section"),   // ✅ NEW
            	        rs.getInt("user_id")
            	);
                students.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    // 🔹 GET STUDENT BY ID (IMPORTANT)
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
            	        rs.getString("department"),
            	        rs.getString("dob"),
            	        rs.getString("contact"),
            	        rs.getString("section"),   // ✅ NEW
            	        rs.getInt("user_id")
            	);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
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
                    rs.getString("department"),
                    rs.getString("dob"),
                    rs.getString("contact"),
                    rs.getString("section"),
                    rs.getInt("user_id")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return student;
    }

    // 🔹 GET STUDENT BY USER ID
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
                        rs.getString("department"),
                        rs.getString("section"),
                        rs.getInt("user_id")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 🔹 GET USER ID BY STUDENT ID
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
}