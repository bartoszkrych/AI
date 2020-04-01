package krych.bartosz.crossword;

import krych.bartosz.abstra.Heuristic;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CrossDescDomVarHeuristic implements Heuristic<CrosswordVariable> {

    @Override
    public List<CrosswordVariable> sort(List<CrosswordVariable> list) {
        return list.stream().sorted(Comparator.comparingInt(x -> x.getDomain().size())).collect(Collectors.toList());
    }
}
