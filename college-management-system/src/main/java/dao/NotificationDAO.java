package dao;

import java.util.ArrayList;
import java.util.List;
import model.Notification;

public class NotificationDAO {

    private static List<Notification> notifications = new ArrayList<>();
    private static int idCounter = 1; 

    public void addNotification(Notification notification) {
        notification.setNotificationId(idCounter++);
        notifications.add(notification);
    }

    public List<Notification> getAllNotifications() {
        return notifications;
    }
}