package krych.bartosz;

import java.util.ArrayList;
import java.util.List;

public class Variable {
    private int value;
    private List<Integer> domain = new ArrayList<>();
    private final int DEF_LEN_SUDOKU = 9;

    public Variable() {
        this.value = 0;
        for (int i= 1; i <= DEF_LEN_SUDOKU; i++) domain.add(i);
    }

    public Variable(int value) {
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
}
