package dao;

import java.util.ArrayList;
import java.util.List;
import model.Exam;

public class ExamDAO {

    private List<Exam> exams = new ArrayList<>();

    // Add exam with max marks
    public void addExam(int examId, int courseId, String examDate, int maxMarks) {
        Exam exam = new Exam(examId, courseId, examDate, maxMarks);
        exams.add(exam);
    }

    // Get all exams
    public List<Exam> getAllExams() {
        return exams;
    }

    // Get exams by course
    public List<Exam> getExamsByCourse(int courseId) {
        List<Exam> list = new ArrayList<>();
        for (Exam e : exams) {
            if (e.getCourseId() == courseId) {
                list.add(e);
            }
        }
        return list;
    }

    // Optional: get exam by ID
    public Exam getExamById(int examId) {
        for (Exam e : exams) {
            if (e.getExamId() == examId) return e;
        }
        return null;
    }

    // Record student marks for an exam
    public void recordStudentMarks(int examId, int studentId, int marks) {
        Exam exam = getExamById(examId);
        if (exam != null) {
            exam.setStudentMarks(studentId, marks);
        }
    }
}