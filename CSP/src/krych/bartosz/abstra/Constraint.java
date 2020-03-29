package krych.bartosz.abstra;

import java.util.List;

public interface Constraint<T, V extends Variable<T>> {
    boolean isGood(List<V> variables, V variable, T value);
}
