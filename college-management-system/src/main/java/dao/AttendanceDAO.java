package dao;

import java.util.ArrayList;
import java.util.List;
import model.Attendance;

public class AttendanceDAO {

    private List<Attendance> attendanceList = new ArrayList<>();

    // Mark attendance
    public void markAttendance(Attendance attendance) {
        attendanceList.add(attendance);
    }

    // Get all attendance
    public List<Attendance> getAllAttendance() {
        return attendanceList;
    }

    // Get attendance by student
    public List<Attendance> getAttendanceByStudent(int studentId) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance a : attendanceList) {
            if (a.getStudentId() == studentId) {
                result.add(a);
            }
        }
        return result;
    }

    // Get attendance by course
    public List<Attendance> getAttendanceByCourse(int courseId) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance a : attendanceList) {
            if (a.getCourseId() == courseId) {
                result.add(a);
            }
        }
        return result;
    }

    // Get attendance by student and course
    public List<Attendance> getAttendanceByStudentAndCourse(int studentId, int courseId) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance a : attendanceList) {
            if (a.getStudentId() == studentId && a.getCourseId() == courseId) {
                result.add(a);
            }
        }
        return result;
    }
}