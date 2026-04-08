/*
 * Author : Haritha
 * Dao helps to retrieve the the data form the mysql using the jdbc query commands
 */

package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Notification;
import db.DBConnection;

public class NotificationDAO {

    // Add notification
    public void addNotification(Notification notification) {
        String query = "INSERT INTO notifications(message, notification_date, target_role, target_id) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, notification.getMessage());
            ps.setDate(2, new java.sql.Date(notification.getDate().getTime()));
            ps.setString(3, notification.getTargetRole());

            if (notification.getTargetId() == null) {
                ps.setNull(4, java.sql.Types.INTEGER);   // to avoid the nullpointerexpection or sql error
            } else {
                ps.setInt(4, notification.getTargetId());
            }

            ps.executeUpdate();
            System.out.println("✅ Notification added to DB successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all notifications
    public List<Notification> getAllNotifications() {
        List<Notification> notice = new ArrayList<>();
        String query = "SELECT * FROM notifications";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Integer targetId = rs.getObject("target_id") != null ? rs.getInt("target_id") : null;

                Notification notification = new Notification(
                        rs.getInt("notification_id"),
                        rs.getString("message"),
                        rs.getDate("notification_date"),
                        rs.getString("target_role"),
                        targetId
                );

                notice.add(notification);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notice;
    }

    // Delete notification by ID
    public void deleteNotification(int notificationId) {
        String query = "DELETE FROM notifications WHERE notification_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, notificationId);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Notification deleted successfully.");
            } else {
                System.out.println("⚠️ Notification ID not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}