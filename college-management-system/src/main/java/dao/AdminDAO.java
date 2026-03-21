package dao;

import java.util.ArrayList;
import java.util.List;
import model.Administrator;

public class AdminDAO {

    private List<Administrator> admins = new ArrayList<>();

    // Updated addAdmin
    public void addAdmin(int id, String name, String password, String dob, String contact) {
        admins.add(new Administrator(id, name, password, dob, contact));
    }

    // Updated updateAdmin
    public void updateAdmin(int id, String name, String password, String dob, String contact) {
        for (Administrator a : admins) {
            if (a.getId() == id) {
                a.setName(name);
                a.setPassword(password);
                a.setDob(dob);
                a.setContact(contact);
            }
        }
    }

    public void deleteAdmin(int id) {
        admins.removeIf(a -> a.getId() == id);
    }

    public List<Administrator> getAllAdmins() {
        return admins;
    }
}