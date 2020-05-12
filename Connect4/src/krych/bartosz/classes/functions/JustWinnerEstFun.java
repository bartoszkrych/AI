package krych.bartosz.classes.functions;

import krych.bartosz.Consts;
import krych.bartosz.classes.State;
import krych.bartosz.interfaces.EstimateFunction;

public class JustWinnerEstFun implements EstimateFunction {
    @Override
    public Integer makeEstimate(State state) {
        if (state.isWin() && state.getLastPlayer().equals(Consts.P_1)) return 1000;
        if (state.isWin() && state.getLastPlayer().equals(Consts.P_2)) return -1000;
        return 0;
    }

    @Override
    public String getNameEst() {
        return "Winner";
    }
}
