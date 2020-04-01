package krych.bartosz.abstra;

import java.util.List;

public interface DomHeuristic<V extends Variable> {
    void sort(List<V> variables);
}
