package dao;

import java.util.ArrayList;
import java.util.List;
import model.User;
import model.LoginResponse;

public class UserDAO {

    private List<User> users = new ArrayList<>();

    public UserDAO() {
        users.add(new User("admin@gmail.com", "admin123", "ADMIN", 1));
        users.add(new User("faculty@gmail.com", "faculty123", "FACULTY", 2));
        users.add(new User("student@gmail.com", "student123", "STUDENT", 3));
    }

    // 🔹 returns LoginResponse for login check
    public LoginResponse checkUser(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return new LoginResponse(user.getRole(), user.getUserId());
            }
        }
        return null; // invalid login
    }
}