package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Result;

public class ResultDAO {

    // Add result to DB
    public void addResult(Result r) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO results(student_id, exam_id, marks, grade, published) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, r.getStudentId());
            ps.setInt(2, r.getCourseId());
            ps.setInt(3, r.getMarks());
            ps.setString(4, r.getGrade());
            ps.setBoolean(5, r.isPublished());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all results
    public List<Result> getAllResults() {
        List<Result> results = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM results";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Result r = new Result(
                        rs.getInt("result_id"),
                        rs.getInt("student_id"),
                        rs.getInt("exam_id"),
                        rs.getInt("marks"),
                        rs.getString("grade")
                );
                r.setPublished(rs.getBoolean("published"));
                results.add(r);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // Get result by ID
    public Result getResultById(int resultId) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM results WHERE result_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, resultId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Result r = new Result(
                        rs.getInt("result_id"),
                        rs.getInt("student_id"),
                        rs.getInt("exam_id"),
                        rs.getInt("marks"),
                        rs.getString("grade")
                );
                r.setPublished(rs.getBoolean("published"));
                rs.close();
                ps.close();
                return r;
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get results by student
    public List<Result> getResultsByStudent(int studentId) {
        List<Result> results = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM results WHERE student_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Result r = new Result(
                        rs.getInt("result_id"),
                        rs.getInt("student_id"),
                        rs.getInt("exam_id"),
                        rs.getInt("marks"),
                        rs.getString("grade")
                );
                r.setPublished(rs.getBoolean("published"));
                results.add(r);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // Get results by course
    public List<Result> getResultsByCourse(int courseId) {
        List<Result> results = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM results WHERE exam_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Result r = new Result(
                        rs.getInt("result_id"),
                        rs.getInt("student_id"),
                        rs.getInt("exam_id"),
                        rs.getInt("marks"),
                        rs.getString("grade")
                );
                r.setPublished(rs.getBoolean("published"));
                results.add(r);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}