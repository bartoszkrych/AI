package krych.bartosz.abstra;

import java.util.List;

public interface Heuristic<V extends Variable> {
    List<V> sort(List<V> list);
}
