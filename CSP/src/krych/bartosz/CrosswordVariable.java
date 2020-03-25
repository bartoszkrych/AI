package krych.bartosz;

import java.util.List;

public class CrosswordVariable {
    private List<String> domain;
    private int iBegin;
    private int jBegin;
    private int length;
    private Orientation orientation;

    public CrosswordVariable(List<String> domain, int iBegin, int jBegin, int length, Orientation orientation) {
        this.domain = domain;
        this.iBegin = iBegin;
        this.jBegin = jBegin;
        this.length = length;
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "CrosswordVariable{" +
                "domain=" + domain +
                ", iBegin=" + iBegin +
                ", jBegin=" + jBegin +
                ", length=" + length +
                ", orientation=" + orientation +
                '}';
    }
}
