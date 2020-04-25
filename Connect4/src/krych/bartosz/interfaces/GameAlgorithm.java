package krych.bartosz.interfaces;

import krych.bartosz.classes.Move;
import krych.bartosz.classes.State;

public interface GameAlgorithm {
    Move findMove(State state);

    int getPlayer();
}
