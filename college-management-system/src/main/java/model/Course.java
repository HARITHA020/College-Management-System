package model;

public class Course {
    private int courseId;
    private String courseName;
    private int facultyId;
    private int credits;           
    private String duration;      
    private String department;     
    private String description;     
    private int semester;
    // Constructor with all fields
    public Course(int courseId, String courseName, int facultyId, int credits, String duration, String department, String description, int semester) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.facultyId = facultyId;
        this.credits = credits;
        this.duration = duration;
        this.department = department;
        this.description = description;
        this.semester = semester;
    }
    // Getters and Setters
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public int getFacultyId() { return facultyId; }
    public void setFacultyId(int facultyId) { this.facultyId = facultyId; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }
 

 
}