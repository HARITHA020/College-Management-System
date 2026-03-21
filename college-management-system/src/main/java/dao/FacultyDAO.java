package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Faculty;

public class FacultyDAO {

    private List<Faculty> faculties = new ArrayList<>();

    public void addFaculty(int id, String name,String Department, String dob, String contact, int userId) {
        faculties.add(new Faculty(id, name, dob, Department, contact, userId));
    }

    public List<Faculty> getAllFaculty() {
        return faculties;
    }

    public void updateFaculty(int id, String name, String department, String dob, String contact) {
        for (Faculty faculty : faculties) {
            if (faculty.getId() == id) {
                faculty.setName(name);
                faculty.setDepartment(department);
                faculty.setDob(dob);          // New
                faculty.setContact(contact); 
                
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
    
    //====admin purpose=========
    public int getUserIdByFacultyId(int id) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT user_id FROM faculty WHERE faculty_id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}