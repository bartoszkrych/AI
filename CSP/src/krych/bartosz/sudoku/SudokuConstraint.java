package krych.bartosz.sudoku;

import krych.bartosz.abstra.Constraint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SudokuConstraint implements Constraint<Integer, SudokuVariable> {

    public boolean existInLine(List<SudokuVariable> variables, Integer val) {
        for (SudokuVariable v : variables) {
            if (v.getValue() != null && v.getValue().equals(val)) {
                return true;
            }
        }
        return false;
    }

    public boolean existInBlock(List<SudokuVariable> variables, SudokuVariable variable, int val) {
        int iBlock = (variable.getI() / 3) * 3;
        int jBlock = (variable.getJ() / 3) * 3;

        List<SudokuVariable> list = variables.stream()
                .filter(x -> x.getI() >= iBlock && x.getI() < iBlock + 3)
                .filter(x -> x.getJ() >= jBlock && x.getJ() < jBlock + 3)
                .collect(Collectors.toList());

        for (SudokuVariable v : list) {
            if (v.getValue() != null && v.getValue().equals(val)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isGood(List<SudokuVariable> variables, SudokuVariable variable, Integer value) {
        List<SudokuVariable> col = variables.stream().filter(x -> x.getJ() == variable.getJ()).collect(Collectors.toList());
        List<SudokuVariable> row = variables.stream().filter(x -> x.getI() == variable.getI()).collect(Collectors.toList());

        return !existInLine(col, value) && !existInLine(row, value) && !existInBlock(variables, variable, value);
    }


    private List<SudokuVariable> notNull(List<SudokuVariable> variables) {
        List<SudokuVariable> list = new ArrayList<>();

        for (SudokuVariable v : variables) {
            if (v.getValue() == null) v.setValue(0);
            list.add(v);
        }
        return list;
    }
}
