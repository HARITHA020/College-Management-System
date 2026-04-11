package model;

public class Faculty extends Person {

    private String department;

    public Faculty(int id, String name, String department, String dob, String contact, int userId) {
        super(id, name, dob, contact, userId); // call parent constructor
        this.department = department;
    }

    // Getter
    public String getDepartment() {
        return department;
    }

    // Setter
    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return String.format(
            "Faculty [ID: %d | Name: %-20s | Dept: %s | DOB: %s | Contact: %s | UserID: %d]",
            id, name, department, dob, contact, userId
        );
    }
}