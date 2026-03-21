package model;

public class User {
    private String email;
    private String password;
    private String role;
    private int userId; // NEW

    public User(String email, String password, String role, int userId){
        this.email = email;
        this.password = password;
        this.role = role;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public int getUserId() {
        return userId;
    }
}