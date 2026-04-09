//AUTHOR :Balamurugan
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Faculty;

public class FacultyDAO {

    // ADD FACULTY
    public void addFaculty(String name, String department, String dob, String contact, int userId) {

        String query = "INSERT INTO faculty(name, department, dob, contact, user_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, department);
            ps.setString(3, dob);
            ps.setString(4, contact);
            ps.setInt(5, userId);

            ps.executeUpdate();

            System.out.println("✅ Faculty added successfully (AUTO ID)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    // UPDATE FACULTY
    public boolean updateFacultyField(int facultyId, String field, String value) {
    	// check fields to prevent invalid faculty
        if (!(field.equals("name") || field.equals("department") || field.equals("dob") || field.equals("contact"))) {
            System.out.println("Invalid field: " + field);
            return false;
        }

        String sql = "UPDATE faculty SET " + field + " = ? WHERE faculty_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (field.equals("dob")) {
                ps.setDate(1, Date.valueOf(value)); // format -'yyyy-mm-dd'
            } else {
                ps.setString(1, value);
            }

            ps.setInt(2, facultyId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // DELETE FACULTY
    public void deleteFaculty(int facultyId) {

        String query = "DELETE FROM faculty WHERE faculty_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, facultyId);

            ps.executeUpdate();

            System.out.println("✅ Faculty deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // GET ALL FACULTY
    public List<Faculty> getAllFaculty() {

        List<Faculty> list = new ArrayList<>();
        String query = "SELECT * FROM faculty";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Faculty f = new Faculty(
                        rs.getInt("faculty_id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("dob"),
                        rs.getString("contact"),
                        rs.getInt("user_id")
                );

                list.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    // GET FACULTY DETAILS BY FACULTY ID
    public Faculty getFacultyById(int facultyId) {

        String query = "SELECT * FROM faculty WHERE faculty_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, facultyId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Faculty(
                        rs.getInt("faculty_id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("dob"),
                        rs.getString("contact"),
                        rs.getInt("user_id")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    // GET FACULTY BY USER ID
    public Faculty getFacultyByUserId(int userId) {

        String query = "SELECT * FROM faculty WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Faculty(
                        rs.getInt("faculty_id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("dob"),
                        rs.getString("contact"),
                        rs.getInt("user_id")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // GET USER ID BY FACULTY ID
    public int getUserIdByFacultyId(int facultyId) {

        String query = "SELECT user_id FROM faculty WHERE faculty_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, facultyId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    // CHECK FACULTY EXISTS
    public boolean isFacultyExists(int facultyId) {

        String query = "SELECT faculty_id FROM faculty WHERE faculty_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, facultyId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}