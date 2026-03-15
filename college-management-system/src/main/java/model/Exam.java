package model;

public class Exam {
	private int courseId;
	private int examId;
	private String examDate;
	
	
	public Exam(int courseId, int examId, String examDate) {
		this.courseId = courseId;
		this.examId = examId;
		this.examDate = examDate;
	}

	public int getCourseId() {
		return courseId;
	}
	
	public int getExamId() {
		return examId;
	}
	
	public String getExamDate() {
		return examDate;
	}
	
	
	
}
