/*
 * Author : Haritha
 * Model is used to initialize the variable and getter,setter used to return or set value based on input 
 */


package model;

public class Timetable {

    private int id;
    private int facultyId;
    private String day;
    private int period;
    private String room;
    private int courseId;
    private String section;

    // Constructor for insert
    public Timetable(int facultyId, String day, int period, String room, int courseId, String section) {
        this.facultyId = facultyId;
        this.day = day;
        this.period = period;
        this.room = room;
        this.courseId = courseId;
        this.section = section;
    }

    // Constructor for fetch
    public Timetable(int id, int facultyId, String day, int period, String room, int courseId, String section) {
        this.id = id;
        this.facultyId = facultyId;
        this.day = day;
        this.period = period;
        this.room = room;
        this.courseId = courseId;
        this.section = section;
    }

    public int gettimetableId() { return id; }
    public int getFacultyId() { return facultyId; }
    public String getDay() { return day; }
    public int getPeriod() { return period; }
    public String getRoom() { return room; }
    public int getCourseId() { return courseId; }
    public String getSection() { return section; }
}