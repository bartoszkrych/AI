package krych.bartosz;

import krych.bartosz.classes.Consts;
import krych.bartosz.classes.GameConsole;
import krych.bartosz.classes.algorithms.AlphaBeta;
import krych.bartosz.classes.algorithms.MinMax;
import krych.bartosz.classes.functions.ThreeInLineEstFun;

public class MainConsole {

    public static void main(String[] args) {

        new GameConsole().startAiVsAi(
                new MinMax(7, Consts.P_1, new ThreeInLineEstFun()),
                new AlphaBeta(7, Consts.P_2, new ThreeInLineEstFun()),
                true);

    }
}
