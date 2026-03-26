package model;

public class Result {
    private int resultId;
    private int studentId;
    private int examId;  // ✅ Changed from courseId to examId
    private int marks;
    private String grade;
    private boolean published;

    // Constructor
    public Result(int resultId, int studentId, int examId, int marks, String grade) {
        this.resultId = resultId;
        this.studentId = studentId;
        this.examId = examId; // ✅ correctly use examId
        this.marks = marks;
        this.grade = grade;
        this.published = false; // default not published
    }

    // Getters and setters
    public int getResultId() { return resultId; }
    public int getStudentId() { return studentId; }
    public int getExamId() { return examId; }  // ✅ getter
    public int getMarks() { return marks; }
    public String getGrade() { return grade; }
    public boolean isPublished() { return published; }

    public void setMarks(int marks) { this.marks = marks; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setPublished(boolean published) { this.published = published; }
}