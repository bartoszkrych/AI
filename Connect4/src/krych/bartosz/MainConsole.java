package krych.bartosz;

import krych.bartosz.classes.GameConsole;
import krych.bartosz.classes.algorithms.AlphaBeta;
import krych.bartosz.classes.algorithms.MinMax;
import krych.bartosz.classes.functions.ThreeInLineEstFun;

public class MainConsole {

    public static void main(String[] args) {
//        Research research = new Research(new GameConsole());
//
//        new GameConsole().startHumVsAi(new NegaScout(5,Consts.P_2, new ThreeInLineEstFun()));
        new GameConsole().startAiVsAi(new AlphaBeta(4, Consts.P_1, new ThreeInLineEstFun()), new MinMax(7, Consts.P_2, new ThreeInLineEstFun()), true);

//        research.comparingOne(Consts.AlphaBeta, Consts.ThreeInLine, Consts.ThreeInLine);
//        research.comparingOne(Consts.AlphaBeta, Consts.TwoThreeInLine, Consts.ThreeInLine);
//        research.comparingOne(Consts.AlphaBeta, Consts.TwoThreeInLine, Consts.TwoThreeInLine);
//        research.fightMMAB(Consts.TwoThreeInLine, Consts.TwoThreeInLine);

        /*research.comparingOne(Consts.MinMax, Consts.ThreeInLine, Consts.ThreeInLine);
        research.comparingOne(Consts.MinMax, Consts.TwoThreeInLine, Consts.ThreeInLine);
        research.comparingOne(Consts.MinMax, Consts.TwoThreeInLine, Consts.TwoThreeInLine);

        research.comparingMMvsAB(Consts.ThreeInLine, Consts.ThreeInLine);
        research.comparingMMvsAB(Consts.TwoThreeInLine, Consts.ThreeInLine);
        research.comparingMMvsAB(Consts.TwoThreeInLine, Consts.TwoThreeInLine);*/
    }
}
