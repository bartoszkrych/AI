package krych.bartosz.crossword;

import krych.bartosz.abstra.Variable;

import java.util.ArrayList;
import java.util.List;

public class CrosswordVariable implements Variable<String> {
    private String value;
    private int iBegin;
    private int jBegin;
    private int length;
    private Orientation orientation;
    private List<String> domain;

    public CrosswordVariable(List<String> domain, int iBegin, int jBegin, int length, Orientation orientation) {
        this.domain = domain;
        this.iBegin = iBegin;
        this.jBegin = jBegin;
        this.length = length;
        this.orientation = orientation;
    }

    public List<String> getDomain() {
        return domain;
    }

    @Override
    public List<String> getCopyDomain() {
        List<String> copyDomain = new ArrayList<>();

        for (String s : domain) {
            copyDomain.add(new String(s));
        }
        return copyDomain;
    }

    public int getiBegin() {
        return iBegin;
    }

    public int getjBegin() {
        return jBegin;
    }

    public Integer getLength() {
        return length;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CrosswordVariable{" +
                "value='" + value + '\'' +
                ", iBegin=" + iBegin +
                ", jBegin=" + jBegin +
                ", length=" + length +
                ", orientation=" + orientation +
                ", domain=" + domain +
                '}';
    }
}
