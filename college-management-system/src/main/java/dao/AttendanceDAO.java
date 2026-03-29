package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Attendance;

public class AttendanceDAO {

    // ===================== INSERT =====================
    public void markAttendance(Attendance att) {

        try {
            Connection con = DBConnection.getConnection();

            // 🔥 Check duplicate
            if (isAttendanceMarked(
                    att.getStudentId(),
                    att.getCourseId(),
                    new java.sql.Date(att.getDate().getTime()))) {

                System.out.println("⚠️ Attendance already marked for this date");
                return;
            }

            String query = "INSERT INTO attendance(student_id, course_id, attendance_date, present) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, att.getStudentId());
            ps.setInt(2, att.getCourseId());
            ps.setDate(3, new java.sql.Date(att.getDate().getTime()));
            ps.setBoolean(4, att.isPresent()); // ✅ BOOLEAN (0/1)

            ps.executeUpdate();

            System.out.println("✅ Attendance stored successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // deletion
    public void deleteByStudentId(int studentId) {
        String query = "DELETE FROM attendance WHERE student_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, studentId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===================== GET BY COURSE =====================
    public List<Attendance> getAttendanceByCourse(int courseId) {

        List<Attendance> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM attendance WHERE course_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, courseId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                boolean present = rs.getBoolean("present");

                Attendance att = new Attendance(
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getDate("attendance_date"),
                        present
                );

                list.add(att);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===================== GET BY STUDENT =====================
    public List<Attendance> getAttendanceByStudent(int studentId) {

        List<Attendance> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM attendance WHERE student_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                boolean present = rs.getBoolean("present");

                Attendance att = new Attendance(
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getDate("attendance_date"),
                        present
                );

                list.add(att);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===================== GET BY STUDENT + COURSE =====================
    public List<Attendance> getAttendanceByStudentAndCourse(int studentId, int courseId) {

        List<Attendance> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM attendance WHERE student_id=? AND course_id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                boolean present = rs.getBoolean("present");

                Attendance att = new Attendance(
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getDate("attendance_date"),
                        present
                );

                list.add(att);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===================== DUPLICATE CHECK =====================
    public boolean isAttendanceMarked(int studentId, int courseId, java.sql.Date date) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT 1 FROM attendance WHERE student_id=? AND course_id=? AND attendance_date=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.setDate(3, date);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}