package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Student;

public class StudentDAO {

    // 🔹 ADD STUDENT
    public void addStudent(String name, String dob, String contact, String department, int userId) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO students(name, dob, contact, department, user_id) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, dob);
            ps.setString(3, contact);
            ps.setString(4, department);
            ps.setInt(5, userId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Student added successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 UPDATE STUDENT
    public void updateStudent(int id, String name, String dob, String contact, String department) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE students SET name=?, dob=?, contact=?, department=? WHERE student_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, dob);
            ps.setString(3, contact);
            ps.setString(4, department);
            ps.setInt(5, id);

            ps.executeUpdate();

            System.out.println("✅ Student updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 DELETE STUDENT
    public void deleteStudent(int id) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "DELETE FROM students WHERE student_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("✅ Student deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 GET ALL STUDENTS
    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM students";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("dob"),
                        rs.getString("contact"),
                        rs.getString("department"),
                        rs.getInt("user_id")
                );

                students.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    // 🔹 GET STUDENT BY USER ID
    public Student getStudentByUserId(int userId) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM students WHERE user_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("dob"),
                        rs.getString("contact"),
                        rs.getString("department"),
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

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT user_id FROM students WHERE student_id=?";

            PreparedStatement ps = con.prepareStatement(query);
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