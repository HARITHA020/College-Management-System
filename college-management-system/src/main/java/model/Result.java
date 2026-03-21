package model;

public class Result {
    private int resultId;
    private int studentId;
    private int courseId;
    private int marks;
    private String grade;
    private boolean published;

    public Result(int resultId, int studentId, int courseId, int marks, String grade) {
        this.resultId = resultId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.marks = marks;
        this.grade = grade;
        this.published = false; // default not published
    }

    // Getters and setters
    public int getResultId() { return resultId; }
    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }
    public int getMarks() { return marks; }
    public String getGrade() { return grade; }
    public boolean isPublished() { return published; }

    public void setMarks(int marks) { this.marks = marks; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setPublished(boolean published) { this.published = published; }
}