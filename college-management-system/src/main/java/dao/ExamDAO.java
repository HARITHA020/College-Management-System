package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Exam;

public class ExamDAO {

    // 🔹 SCHEDULE EXAM
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

    // 🔹 UPDATE EXAM
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
    public void deleteExam(int examId) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "DELETE FROM exams WHERE exam_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, examId);

            ps.executeUpdate();

            System.out.println("✅ Exam deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    // 🔹 GET EXAM BY ID
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

    // 🔹 GET EXAMS BY COURSE ID
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