/*
 * Author : Haritha
 * Model is used to initialize the variable and getter,setter used to return or set value based on input 
 */

package model;

import java.util.Date;

public class Notification {

    private int notificationId; // auto-incremented from DB
    private String message;
    private Date date;
    private String targetRole; // ALL / STUDENT / FACULTY / ADMIN
    private Integer targetId;  // particular person

    public Notification() {}

    // Constructor without ID (for adding new notification)
    public Notification(String message, Date date, String targetRole, Integer targetId) {
        this.message = message;
        this.date = date;
        this.targetRole = targetRole;
        this.targetId = targetId;
    }

    // Constructor with ID (for fetching from DB)
    public Notification(int notificationId, String message, Date date, String targetRole, Integer targetId) {
        this.notificationId = notificationId;
        this.message = message;
        this.date = date;
        this.targetRole = targetRole;
        this.targetId = targetId;
    }

    // Getters and Setters
    public int getNotificationId() { return notificationId; }
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getTargetRole() { return targetRole; }
    public void setTargetRole(String targetRole) { this.targetRole = targetRole; }

    public Integer getTargetId() { return targetId; }
    public void setTargetId(Integer targetId) { this.targetId = targetId; }

    @Override
    public String toString() {
        return "ID: " + notificationId +
               " | Message: " + message +
               " | Date: " + date +
               " | Role: " + targetRole +
               " | Target ID: " + (targetId == null ? "ALL" : targetId);
    }
}