package dao;

import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO {

    private List<User> users = new ArrayList<>();

    public UserDAO() {
        users.add(new User("admin@gmail.com", "admin123", "ADMIN"));
        users.add(new User("faculty@gmail.com", "faculty123", "FACULTY"));
        users.add(new User("student@gmail.com", "student123", "STUDENT"));
    }

    public String checkUser(String email, String password) {

        for (User user : users) {
            if (user.getEmail().equals(email) &&
                user.getPassword().equals(password)) {
                return user.getRole();
            }
        }
        return null; // FIXED
    }
}