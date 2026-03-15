package dao;

import java.util.ArrayList;
import java.util.List;

import model.Faculty;



public class FacultyDAO {

    private List<Faculty> facultys = new ArrayList<>();

    public void addFaculty(Faculty faculty) {
		facultys.add(faculty);
	}
    public List<Faculty> getAllfaculty() {
        return facultys;
    }
    
    public void updateFaculty(int id, String name, String department) {
		for(Faculty faculty : facultys) {

	        if(faculty.getId() == id) {

	            faculty.setName(name);
	            faculty.setDepartment(department);

	            System.out.println("Student Updated Successfully");
	        }
	    }
	}
	
	public void deleteFaculty(int id) {
		for(Faculty faculty:facultys) {
			if(faculty.getId() ==id) {
				facultys.remove(id);
			}
		}
	}
}