package model;
public class Faculty {
    private int id;
    private String name;
    private String department;
    private String dob;       // New field
    private String contact;   // New field

    public Faculty(int id, String name, String department, String dob, String contact) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        if (department == null || department.trim().isEmpty()) throw new IllegalArgumentException("Department cannot be empty");
        if (dob == null || dob.trim().isEmpty()) throw new IllegalArgumentException("DOB cannot be empty");
        if (contact == null || contact.trim().isEmpty()) throw new IllegalArgumentException("Contact cannot be empty");

        this.id = id;
        this.name = name.trim();
        this.department = department.trim();
        this.dob = dob.trim();
        this.contact = contact.trim();
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getDob() { return dob; }           // New getter
    public String getContact() { return contact; }   // New getter

    // Setters
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        this.name = name.trim();
    }

    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) throw new IllegalArgumentException("Department cannot be empty");
        this.department = department.trim();
    }

    public void setDob(String dob) {
        if (dob == null || dob.trim().isEmpty()) throw new IllegalArgumentException("DOB cannot be empty");
        this.dob = dob.trim();
    }

    public void setContact(String contact) {
        if (contact == null || contact.trim().isEmpty()) throw new IllegalArgumentException("Contact cannot be empty");
        this.contact = contact.trim();
    }

    @Override
    public String toString() {
        return String.format("Faculty [ID: %d | Name: %-20s | Department: %s | DOB: %s | Contact: %s]", 
                              id, name, department, dob, contact);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Faculty)) return false;
        Faculty other = (Faculty) obj;
        return this.id == other.id;
    }
}