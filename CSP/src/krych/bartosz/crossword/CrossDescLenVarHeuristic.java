package krych.bartosz.crossword;

import krych.bartosz.abstra.Heuristic;

import java.util.List;
import java.util.stream.Collectors;

public class CrossDescLenVarHeuristic implements Heuristic<CrosswordVariable> {

    @Override
    public List<CrosswordVariable> sort(List<CrosswordVariable> list) {
        return list.stream().sorted((x, y) -> -x.getLength().compareTo(y.getLength())).collect(Collectors.toList());
    }
}
