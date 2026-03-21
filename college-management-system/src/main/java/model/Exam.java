package model;

import java.util.HashMap;
import java.util.Map;

public class Exam {

    private int examId;
    private int courseId;
    private String examDate;
    private int maxMarks;
    private Map<Integer, Integer> studentMarks; // studentId -> marks

    public Exam(int examId, int courseId, String examDate, int maxMarks) {
        this.examId = examId;
        this.courseId = courseId;
        this.examDate = examDate;
        this.maxMarks = maxMarks;
        this.studentMarks = new HashMap<>();
    }

    public int getExamId() { return examId; }
    public int getCourseId() { return courseId; }
    public String getExamDate() { return examDate; }
    public int getMaxMarks() { return maxMarks; }

    // Store marks for a student
    public void setStudentMarks(int studentId, int marks) {
        studentMarks.put(studentId, marks);
    }

    // Retrieve marks for a student
    public Integer getStudentMarks(int studentId) {
        return studentMarks.get(studentId);
    }
}