package dao;

import java.util.ArrayList;
import java.util.List;

import model.Faculty;

public class FacultyDAO {

    private List<Faculty> faculties = new ArrayList<>();

    public void addFaculty(int id, String name,String Department, String dob, String contact, int userId) {
        faculties.add(new Faculty(id, name, dob, Department, contact, userId));
    }

    public List<Faculty> getAllFaculty() {
        return faculties;
    }

    public void updateFaculty(int id, String name, String department, String dob, String contact, int userId) {
        for (Faculty faculty : faculties) {
            if (faculty.getId() == id) {
                faculty.setName(name);
                faculty.setDepartment(department);
                faculty.setDob(dob);          // New
                faculty.setContact(contact); 
                faculty.setUserId(userId);;// New
                System.out.println("Faculty Updated Successfully");
            }
        }
    }

    public void deleteFaculty(int id) {
        faculties.removeIf(faculty -> faculty.getId() == id);
    }
    
    public Faculty getFacultyByUserId(int userId) {
        for (Faculty faculty : faculties) {
            if (faculty.getUserId() == userId) return faculty;
        }
        return null;
    }
}