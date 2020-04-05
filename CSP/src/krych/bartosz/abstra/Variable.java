package krych.bartosz.abstra;

import java.util.List;

public interface Variable<V> {
    V getValue();

    void setValue(V val);

    List<V> getDomain();
}
