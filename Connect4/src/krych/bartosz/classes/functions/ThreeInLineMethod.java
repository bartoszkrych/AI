package krych.bartosz.classes.functions;

import krych.bartosz.Consts;
import krych.bartosz.classes.State;

import java.util.List;

public abstract class ThreeInLineMethod {

    protected int rows = Consts.ROWS;
    protected int cols = Consts.COLS;

    protected int counterThreeInLine(State state, int playerToCheck) {
        List<List<Integer>> board = state.getBoard();
        int counter = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Integer toCheck = board.get(i).get(j);
                if (playerToCheck != toCheck) continue;

                if (state.canMove(i, j + 2)
                        && toCheck.equals(board.get(i).get(j + 1))
                        && toCheck.equals(board.get(i).get(j + 2)))
                    counter++;

                if (state.canMove(i - 2, j)
                        && toCheck.equals(board.get(i - 1).get(j))
                        && toCheck.equals(board.get(i - 2).get(j)))
                    counter++;

                if (state.canMove(i + 2, j + 2)
                        && toCheck.equals(board.get(i + 1).get(j + 1))
                        && toCheck.equals(board.get(i + 2).get(j + 2)))
                    counter++;

                if (state.canMove(i - 2, j + 2)
                        && toCheck.equals(board.get(i - 1).get(j + 1))
                        && toCheck.equals(board.get(i - 2).get(j + 2)))
                    counter++;
            }
        }
        return counter;
    }
}
