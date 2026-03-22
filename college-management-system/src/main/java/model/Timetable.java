package model;

public class Timetable {

    private int id;
    private String day;
    private String time;
    private String room;
    private int courseId;
    private int facultyId;
    private String section;

    //for adding timetable
    public Timetable( int facultyId, String day, String time, String room, int courseId, String section) {
       
        this.facultyId = facultyId;
        this.day = day;
        this.time = time;
        this.room = room;
        this.courseId = courseId;
        this.section = section;
    }
    //for fecting timetable
    public Timetable(int id, int facultyId, String day, String time, String room, int courseId, String section) {
        this.id = id;
        this.facultyId = facultyId;
        this.day = day;
        this.time = time;
        this.room = room;
        this.courseId = courseId;
        this.section = section;
    }
    public int gettimetableId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getRoom() {
        return room;
    }
    public String getSection() {
        return section;
    } 
    public int getFacultyId() {
        return facultyId;
    }

    public int getCourseId() {
        return courseId;
    }
}