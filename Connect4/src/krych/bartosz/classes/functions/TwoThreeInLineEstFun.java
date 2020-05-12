package krych.bartosz.classes.functions;

import krych.bartosz.Consts;
import krych.bartosz.classes.State;

import java.util.List;

public class TwoThreeInLineEstFun extends ThreeInLineMethod implements EstimateFunction {

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
                + counterThreeInLine(state, Consts.P_1) * 100 + counterTwoInLine(state, Consts.P_1) * 10
                - counterThreeInLine(state, Consts.P_2) * 100 + counterTwoInLine(state, Consts.P_2) * 10;
    }

    @Override
    public String getNameEst() {
        return "TwoThree";
    }

    private int counterTwoInLine(State state, int playerToCheck) {
        List<List<Integer>> board = state.getBoard();
        int counter = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Integer toCheck = board.get(i).get(j);
                if (playerToCheck != toCheck) continue;

                if (state.canMove(i, j + 1) && toCheck.equals(board.get(i).get(j + 1)))
                    counter++;
                if (state.canMove(i - 1, j) && toCheck.equals(board.get(i - 1).get(j)))
                    counter++;
                if (state.canMove(i + 1, j + 1) && toCheck.equals(board.get(i + 1).get(j + 1)))
                    counter++;
                if (state.canMove(i - 1, j + 1) && toCheck.equals(board.get(i - 1).get(j + 1)))
                    counter++;
            }
        }
        return counter;
    }
}
