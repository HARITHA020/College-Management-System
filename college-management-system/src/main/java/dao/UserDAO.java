package dao;

import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO {

    private List<User> users = new ArrayList<>();

    public UserDAO() {
        users.add(new User(1, "admin@gmail.com", "admin123", "ADMIN"));
        users.add(new User(2, "faculty@gmail.com", "faculty123", "FACULTY"));
        users.add(new User(3, "student@gmail.com", "student123", "STUDENT"));
    }

    public User checkUser(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) &&
                user.getPassword().equals(password)) {
                return user;  // return full User object
            }
        }
        return null;
    }
}