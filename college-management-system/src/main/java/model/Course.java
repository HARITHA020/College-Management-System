package model;

public class Course {

    private int id;
    private String name;
    private int faculty_id;



	public Course(int course_Id, String course_Name) {
		this.id=course_Id;
		this.name=course_Name;
	}

	public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

	public int getFacultyid() {
		return faculty_id;
	}

	public void setFacultyid(int faculty_id) {
		this.faculty_id = faculty_id;
	}
    
}