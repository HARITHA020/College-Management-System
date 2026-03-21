package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}