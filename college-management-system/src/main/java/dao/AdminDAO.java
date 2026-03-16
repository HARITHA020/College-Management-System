package dao;

import java.util.ArrayList;
import java.util.List;
import model.Administrator;

public class AdminDAO {

    private List<Administrator> admins = new ArrayList<>();

    public void addAdmin(int id, String name, String password) {
        admins.add(new Administrator(id, name, password));
    }

    public void updateAdmin(int id, String name, String password) {
        for (Administrator a : admins) {
            if (a.getId() == id) {
                a.setName(name);
                a.setPassword(password);
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