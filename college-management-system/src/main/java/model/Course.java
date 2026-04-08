/*
 * Course model
 * Author: Jerishwin Joseph
 */
package model;

// Course model class representing a course offered by the college
public class Course {
    private int courseId;
    private String courseName;
    private int facultyId;

    public Course(int courseId, String courseName, int facultyId) {
        this.courseId   = courseId;
        this.courseName = courseName;
        this.facultyId  = facultyId;
    }
    
    // Getters and Setters
    public int getCourseId()                    { return courseId; }
    public void setCourseId(int courseId)       { this.courseId = courseId; }

    public String getCourseName()               { return courseName; }
    public void setCourseName(String courseName){ this.courseName = courseName; }

    public int getFacultyId()                   { return facultyId; }
    public void setFacultyId(int facultyId)     { this.facultyId = facultyId; }
}