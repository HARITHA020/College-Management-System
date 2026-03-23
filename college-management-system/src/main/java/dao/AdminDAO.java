package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Administrator;

public class AdminDAO {

    // 🔹 ADD ADMIN
    public void addAdmin(String name, String dob, String contact, int userId) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO administrators(name, dob, contact, user_id) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, dob);
            ps.setString(3, contact);
            ps.setInt(4, userId);

            int rows = ps.executeUpdate();

            
            if (rows > 0) {
                System.out.println("✅ Admin added successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 UPDATE ADMIN
    public void updateAdmin(int id, String name, String dob, String contact) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE administrators SET name=?, dob=?, contact=? WHERE admin_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, dob);
            ps.setString(3, contact);
            ps.setInt(4, id);

            ps.executeUpdate();

            System.out.println("✅ Admin updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 DELETE ADMIN
    public void deleteAdmin(int id) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "DELETE FROM administrators WHERE admin_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("✅ Admin deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 GET ALL ADMINS
    public List<Administrator> getAllAdmins() {

        List<Administrator> admins = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM administrators";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Administrator a = new Administrator(
                        rs.getInt("admin_id"),
                        rs.getString("name"),
                        null, // ❌ no password in this table
                        rs.getString("dob"),
                        rs.getString("contact"),
                        rs.getInt("user_id")
                );

                admins.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return admins;
    }

    // 🔹 GET ADMIN BY USER ID
    public Administrator getAdminByUserId(int userId) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM administrators WHERE user_id=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Administrator(
                        rs.getInt("admin_id"), // ✅ fixed
                        rs.getString("name"),
                        null, // ❌ no password
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
    
    public int getUserIdByAdminId(int adminId) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT user_id FROM administrators WHERE admin_id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, adminId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}