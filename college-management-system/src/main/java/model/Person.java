package model;
public abstract class Person {

    protected int id;
    protected String name;
    protected String dob;
    protected String contact;
    protected int userId;

    public Person(int id, String name, String dob, String contact, int userId) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.contact = contact;
        this.userId = userId;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDob() { return dob; }
    public String getContact() { return contact; }
    public int getUserId() { return userId; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDob(String dob) { this.dob = dob; }
    public void setContact(String contact) { this.contact = contact; }
    public void setUserId(int userId) { this.userId = userId; }

    
}
