package krych.bartosz.crossword;

import krych.bartosz.abstra.Heuristic;

import java.util.List;

public class CrossNaiveVarHeuristic implements Heuristic<CrosswordVariable> {
    @Override
    public List<CrosswordVariable> sort(List<CrosswordVariable> list) {
        return list;
    }
}
