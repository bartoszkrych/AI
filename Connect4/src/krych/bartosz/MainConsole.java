package krych.bartosz;

import krych.bartosz.classes.Consts;
import krych.bartosz.classes.GameConsole;
import krych.bartosz.classes.algorithms.NegaScout;
import krych.bartosz.classes.functions.ThreeInLineEstFun;

public class MainConsole {

    public static void main(String[] args) {
//        new GameConsole().startAiVsAi(
//                new AlphaBeta(7, Consts.P_1, new ThreeInLineEstFun()),
//                new NegaScout(10, Consts.P_2, new ThreeInLineEstFun()),
//                true);
        new GameConsole().startHumVsAi(new NegaScout(7, Consts.P_2, new ThreeInLineEstFun()));
    }
}
