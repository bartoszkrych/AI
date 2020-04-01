package krych.bartosz.crossword.heuristic;

import krych.bartosz.abstra.DomHeuristic;
import krych.bartosz.crossword.CrosswordVariable;

import java.util.Collections;
import java.util.List;

public class CrossShufDomHeuristic implements DomHeuristic<CrosswordVariable> {
    @Override
    public void sort(List<CrosswordVariable> variables) {
        for (CrosswordVariable v : variables) {
            Collections.shuffle(v.getDomain());
        }
    }
}
