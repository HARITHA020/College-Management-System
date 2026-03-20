package model;

public class Assignment {

    private int id;
    private int courseId;
    private String title;
    private String description;

    public Assignment(int id, int courseId, String title, String description) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
    }

    public int getId() { return id; }
    public int getCourseId() { return courseId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
}