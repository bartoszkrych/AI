package classes;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Integer lastPlayer;
    private Move lastMove;
    private List<List<Integer>> boardGame;
    private boolean gameover = false;
    private Integer turn = 1;
    private Integer winner;

    private boolean overflowSome = false;

    public Board() {
        this.lastPlayer = Consts.EMPTY;
        this.lastMove = new Move();
        this.boardGame = new ArrayList<>();
        for (int i = 0; i < GameParams.rows; i++) {
            boardGame.add(new ArrayList<>());
            for (int j = 0; j < GameParams.cols; j++) {
                boardGame.get(i).set(j, Consts.EMPTY);
            }
        }
    }

    public Board(Board board) {
        lastMove = board.lastMove;
        lastPlayer = board.lastPlayer;
        winner = board.winner;
        this.boardGame = new ArrayList<>();
        for (int i = 0; i < GameParams.rows; i++) {
            boardGame.add(new ArrayList<>());
            for (int j = 0; j < GameParams.cols; j++) {
                boardGame.get(i).set(j, board.boardGame.get(i).get(j));
            }
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
        boardGame.get(row).set(col, player);
        turn++;
    }

    public void backMove(int row, int col, int player) {
        boardGame.get(row).set(col, Consts.EMPTY);
        if (player == Consts.P_1) {
            lastPlayer = Consts.P_2;
        } else if (player == Consts.P_2) {
            lastPlayer = Consts.P_1;
        }
        if (turn > 1) turn--;
    }

    public List<Board> generateNodes(int player) {
        List<Board> nodes = new ArrayList<>();
        for (int i = 0; i < GameParams.cols; i++) {
            if (!isFullCol(i)) {
                Board node = new Board(this);
                node.nextMove(i, player);
                nodes.add(node);
            }
        }
        return nodes;
    }

    private Integer getEmptyRow(int col) {
        int lastRow = -1;
        for (int i = 0; i < GameParams.rows; i++) {
            if (boardGame.get(i).get(col).equals(Consts.EMPTY)) lastRow = i;
        }
        return lastRow;
    }

    public boolean isFullCol(int col) {
        return !boardGame.get(0).get(col).equals(Consts.EMPTY);
    }
}
