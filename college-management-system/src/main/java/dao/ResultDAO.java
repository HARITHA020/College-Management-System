package dao;

import java.util.ArrayList;
import java.util.List;
import model.Result;

public class ResultDAO {

    private static List<Result> results = new ArrayList<>();

    public void addResult(Result result) {
        results.add(result);
    }

    public List<Result> getAllResults() {
        return results;
    }
}