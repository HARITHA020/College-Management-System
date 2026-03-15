package service;

import dao.FacultyDAO;
import model.Faculty;

public class FacultyService {
	FacultyDAO facultyDao=new FacultyDAO();
	public void addFaculty(int id, String name, String department) {

        Faculty faculty = new Faculty(id, name, department);
        facultyDao.addFaculty(faculty);

        System.out.println("Faculty Added Successfully");
    }

    public void updateFaculty(int id, String name, String department) {

        facultyDao.updateFaculty(id, name, department);

        System.out.println("Faculty Updated Successfully");
    }

    public void deleteFaculty(int id) {

        facultyDao.deleteFaculty(id);

        System.out.println("Faculty Deleted Successfully");
    }

	

}
