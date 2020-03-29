package krych.bartosz.sudoku;

import krych.bartosz.abstra.Heuristic;

import java.util.List;

public class SudokuVarNaivHeuristic implements Heuristic<SudokuVariable> {
    @Override
    public List<SudokuVariable> sort(List<SudokuVariable> list) {
        return list;
    }
}
