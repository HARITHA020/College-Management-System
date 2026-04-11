/*
 * Author : Haritha
 * Model is used to initialize the variable and getter,setter used to return or set value based on input 
 */

package model;

public class Administrator extends Person {

    private String password;

    public Administrator(int id, String name, String dob, String contact,
                         int userId, String password) {

        super(id, name, dob, contact, userId); // call parent constructor
        this.password = password;
    }

    // Getter
    public String getPassword() {
        return password;
    }

    // Setter
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
            "Admin [ID: %d | Name: %-20s | DOB: %s | Contact: %s | UserID: %d]",
            id, name, dob, contact, userId
        );
    }
}