package krych.bartosz.sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuVariable {
    private int value;
    private List<Integer> domain = new ArrayList<>();
    private int i;
    private int j;
    private final int DEF_LEN_SUDOKU = 9;

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public SudokuVariable(int i, int j) {
        this.value = 0;
        for (int k = 1; k <= DEF_LEN_SUDOKU; k++) domain.add(k);
    }

    public SudokuVariable(int value, int i, int j) {
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
