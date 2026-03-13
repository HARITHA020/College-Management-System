package model;

public class Student {

    private int id;
    private String name;
    private String department;

    // Constructor with basic validation
    public Student(int id, String name, String department) {

        this.id = id;
        this.name = name;
        this.department = department;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }

    // Setters with validation
    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
		this.id = id;
	}

	public void setDepartment(String department) { 
        this.department = department;
    }

    // Formatted display
    @Override
    public String toString() {
        return String.format("Student [ID: %d | Name: %-20s | Department: %s]", id, name, department);
    }

}