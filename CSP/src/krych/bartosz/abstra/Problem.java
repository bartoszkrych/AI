package krych.bartosz.abstra;

import java.util.List;

public interface Problem<V> {
    void printBoard();

    List<V> getVariables();

    int getHeight();

    int getWidth();
}
