package interfaces;

import classes.Move;
import classes.State;

public interface GameAlgorithm {
    Move findMove(State state);
}
