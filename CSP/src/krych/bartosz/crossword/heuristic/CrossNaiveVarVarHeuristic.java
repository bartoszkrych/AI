package krych.bartosz.crossword.heuristic;

import krych.bartosz.abstra.VarHeuristic;
import krych.bartosz.crossword.CrosswordVariable;

import java.util.List;

public class CrossNaiveVarVarHeuristic implements VarHeuristic<CrosswordVariable> {
    @Override
    public List<CrosswordVariable> sort(List<CrosswordVariable> list) {
        return list;
    }
}
