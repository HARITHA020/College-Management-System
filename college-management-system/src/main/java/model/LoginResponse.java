/*
 * Author : Haritha
 * Model is used to initialize the variable and getter,setter used to return or set value based on input 
 */
package model;

public class LoginResponse {
    private String role;
    private int userId;

    public LoginResponse(String role, int userId) {
        this.role = role;
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public int getUserId() {
        return userId;
    }
}