/*
 * Author : Haritha
 * Model is used to initialize the variable and getter,setter used to return or set value based on input 
 */



package model;

public class Administrator {

    private int id;
    private String name;
    private String password;
    private String dob;
    private String contact;
    private int userId;  // Link to User table

    //Constructor
    public Administrator(int id, String name, String password, String dob, String contact, int userId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.dob = dob;
        this.contact = contact;
        this.userId = userId;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getDob() { return dob; }
    public String getContact() { return contact; }
    public int getUserId() { return userId; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setDob(String dob) { this.dob = dob; }
    public void setContact(String contact) { this.contact = contact; }
    public void setUserId(int userId) { this.userId = userId; }

    @Override
    public String toString() {
        return String.format(
            "Admin [ID: %d | Name: %-20s | DOB: %s | Contact: %s | UserID: %d]",
            id, name, dob, contact, userId
        );
    }
}