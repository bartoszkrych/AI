package krych.bartosz.sudoku;

import krych.bartosz.abstra.Variable;

import java.util.ArrayList;
import java.util.List;

public class SudokuVariable implements Variable<Integer> {
    private Integer value;
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
        this.i = i;
        this.j = j;
        for (int k = 1; k <= DEF_LEN_SUDOKU; k++) domain.add(k);
    }

    public SudokuVariable(int value, int i, int j) {
        this.i = i;
        this.j = j;
        this.value = value;
        this.domain.add(value);
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public List<Integer> getDomain() {
        return domain;
    }
}
