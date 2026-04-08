/*
 * Exam DAO
 * Author: Jerishwin Joseph
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Exam;

public class ExamDAO {

    // Schedule Exam
    public void scheduleExam(int courseId, String examDate, int maxMarks) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO exams(course_id, exam_date, max_marks) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, courseId);
            ps.setString(2, examDate);
            ps.setInt(3, maxMarks);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Exam scheduled successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // update Exam
    public void updateExam(int examId, int courseId, String examDate, int maxMarks) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE exams SET course_id=?, exam_date=?, max_marks=? WHERE exam_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, courseId);
            ps.setString(2, examDate);
            ps.setInt(3, maxMarks);
            ps.setInt(4, examId);

            ps.executeUpdate();

            System.out.println("✅ Exam updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 DELETE EXAM

    public boolean deleteExamById(int id) {

        String sql = "DELETE FROM exams WHERE exam_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

 // UPDATE
    public boolean updateExamField(int id, String field, String value) {

        // ✅ only allow valid fields
        if (!(field.equals("course_id") || field.equals("exam_date") || field.equals("max_marks"))) {
            return false;
        }

        String sql = "UPDATE exams SET " + field + " = ? WHERE exam_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (field.equals("course_id") || field.equals("max_marks")) {
                ps.setInt(1, Integer.parseInt(value));
            } else {
                ps.setString(1, value);
            }

            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    // 🔹 GET ALL EXAMS
    public List<Exam> getAllExams() {

        List<Exam> exams = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM exams";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Exam e = new Exam(
                        rs.getInt("exam_id"),
                        rs.getInt("course_id"),
                        rs.getString("exam_date"),
                        rs.getInt("max_marks")
                );

                exams.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exams;
    }

    // Get Exam by ID
    public Exam getExamById(int examId) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM exams WHERE exam_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, examId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Exam(
                        rs.getInt("exam_id"),
                        rs.getInt("course_id"),
                        rs.getString("exam_date"),
                        rs.getInt("max_marks")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Get Exams by Course ID
    public List<Exam> getExamsByCourseId(int courseId) {

        List<Exam> exams = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM exams WHERE course_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, courseId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Exam e = new Exam(
                        rs.getInt("exam_id"),
                        rs.getInt("course_id"),
                        rs.getString("exam_date"),
                        rs.getInt("max_marks")
                );

                exams.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exams;
    }
}