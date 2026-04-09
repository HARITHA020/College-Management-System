//AUTHOR: Balamurugan

package model;

import java.util.Date;

public class Attendance {

    private int studentId;
    private int courseId;
    private Date date;  
    private boolean present; // true = present, false = absent

    public Attendance(int studentId, int courseId, Date date, boolean present) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.date = date;
        this.present = present;
    }

    // Getters
    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }
    public Date getDate() { return date; }
    public boolean isPresent() { return present; }

    // Setter
    public void setPresent(boolean present) { this.present = present; }
}