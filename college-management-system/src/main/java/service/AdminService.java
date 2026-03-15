package service;

import java.util.List;

import dao.AdminDAO;
import dao.CourseDAO;
import dao.TimetableDAO;
import model.Administrator;
import model.Course;
import model.Faculty;
import model.Timetable;

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
    
    public void viewAdmins() {
		
		List<Administrator> admins = adminDao.getAllAdmins();
		System.out.println("Admin Details:");
		for (Administrator admin: admins) {
			System.out.println("Admin Id"+admin.getId() + " Admin Name " + admin.getName());
		}
	}
    
    public void viewCourse() {
    	List<Course> courses= courseDao.getAllCourses();
    	System.out.println("Course details:");
    	for(Course course:courses) {
    		System.out.println("Course Id:"+course.getcourseId()+" Course Name:"+course.getcourseName()+"Faculty assignned to corurse:"+course.getFacultyid());
    	}
    }
    
    public void viewTimetable() {
    	List<Timetable> timetables= timetableDao.getAllTimetables();
    	System.out.println("Timetable Schedules:");
    	for(Timetable timetable:timetables) {
    		System.out.println("Id:"+timetable.gettimetableId()+" Day:"+timetable.getDay()+" Time:"+timetable.getTime()+" Room No:"+timetable.getRoom()+" courseId:"+timetable.getCourseId());
    	}
    }

}