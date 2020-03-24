package krych.bartosz;

import java.util.List;

public class Variable {
    private int value;
    private List<Integer> domain;

    public Variable(List<Integer> domain) {
        this.value = 0;
        this.domain = domain;
    }

    public Variable(int value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
        domain = null;
    }

    public void setDomain(List<Integer> domain) {
        this.domain = domain;
    }


    public int getValue() {
        return value;
    }
}
