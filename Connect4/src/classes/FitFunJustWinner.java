package classes;

import interfaces.FitnessFunction;

public class FitFunJustWinner implements FitnessFunction {
    @Override
    public Integer calcFitness(State state) {
        if (state.isWin() && state.getLastPlayer().equals(Consts.P_1)) return 1000;
        if (state.isWin() && state.getLastPlayer().equals(Consts.P_2)) return -1000;
        return 0;
    }
}
