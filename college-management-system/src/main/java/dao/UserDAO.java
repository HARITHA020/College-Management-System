/*
 * Author : Haritha
 * Dao helps to retrieve the the data form the mysql using the jdbc query commands
 */


package dao;

import java.sql.*;
import db.DBConnection;
import model.LoginResponse;

public class UserDAO {

    public LoginResponse checkUser(String email, String password) {

        LoginResponse response = null;

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT role, user_id FROM users WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                int userId = rs.getInt("user_id");

                response = new LoginResponse(role, userId);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    //  Check email exists
    public boolean checkEmailExists(String email) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM users WHERE email=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Create new user
    public int createUser(String email, String password, String role) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO users(email, password, role) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, role);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // ✅ user_id
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    
    public void deleteUser(int userId) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "DELETE FROM users WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, userId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}