package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Administrator;
import model.Timetable;

public class TimetableDAO {

   

    public void addTimetable( int facultyId, String day, String time, String room, int courseId, String section) {

    	 try {
             Connection con = DBConnection.getConnection();

             String query = "INSERT INTO timetable(faculty_id,course_id,day,time_slot,room,section) VALUES (?, ?, ?, ?,?,?)";

             PreparedStatement ps = con.prepareStatement(query);
             ps.setInt(1,facultyId );
             ps.setInt(2, courseId);
             ps.setString(3, day);
             ps.setString(4, time);
             ps.setString(5, room);
             ps.setString(6, section);

             int rows = ps.executeUpdate();

             if (rows > 0) {
                 System.out.println("✅ timetable added successfully");
             }

         } catch (Exception e) {
             e.printStackTrace();
         }
    }

    public List<Timetable> getAllTimetables() {
        List<Timetable> timetables=new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM timetable";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Timetable timetable = new Timetable(
                        rs.getInt("timetable_id"),
                        rs.getInt("faculty_id"),
                        rs.getString("day"),
                        rs.getString("time_slot"),
                        rs.getString("room"),
                        rs.getInt("course_id"),
                        rs.getString("section")
                );

                timetables.add(timetable);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return timetables;
        
    }
}