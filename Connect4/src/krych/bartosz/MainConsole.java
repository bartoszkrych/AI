package krych.bartosz;

import krych.bartosz.classes.GameConsole;

public class MainConsole {

    public static void main(String[] args) {
        Research research = new Research(new GameConsole());

//        new GameConsole().startHumVsAi(new NegaScout(5,Consts.P_2, new ThreeInLineEstFun()));

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
