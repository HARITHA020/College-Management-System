package model;

public class Student {

    private int id;
    private String name;
    private String department;
    private String dob;
    private String contact;
    private int userId;  // Link to User table

    public Student(int id, String name, String department, String dob, String contact, int userId) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.dob = dob;
        this.contact = contact;
        this.userId = userId;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getDob() { return dob; }
    public String getContact() { return contact; }
    public int getUserId() { return userId; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setDob(String dob) { this.dob = dob; }
    public void setContact(String contact) { this.contact = contact; }
    public void setUserId(int userId) { this.userId = userId; }

    @Override
    public String toString() {
        return String.format(
            "Student [ID: %d | Name: %-20s | Department: %s | DOB: %s | Contact: %s | UserID: %d]",
            id, name, department, dob, contact, userId
        );
    }
}