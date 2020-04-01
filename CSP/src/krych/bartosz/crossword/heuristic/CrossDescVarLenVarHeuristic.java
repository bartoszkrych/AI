package krych.bartosz.crossword.heuristic;

import krych.bartosz.abstra.VarHeuristic;
import krych.bartosz.crossword.CrosswordVariable;

import java.util.List;
import java.util.stream.Collectors;

public class CrossDescVarLenVarHeuristic implements VarHeuristic<CrosswordVariable> {

    @Override
    public List<CrosswordVariable> sort(List<CrosswordVariable> list) {
        return list.stream().sorted((x, y) -> -x.getLength().compareTo(y.getLength())).collect(Collectors.toList());
    }
}
