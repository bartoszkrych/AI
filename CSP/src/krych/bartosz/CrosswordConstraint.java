package krych.bartosz;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class CrosswordConstraint {

    public CrosswordConstraint() {
    }

    public boolean contains(Stack<CrosswordVariable> crosswordVariableList, String value) {
        List<String> strings = crosswordVariableList.stream().map(CrosswordVariable::getValue).collect(Collectors.toList());
        return strings.contains(value);
    }

    public boolean isWrongIntersection(Stack<CrosswordVariable> crosswordVariables, String value) {
        List<CrosswordVariable> list = crosswordVariables.stream().filter(x -> x.getValue() != null).collect(Collectors.toList());
        CrosswordVariable toCheck = crosswordVariables.peek();
        int iCheck = toCheck.getiBegin();
        int jCheck = toCheck.getjBegin();
        int maxCheck;
        if (toCheck.getOrientation() == Orientation.VERTICAL) {
            maxCheck = iCheck + toCheck.getLength();
            List<CrosswordVariable> hList = list.stream()
                    .filter(x -> x.getOrientation() == Orientation.HORIZONTAL)
                    .filter(x -> x.getiBegin() >= iCheck && x.getiBegin() < maxCheck)
                    .filter(x -> x.getjBegin() <= jCheck && x.getjBegin() + x.getLength() - 1 >= jCheck).collect(Collectors.toList());
            if (!hList.isEmpty()) {
                for (CrosswordVariable v : hList) {
                    int indexV = jCheck - v.getjBegin();
                    int indexChecked = v.getiBegin() - iCheck;

                    if (v.getValue().charAt(indexV) != value.charAt(indexChecked)) return true;
                }
            }
        } else {
            maxCheck = jCheck + toCheck.getLength();
            List<CrosswordVariable> vList = list.stream()
                    .filter(x -> x.getOrientation() == Orientation.VERTICAL)
                    .filter(x -> x.getjBegin() >= jCheck && x.getjBegin() < maxCheck)
                    .filter(x -> x.getiBegin() <= iCheck && x.getiBegin() + x.getLength() - 1 >= iCheck).collect(Collectors.toList());
            if (!vList.isEmpty()) {
                for (CrosswordVariable v : vList) {
                    int indexV = iCheck - v.getiBegin();
                    int indexChecked = v.getjBegin() - jCheck;
                    if (v.getValue().charAt(indexV) != value.charAt(indexChecked)) return true;
                }
            }
        }
        return false;
    }
}
