/*
 * Course DAO
 * Author: Jerishwin Joseph
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Course;

public class CourseDAO {

    // Add Course
    public void addCourse(int facultyId, String courseName) {

        String query = "INSERT INTO courses(course_name, faculty_id) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

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

    // Update Course
    public void updateCourse(int id, String courseName, int facultyId) {

        String query = "UPDATE courses SET course_name=?, faculty_id=? WHERE course_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, courseName);
            ps.setInt(2, facultyId);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Course updated successfully");
            } else {
                System.out.println("⚠️ Course not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete Course
    public void deleteCourse(int id) {

        String query = "DELETE FROM courses WHERE course_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);

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

    // Assign Course to Faculty
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

    // Get All Courses
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
                        rs.getInt("faculty_id")
                );

                courses.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return courses;
    }

    // Get Course By ID
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
                        rs.getInt("faculty_id")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Get Courses By Faculty ID
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