package krych.bartosz.classes;

import java.util.ArrayList;
import java.util.List;

public class State {
    private Integer lastPlayer;
    private Move lastMove;
    private List<List<Integer>> board;
    private Integer winner;
    private int rows = Consts.ROWS;
    private int cols = Consts.COLS;

    public State() {
        this.lastPlayer = Consts.P_2;
        this.lastMove = new Move();
        this.board = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(Consts.EMPTY);
            }
            board.add(row);
        }
    }

    public State(State state) {
        lastMove = state.lastMove;
        lastPlayer = state.lastPlayer;
        winner = state.winner;
        this.board = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(state.board.get(i).get(j));
            }
            board.add(row);
        }
    }

    public void nextMove(int col, int player) {
        int row = getEmptyRow(col);
        if (row == -1) {
            System.err.println("Full column: " + (col + 1) + " !!");
            return;
        }
        lastMove = new Move(row, col);
        lastPlayer = player;
        board.get(row).set(col, player);
    }

    public List<State> generateNodes(int player) {
        List<State> nodes = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            if (!isFullCol(i)) {
                State node = new State(this);
                node.nextMove(i, player);
                nodes.add(node);
            }
        }
        return nodes;
    }

    public boolean isWin() {
        if (checkerWin())
            return true;
        winner = Consts.EMPTY;
        return false;
    }

    public boolean isEnd() {
        if (isWin()) return true;
        return !board.get(0).contains(Consts.EMPTY);
    }

    private boolean checkerWin() {
        for (int i = 0; i < rows; i++) {
            List<Integer> row = board.get(i);
            for (int j = 0; j < cols; j++) {
                Integer toCheck = board.get(i).get(j);
                if (toCheck.equals(Consts.EMPTY)) continue;

                if (canMove(i, j + 3)
                        && row.get(j).equals(row.get(j + 1))
                        && row.get(j).equals(row.get(j + 2))
                        && row.get(j).equals(row.get(j + 3))) {
                    winner = row.get(j);
                    return true;
                }

                if (canMove(i - 3, j)
                        && toCheck.equals(board.get(i - 1).get(j))
                        && toCheck.equals(board.get(i - 2).get(j))
                        && toCheck.equals(board.get(i - 3).get(j))) {
                    winner = toCheck;
                    return true;

                }
                if (canMove(i + 3, j + 3)
                        && toCheck.equals(board.get(i + 1).get(j + 1))
                        && toCheck.equals(board.get(i + 2).get(j + 2))
                        && toCheck.equals(board.get(i + 3).get(j + 3))) {
                    winner = toCheck;
                    return true;
                }

                if (canMove(i - 3, j + 3)
                        && toCheck.equals(board.get(i - 1).get(j + 1))
                        && toCheck.equals(board.get(i - 2).get(j + 2))
                        && toCheck.equals(board.get(i - 3).get(j + 3))) {
                    winner = toCheck;
                    return true;
                }

            }
        }
        return false;
    }

    private Integer getEmptyRow(int col) {
        int lastRow = -1;
        for (int i = 0; i < rows; i++) {
            if (board.get(i).get(col).equals(Consts.EMPTY)) lastRow = i;
        }
        return lastRow;
    }

    public boolean isFullCol(int col) {
        return !board.get(0).get(col).equals(Consts.EMPTY);
    }

    public boolean canMove(int row, int col) {
        return (row > -1) && (col > -1) && (row <= 5) && (col <= 6);
    }

    public Integer getWinner() {
        return winner;
    }

    public List<List<Integer>> getBoard() {
        return board;
    }

    public Integer getLastPlayer() {
        return lastPlayer;
    }

    public void setLastPlayer(Integer lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    public Move getLastMove() {
        return lastMove;
    }
}
