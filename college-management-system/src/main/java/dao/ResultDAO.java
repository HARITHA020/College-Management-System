package dao;

import java.util.ArrayList;
import java.util.List;
import model.Result;

public class ResultDAO {

    private List<Result> resultList = new ArrayList<>();

    public void addResult(Result r) {
        resultList.add(r);
    }

    public List<Result> getAllResults() {
        return resultList;
    }

    public Result getResultById(int resultId) {
        for (Result r : resultList) {
            if (r.getResultId() == resultId) return r;
        }
        return null;
    }

    // Get results by student
    public List<Result> getResultsByStudent(int studentId) {
        List<Result> res = new ArrayList<>();
        for (Result r : resultList) {
            if (r.getStudentId() == studentId) res.add(r);
        }
        return res;
    }

    // Get results by course
    public List<Result> getResultsByCourse(int courseId) {
        List<Result> res = new ArrayList<>();
        for (Result r : resultList) {
            if (r.getCourseId() == courseId) res.add(r);
        }
        return res;
    }
}