package dao;

import java.util.ArrayList;
import java.util.List;
import model.Administrator;

public class AdminDAO {

    private List<Administrator> admins = new ArrayList<>();

    public void addAdmin(int id, String name, String password, String dob, String contact, int userId) {
        admins.add(new Administrator(id, name, password, dob, contact, userId));
    }

    public void updateAdmin(int id, String name, String password, String dob, String contact, int userId) {
        for (Administrator a : admins) {
            if (a.getId() == id) {
                a.setName(name);
                a.setPassword(password);
                a.setDob(dob);
                a.setContact(contact);
                a.setUserId(userId);
            }
        }
    }

    public void deleteAdmin(int id) {
        admins.removeIf(a -> a.getId() == id);
    }

    public List<Administrator> getAllAdmins() {
        return admins;
    }
    
    public Administrator getAdminByUserId(int userId) {
        for (Administrator a : admins) {
            if (a.getUserId() == userId) return a;
        }
        return null;
    }
}