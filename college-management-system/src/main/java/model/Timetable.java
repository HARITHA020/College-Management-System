package model;

public class Timetable {

    private int id;
    private String day;
    private String time;
    private String room;
    private int courseId;

    public Timetable(int id, String day, String time, String room, int courseId) {
        this.id = id;
        this.day = day;
        this.time = time;
        this.room = room;
        this.courseId = courseId;
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

    public int getCourseId() {
        return courseId;
    }
}