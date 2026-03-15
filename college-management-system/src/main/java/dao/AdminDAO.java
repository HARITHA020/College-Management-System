package dao;

import java.util.ArrayList;
import java.util.List;

import model.Administrator;


public class AdminDAO {

    private List<Administrator> admins = new ArrayList<>();

    public void addAdmin(int id, String name, String password) {
    	Administrator admin=new Administrator(id,name,password);
        admins.add(admin);
    }

    public void updateAdmin(int id, String name, String password) {

        for (Administrator admin : admins) {

            if (admin.getId() == id) {

                admin.setName(name);
                admin.setPassword(password);
                break;
            }
        }
    }

    public void deleteAdmin(int id) {

    	for(Administrator admin:admins) {
    		if(admin.getId()== id) {
    			admins.remove(id);
    		}
    	}
    }

    public List<Administrator> getAllAdmins() {
        return admins;
    }
}