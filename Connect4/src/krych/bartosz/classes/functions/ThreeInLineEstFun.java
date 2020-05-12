package krych.bartosz.classes.functions;

import krych.bartosz.Consts;
import krych.bartosz.classes.State;

public class ThreeInLineEstFun extends ThreeInLineMethod implements EstimateFunction {

    @Override
    public Integer makeEstimate(State state) {
        int estimate = 0;
        if (state.isWin()) {
            estimate = 1000;
            if (state.getWinner().equals(Consts.P_2)) {
                estimate = -estimate;
            }
        }
        return estimate
                + counterThreeInLine(state, Consts.P_1) * 100
                - counterThreeInLine(state, Consts.P_2) * 100;
    }

    @Override
    public String getNameEst() {
        return "Three";
    }


}
