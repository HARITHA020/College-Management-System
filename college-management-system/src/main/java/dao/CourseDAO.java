package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Course;

public class CourseDAO {

    // 🔹 ADD COURSE
    public void addCourse(String name, int credits, String duration, String department, int facultyId, String description) {
        String query = "INSERT INTO courses(course_name, credits, duration, department, faculty_id, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setInt(2, credits);
            ps.setString(3, duration);
            ps.setString(4, department);
            ps.setInt(5, facultyId);
            ps.setString(6, description);

            ps.executeUpdate();
            System.out.println("✅ Course added successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateCourseField(int courseId, String field, String value) {

        // ⚠️ Only allow valid fields to prevent SQL injection
        if (!(field.equals("course_name") || field.equals("credits") || field.equals("duration") ||
              field.equals("department") || field.equals("faculty_id") || field.equals("description"))) {
            return false;
        }

        String sql = "UPDATE courses SET " + field + " = ? WHERE course_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Handle integer fields
            if (field.equals("credits") || field.equals("faculty_id")) {
                ps.setInt(1, Integer.parseInt(value));
            } else {
                ps.setString(1, value);
            }

            ps.setInt(2, courseId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 🔹 DELETE COURSE
    public void deleteCourse(int courseId) {
        String query = "DELETE FROM courses WHERE course_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, courseId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Course deleted successfully");
            } else {
                System.out.println("⚠️ Course not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 GET ALL COURSES
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Course c = new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getInt("faculty_id"),
                        rs.getInt("credits"),
                        rs.getString("duration"),
                        rs.getString("department"),
                        rs.getString("description")
                );
                courses.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    // 🔹 GET COURSE BY ID
    public Course getCourseById(int courseId) {
        String query = "SELECT * FROM courses WHERE course_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getInt("faculty_id"),
                        rs.getInt("credits"),
                        rs.getString("duration"),
                        rs.getString("department"),
                        rs.getString("description")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔹 ASSIGN COURSE TO FACULTY
    public void assignCourse(int courseId, int facultyId) {
        String query = "UPDATE courses SET faculty_id=? WHERE course_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, facultyId);
            ps.setInt(2, courseId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Course assigned to faculty");
            } else {
                System.out.println("⚠️ Course not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 GET COURSES BY FACULTY ID
    public List<Course> getCoursesByFacultyId(int facultyId) {

        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses WHERE faculty_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, facultyId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Course c = new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getInt("credits"),
                        rs.getInt("faculty_id"),
                        rs.getString("department"),
                        rs.getString("duration"),
                        rs.getString("description")
                );

                courses.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return courses;
    }
}