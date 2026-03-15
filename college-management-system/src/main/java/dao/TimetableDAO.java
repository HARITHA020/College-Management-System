package dao;

import java.util.ArrayList;
import java.util.List;
import model.Timetable;

public class TimetableDAO {

    private List<Timetable> timetables = new ArrayList<>();

    public void addTimetable(int id, String day, String time, String room, int courseId) {

        Timetable timetable = new Timetable(id, day, time, room, courseId);

        timetables.add(timetable);
    }

    public List<Timetable> getAllTimetables() {
        return timetables;
    }
}