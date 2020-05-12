package krych.bartosz.classes.functions;

import krych.bartosz.classes.State;

public interface EstimateFunction {
    Integer makeEstimate(State state);

    String getNameEst();
}
