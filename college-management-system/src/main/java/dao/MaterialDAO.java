package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Material;

public class MaterialDAO {

    // ADD MATERIAL
    public void addMaterial(Material m) {

        String sql = "INSERT INTO materials (course_id, title, content) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, m.getCourseId());
            ps.setString(2, m.getTitle());
            ps.setString(3, m.getContent());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // GET ALL MATERIALS
    public List<Material> getAllMaterials() {

        List<Material> list = new ArrayList<>();

        String sql = "SELECT * FROM materials";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Material m = new Material(
                        rs.getInt("material_id"), 
                        rs.getInt("course_id"),
                        rs.getString("title"),
                        rs.getString("content")
                );

                list.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}