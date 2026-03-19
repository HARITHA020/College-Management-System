package dao;

import java.util.ArrayList;
import java.util.List;

import model.Enrollment;

public class EnrollmentDAO {

    // 🔹 Make static so all services share the same list
    private static List<Enrollment> enrollments = new ArrayList<>();

    // 🔹 Assign student to course
    public void enrollStudent(int studentId, int courseId) {
        // 🔹 Prevent duplicate enrollment
        for (Enrollment e : enrollments) {
            if (e.getStudentId() == studentId && e.getCourseId() == courseId) {
                System.out.println("Student already enrolled in this course");
                return;
            }
        }

        int id = enrollments.size() + 1;
        Enrollment enrollment = new Enrollment(id, studentId, courseId);
        enrollments.add(enrollment);

        System.out.println("Student assigned to course successfully");
    }

    // 🔹 Get all enrollments
    public List<Enrollment> getAllEnrollments() {
        return enrollments;
    }

    // 🔹 Optional: Get enrollment by studentId
    public List<Enrollment> getEnrollmentsByStudent(int studentId) {
        List<Enrollment> list = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getStudentId() == studentId) {
                list.add(e);
            }
        }
        return list;
    }

    // 🔹 Optional: Get enrollment by courseId
    public List<Enrollment> getEnrollmentsByCourse(int courseId) {
        List<Enrollment> list = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getCourseId() == courseId) {
                list.add(e);
            }
        }
        return list;
    }
}