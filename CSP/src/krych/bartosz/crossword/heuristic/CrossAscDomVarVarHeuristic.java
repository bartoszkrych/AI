package krych.bartosz.crossword.heuristic;

import krych.bartosz.abstra.VarHeuristic;
import krych.bartosz.crossword.CrosswordVariable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CrossAscDomVarVarHeuristic implements VarHeuristic<CrosswordVariable> {

    @Override
    public List<CrosswordVariable> sort(List<CrosswordVariable> list) {
        return list.stream().sorted(Comparator.comparingInt(x -> x.getDomain().size())).collect(Collectors.toList());
    }
}
