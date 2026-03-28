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