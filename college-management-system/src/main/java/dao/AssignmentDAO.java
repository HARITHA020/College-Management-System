package dao;

import java.util.ArrayList;
import java.util.List;
import model.Assignment;

public class AssignmentDAO {

    private List<Assignment> assignments = new ArrayList<>();

    public void addAssignment(Assignment a) {
        assignments.add(a);
    }

    public List<Assignment> getAllAssignments() {
        return assignments;
    }
}