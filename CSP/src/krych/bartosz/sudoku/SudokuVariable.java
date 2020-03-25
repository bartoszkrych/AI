package krych.bartosz.sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuVariable {
    private int value;
    private List<Integer> domain = new ArrayList<>();
    private final int DEF_LEN_SUDOKU = 9;

    public SudokuVariable() {
        this.value = 0;
        for (int i = 1; i <= DEF_LEN_SUDOKU; i++) domain.add(i);
    }

    public SudokuVariable(int value) {
        this.value = value;
        this.domain.add(value);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setDomain(List<Integer> domain) {
        this.domain = domain;
    }

    public int getValue() {
        return value;
    }

    public List<Integer> getDomain() {
        return domain;
    }
}
