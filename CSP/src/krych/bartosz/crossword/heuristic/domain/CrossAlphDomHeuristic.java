package krych.bartosz.crossword.heuristic.domain;

import krych.bartosz.abstra.DomHeuristic;
import krych.bartosz.crossword.CrosswordVariable;

import java.util.Collections;
import java.util.List;

public class CrossAlphDomHeuristic implements DomHeuristic<CrosswordVariable> {
    @Override
    public void sort(List<CrosswordVariable> variables) {
        for (CrosswordVariable v : variables) {
            Collections.sort(v.getDomain());
        }
    }
}
