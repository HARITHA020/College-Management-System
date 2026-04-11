package model;

public class Student extends Person {

    private String department;
    private String section;

    // Constructor with ID
    public Student(int id, String name, String dob, String contact,
                   int userId, String department, String section) {

        super(id, name, dob, contact, userId); // call Person constructor
        this.department = department;
        this.section = section;
    }

    // Constructor without ID
    public Student(String name, String dob, String contact,
                   int userId, String department, String section) {

        super(0, name, dob, contact, userId); // id = 0 (default)
        this.department = department;
        this.section = section;
    }

    // Getters
    public String getDepartment() { return department; }
    public String getSection() { return section; }

    // Setters
    public void setDepartment(String department) { this.department = department; }
    public void setSection(String section) { this.section = section; }

    @Override
    public String toString() {
        return String.format(
            "Student [ID: %d | Name: %-20s | Dept: %s | Section: %s | DOB: %s | Contact: %s | UserID: %d]",
            id, name, department, section, dob, contact, userId
        );
    }
}