package krych.bartosz;

import krych.bartosz.crossword.CrosswordVariable;

import java.util.List;
import java.util.stream.Collectors;

public class VariableHeuristic {

    public static List<CrosswordVariable> sortDesc(List<CrosswordVariable> list) {
        return list.stream().sorted((x, y) -> -x.getLength().compareTo(y.getLength())).collect(Collectors.toList());
    }
}
