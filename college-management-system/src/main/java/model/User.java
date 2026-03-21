package model;

public class User {
    private int userId;       // NEW
    private String email;
    private String password;
    private String role;

    public User(int userId, String email, String password, String role){
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}