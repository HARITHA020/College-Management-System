package model;

public class Student {

    private int id;
    private String name;
    private String department;
    private String dob;      // NEW
    private String contact;  // NEW

    // Constructor with all fields
    public Student(int id, String name, String department, String dob, String contact) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.dob = dob;
        this.contact = contact;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getDob() { return dob; }          // NEW
    public String getContact() { return contact; }  // NEW

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setDob(String dob) { this.dob = dob; }          // NEW
    public void setContact(String contact) { this.contact = contact; }  // NEW

    @Override
    public String toString() {
        return String.format(
            "Student [ID: %d | Name: %-20s | Department: %s | DOB: %s | Contact: %s]",
            id, name, department, dob, contact
        );
    }
}