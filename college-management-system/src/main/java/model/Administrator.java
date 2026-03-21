package model;

public class Administrator {

    private int id;
    private String name;
    private String password;
    private String dob;       // New field
    private String contact;   // New field

    // Constructor updated to include new fields
    public Administrator(int id, String name, String password, String dob, String contact) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.dob = dob;
        this.contact = contact;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getDob() { return dob; }          // Getter for dob
    public String getContact() { return contact; }  // Getter for contact

    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setDob(String dob) { this.dob = dob; }        // Setter for dob
    public void setContact(String contact) { this.contact = contact; } // Setter for contact
}