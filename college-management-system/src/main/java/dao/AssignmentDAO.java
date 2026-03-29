package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Assignment;

public class AssignmentDAO {

    //ADD ASSIGNMENT
    public void addAssignment(Assignment a) {

        String sql = "INSERT INTO assignments (course_id, title, description) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, a.getCourseId());
            ps.setString(2, a.getTitle());
            ps.setString(3, a.getDescription());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean deleteAssignment(int materialId) {
        try (Connection con = DBConnection.getConnection()) {
            String query = "DELETE FROM assignments WHERE assignment_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, materialId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //GET ALL ASSIGNMENTS
    public List<Assignment> getAllAssignments() {

        List<Assignment> list = new ArrayList<>();

        String sql = "SELECT * FROM assignments";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Assignment a = new Assignment(
                        rs.getInt("assignment_id"),  
                        rs.getInt("course_id"),
                        rs.getString("title"),
                        rs.getString("description")
                );

                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}