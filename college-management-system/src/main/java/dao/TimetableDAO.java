package dao;

import java.util.ArrayList;
import java.util.List;
import model.Timetable;

public class TimetableDAO {

    private List<Timetable> timetables = new ArrayList<>();

    public void addTimetable(int id, int facultyId, String day, String time, String room, int courseId, String section) {

        Timetable timetable = new Timetable(id, facultyId, day, time, room, courseId, section);

        timetables.add(timetable);
    }

    public List<Timetable> getAllTimetables() {
        return timetables;
    }
}