package service;

import dao.AdminDAO;
import dao.CourseDAO;
import dao.TimetableDAO;

public class AdminService {
	
	
	CourseDAO courseDao=new CourseDAO();
	TimetableDAO timetableDao=new TimetableDAO();
	AdminDAO adminDao = new AdminDAO();

    public void addAdmin(int id, String name, String password) {

        adminDao.addAdmin(id,name,password);

        System.out.println("Admin Added Successfully");
    }

    public void updateAdmin(int id, String name, String password) {

        adminDao.updateAdmin(id, name, password);

        System.out.println("Admin Updated Successfully");
    }

    public void deleteAdmin(int id) {

        adminDao.deleteAdmin(id);

        System.out.println("Admin Deleted Successfully");
    }
	
    public void addCourse(int course_Id, String course_Name) {
    	courseDao.addCourse(course_Id,course_Name);
        System.out.println("Course Added Successfully");
    }

    public void assignCourse(int courseId, int facultyId) {

        System.out.println("Course Assigned Successfully");
    }

    public void addTimetable(int id, String day, String time, String room, int courseId) {
    	timetableDao.addTimetable(id,day,time,room,courseId);
        System.out.println("Timetable Added Successfully");
    }
}