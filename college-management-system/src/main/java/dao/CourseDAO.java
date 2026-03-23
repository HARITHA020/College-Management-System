package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Course;

public class CourseDAO {

    // 🔹 ADD COURSE
    public void addCourse(String courseName, int facultyId) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO courses(course_name, faculty_id) VALUES (?, ?)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, courseName);
            ps.setInt(2, facultyId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Course added successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 UPDATE COURSE
    public void updateCourse(int id, String courseName, int facultyId) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE courses SET course_name=?, faculty_id=? WHERE course_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, courseName);
            ps.setInt(2, facultyId);
            ps.setInt(3, id);

            ps.executeUpdate();

            System.out.println("✅ Course updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 DELETE COURSE
    public void deleteCourse(int id) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "DELETE FROM courses WHERE course_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("✅ Course deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 GET ALL COURSES
    public List<Course> getAllCourses() {

        List<Course> courses = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM courses";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Course c = new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getInt("faculty_id")
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

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM courses WHERE course_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, courseId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getInt("faculty_id")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 🔹 GET COURSES BY FACULTY ID
    public List<Course> getCoursesByFacultyId(int facultyId) {

        List<Course> courses = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM courses WHERE faculty_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, facultyId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Course c = new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getInt("faculty_id")
                );

                courses.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return courses;
    }
}