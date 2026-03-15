package dao;

import java.util.ArrayList;
import java.util.List;

import model.Exam;

public class ExamDAO {
	List<Exam> exams=new ArrayList<>();
	public void addExam(int examId, int courseId, String examDate) {
		Exam exam=new Exam(examId,courseId,examDate);
		exams.add(exam);
	}
	
	public List<Exam> getAllExam(){
		return exams;
	}


}
