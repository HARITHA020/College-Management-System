package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Attendance;

public class AttendanceDAO {

    // ✅ INSERT ATTENDANCE
    public void markAttendance(Attendance att) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO attendance(student_id, course_id, date, status) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, att.getStudentId());
            ps.setInt(2, att.getCourseId());

            // Convert java.util.Date → java.sql.Date
            ps.setDate(3, new java.sql.Date(att.getDate().getTime()));

            // Convert boolean → String
            String status = att.isPresent() ? "PRESENT" : "ABSENT";
            ps.setString(4, status);

            ps.executeUpdate();

            System.out.println("✅ Attendance stored successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ GET ATTENDANCE BY COURSE
    public List<Attendance> getAttendanceByCourse(int courseId) {

        List<Attendance> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM attendance WHERE course_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, courseId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                boolean present = rs.getString("status").equalsIgnoreCase("PRESENT");

                Attendance att = new Attendance(
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getDate("date"),
                        present
                );

                list.add(att);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ CHECK DUPLICATE (VERY IMPORTANT 🔥)
    public boolean isAttendanceMarked(int studentId, int courseId, java.sql.Date date) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT 1 FROM attendance WHERE student_id=? AND course_id=? AND date=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.setDate(3, date);

            ResultSet rs = ps.executeQuery();

            return rs.next(); // true if already exists

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}