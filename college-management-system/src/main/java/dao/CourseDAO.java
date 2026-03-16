package dao;

import java.util.ArrayList;
import java.util.List;
import model.Course;

public class CourseDAO {

    private List<Course> courses = new ArrayList<>();

    // Add Course
    public void addCourse(int course_Id, String course_Name) {

        Course course = new Course(course_Id, course_Name);
        courses.add(course);
    }

    // Assign Course to Faculty
    public void assignCourse(int courseId, int facultyId) {

        for (Course course : courses) {

            if (course.getcourseId() == courseId) {
                course.setFacultyid(facultyId);
                break;
            }
        }
    }

    // View all Courses
    public List<Course> getAllCourses() {
        return courses;
    }
}