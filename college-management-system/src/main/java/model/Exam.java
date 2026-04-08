/*
 * Exam Model
 * Author: Jerishwin Joseph
 */
package model;

// Exam model class representing an exam for a course
public class Exam {
    private int examId;
    private int courseId;
    private String examDate;
    private int maxMarks;

    public Exam(int examId, int courseId, String examDate, int maxMarks) {
        this.examId   = examId;
        this.courseId = courseId;
        this.examDate = examDate;
        this.maxMarks = maxMarks;
    }

    // Getters and Setters
    public int getExamId()                    { return examId; }
    public void setExamId(int examId)         { this.examId = examId; }

    public int getCourseId()                  { return courseId; }
    public void setCourseId(int courseId)     { this.courseId = courseId; }

    public String getExamDate()               { return examDate; }
    public void setExamDate(String examDate)  { this.examDate = examDate; }

    public int getMaxMarks()                  { return maxMarks; }
    public void setMaxMarks(int maxMarks)     { this.maxMarks = maxMarks; }
}