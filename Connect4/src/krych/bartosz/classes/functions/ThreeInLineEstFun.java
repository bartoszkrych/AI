package krych.bartosz.classes.functions;

import krych.bartosz.Consts;
import krych.bartosz.classes.State;

public class ThreeInLineEstFun extends ThreeInLineMethod implements EstimateFunction {

    @Override
    public Integer makeEstimate(State state) {
        int estimate = 0;
        if (state.isWin()) {
            if (state.getWinner().equals(Consts.P_1)) {
                estimate = 1000;
            } else if (state.getWinner().equals(Consts.P_2)) {
                estimate = -1000;
            }
        }
        estimate += counterThreeInLine(state, Consts.P_1) * 100;
        estimate -= counterThreeInLine(state, Consts.P_2) * 100;
        return estimate;
    }

    @Override
    public String getNameEst() {
        return "Three";
    }


}
