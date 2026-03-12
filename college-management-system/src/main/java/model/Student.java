package model;

import java.util.Objects;

public class Student {

    private int id;
    private String name;
    private String department;

    // Constructor with basic validation
    public Student(int id, String name, String department) {
        if (id <= 0) throw new IllegalArgumentException("ID must be a positive number");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        if (department == null || department.trim().isEmpty()) throw new IllegalArgumentException("Department cannot be empty");

        this.id = id;
        this.name = name.trim();
        this.department = department.trim();
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }

    // Setters with validation
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        this.name = name.trim();
    }

    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) throw new IllegalArgumentException("Department cannot be empty");
        this.department = department.trim();
    }

    // Formatted display
    @Override
    public String toString() {
        return String.format("Student [ID: %d | Name: %-20s | Department: %s]", id, name, department);
    }

    // Equality based on unique ID
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        return this.id == other.id;
    }

}