/*
 * Student Model
 * Author: Jerishwin Joseph
 */
package model;

public class Student {

    private int id;
    private String name;
    private String department;
    private String dob;
    private String contact;
    private String section;   
    private int userId;

    // Constructor with ID
    public Student(int id, String name, String department, String dob, String contact, String section, int userId) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.dob = dob;
        this.contact = contact;
        this.section = section;   
        this.userId = userId;
    }

    // Constructor without ID
    public Student(String name, String department, String dob, String contact, String section, int userId) {
        this.name = name;
        this.department = department;
        this.dob = dob;
        this.contact = contact;
        this.section = section;  
        this.userId = userId;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getDob() { return dob; }
    public String getContact() { return contact; }
    public String getSection() { return section; }  
    public int getUserId() { return userId; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setDob(String dob) { this.dob = dob; }
    public void setContact(String contact) { this.contact = contact; }
    public void setSection(String section) { this.section = section; } 
    public void setUserId(int userId) { this.userId = userId; }

    @Override
    public String toString() {
        return String.format(
            "Student [ID: %d | Name: %-20s | Dept: %s | Section: %s | DOB: %s | Contact: %s | UserID: %d]",
            id, name, department, section, dob, contact, userId
        );
    }
}