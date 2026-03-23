package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Enrollment;

public class EnrollmentDAO {

    // 🔹 ENROLL STUDENT
    public void enrollStudent(int studentId, int courseId) {

        try (Connection con = DBConnection.getConnection()) {

            // ✅ Check duplicate
            String check = "SELECT * FROM enrollments WHERE student_id=? AND course_id=?";
            PreparedStatement ps1 = con.prepareStatement(check);
            ps1.setInt(1, studentId);
            ps1.setInt(2, courseId);

            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                System.out.println("⚠ Student already enrolled");
                return;
            }

            // ✅ Insert
            String query = "INSERT INTO enrollments(student_id, course_id) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            ps.executeUpdate();

            System.out.println("✅ Student assigned to course");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 GET ALL ENROLLMENTS
    public List<Enrollment> getAllEnrollments() {

        List<Enrollment> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT * FROM enrollments";
            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Enrollment e = new Enrollment(
                        rs.getInt("enrollment_id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id")
                );
                list.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔹 GET BY STUDENT
    public List<Enrollment> getEnrollmentsByStudent(int studentId) {

        List<Enrollment> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT * FROM enrollments WHERE student_id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Enrollment(
                        rs.getInt("enrollment_id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔹 GET BY COURSE
    public List<Enrollment> getEnrollmentsByCourse(int courseId) {

        List<Enrollment> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT * FROM enrollments WHERE course_id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, courseId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Enrollment(
                        rs.getInt("enrollment_id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔹 DELETE ENROLLMENT
    public void deleteEnrollment(int studentId, int courseId) {

        try (Connection con = DBConnection.getConnection()) {

            String query = "DELETE FROM enrollments WHERE student_id=? AND course_id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Enrollment removed");
            } else {
                System.out.println("⚠ Enrollment not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}