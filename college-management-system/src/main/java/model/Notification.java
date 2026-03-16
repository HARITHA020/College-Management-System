package model;

import java.util.Date;

public class Notification {

    private int notificationId;
    private String message;
    private Date date;

    public Notification(int notificationId, String message, Date date) {
        this.notificationId = notificationId;
        this.message = message;
        this.date = date;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}