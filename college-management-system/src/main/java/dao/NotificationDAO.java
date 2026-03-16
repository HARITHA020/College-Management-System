package dao;

import java.util.ArrayList;
import java.util.List;
import model.Notification;

public class NotificationDAO {

    private List<Notification> notifications = new ArrayList<>();

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public List<Notification> getAllNotifications() {
        return notifications;
    }
}