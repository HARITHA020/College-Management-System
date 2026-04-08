/*
 * Author: Haritha
 * Dao helps to retrieve the the data form the mysql using the jdbc query commands
*/



package dao;

import java.sql.*;
import java.util.*;
import db.DBConnection;
import model.Timetable;

public class TimetableDAO {

    // ADD
    public void addTimetable(int facultyId, String day, int period, String room, int courseId, String section) {

        try (Connection con = DBConnection.getConnection()) {

            String query = "INSERT INTO timetable(faculty_id, course_id, day, period, room, section) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, facultyId);
            ps.setInt(2, courseId);
            ps.setString(3, day);
            ps.setInt(4, period);
            ps.setString(5, room);
            ps.setString(6, section);

            ps.executeUpdate();
            System.out.println("✅ Timetable added");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean updateTimetableField(int id, String field, String value) {

        // only allow valid fields
        if (!(field.equals("day") || field.equals("period") || field.equals("room") ||
              field.equals("course_id") || field.equals("faculty_id") || field.equals("section"))) {
            return false;
        }

        String sql = "UPDATE timetable SET " + field + " = ? WHERE timetable_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Handle integer fields
            if (field.equals("period") || field.equals("course_id") || field.equals("faculty_id")) {
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
    //delete
    public boolean deleteTimetableById(int id) {

        String sql = "DELETE FROM timetable WHERE timetable_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // GET ALL
    public List<Timetable> getAllTimetables() {

        List<Timetable> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT * FROM timetable";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Timetable(
                        rs.getInt("timetable_id"),
                        rs.getInt("faculty_id"),
                        rs.getString("day"),
                        rs.getInt("period"),
                        rs.getString("room"),
                        rs.getInt("course_id"),
                        rs.getString("section")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}