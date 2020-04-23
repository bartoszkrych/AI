package krych.bartosz.classes;

import krych.bartosz.interfaces.FitnessFunction;

import java.util.List;

public class FitFunThreeInLine implements FitnessFunction {

    private int rows = Consts.ROWS;
    private int cols = Consts.COLS;

    @Override
    public Integer calcFitness(State state) {
        int fitP1 = 0;
        int fitP2 = 0;
        if (state.isWin()) {
            if (state.getWinner().equals(Consts.P_1)) {
                fitP1 = 100;
            } else if (state.getWinner().equals(Consts.P_2)) {
                fitP2 = 100;
            }
        }

        fitP1 += counterThreeInLine(state, Consts.P_1) * 10;
        fitP2 += counterThreeInLine(state, Consts.P_2) * 10;
        return fitP1 - fitP2;
    }


    public int counterThreeInLine(State state, int playerSymbol) {
        List<List<Integer>> board = state.getBoard();
        int counter = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Integer toCheck = board.get(i).get(j);
                if (state.canMove(i, j + 2) && toCheck.equals(board.get(i).get(j + 1))
                        && toCheck.equals(board.get(i).get(j + 2))
                        && toCheck == playerSymbol) {
                    counter++;
                }


                if (state.canMove(i - 2, j) && toCheck.equals(board.get(i - 1).get(j))
                        && toCheck.equals(board.get(i - 2).get(j))
                        && toCheck == playerSymbol) {
                    counter++;
                }


                if (state.canMove(i + 2, j + 2) && toCheck.equals(board.get(i + 1).get(j + 1))
                        && toCheck.equals(board.get(i + 2).get(j + 2))
                        && (toCheck == playerSymbol)) {
                    counter++;
                }

                if (state.canMove(i - 2, j + 2) && toCheck.equals(board.get(i - 1).get(j + 1))
                        && toCheck.equals(board.get(i - 2).get(j + 2))
                        && toCheck == playerSymbol) {
                    counter++;
                }

            }
        }
        return counter;

    }
}
