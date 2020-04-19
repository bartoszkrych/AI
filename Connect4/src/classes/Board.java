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
    private int rows = GameParams.rows;
    private int cols = GameParams.cols;

    private boolean overflowSome = false;

    public Board() {
        this.lastPlayer = Consts.EMPTY;
        this.lastMove = new Move();
        this.boardGame = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            boardGame.add(new ArrayList<>());
            for (int j = 0; j < cols; j++) {
                boardGame.get(i).set(j, Consts.EMPTY);
            }
        }
    }

    public Board(Board board) {
        lastMove = board.lastMove;
        lastPlayer = board.lastPlayer;
        winner = board.winner;
        this.boardGame = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            boardGame.add(new ArrayList<>());
            for (int j = 0; j < cols; j++) {
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
        for (int i = 0; i < cols; i++) {
            if (!isFullCol(i)) {
                Board node = new Board(this);
                node.nextMove(i, player);
                nodes.add(node);
            }
        }
        return nodes;
    }

    public boolean isWin() {
        if (checkHor() || checkVer() || checkDescDiag() || checkAscDiag())
            return true;
        winner = Consts.EMPTY;
        return false;
    }

    private boolean checkHor() {
        for (int i = 0; i < rows; i++) {
            List<Integer> row = boardGame.get(i);
            for (int j = 0; j < cols; j++) {
                if (canMove(i, j + 3)) {
                    if (row.get(j).equals(row.get(j + 1))
                            && row.get(j).equals(row.get(j + 2))
                            && row.get(j).equals(row.get(j + 3))
                            && !row.get(j).equals(Consts.EMPTY)) {
                        winner = row.get(j);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkVer() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (canMove(i - 3, j)) {
                    if (boardGame.get(i).get(j).equals(boardGame.get(i - 1).get(j))
                            && boardGame.get(i).get(j).equals(boardGame.get(i - 2).get(j))
                            && boardGame.get(i).get(j).equals(boardGame.get(i - 3).get(j))
                            && !boardGame.get(i).get(j).equals(Consts.EMPTY)) {
                        winner = boardGame.get(i).get(j);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkDescDiag() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (canMove(i + 3, j + 3)) {
                    if (boardGame.get(i).get(j).equals(boardGame.get(i + 1).get(j + 1))
                            && boardGame.get(i).get(j).equals(boardGame.get(i + 2).get(j + 2))
                            && boardGame.get(i).get(j).equals(boardGame.get(i + 3).get(j + 3))
                            && !boardGame.get(i).get(j).equals(Consts.EMPTY)) {
                        winner = boardGame.get(i).get(j);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkAscDiag() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (canMove(i + 3, j + 3)) {
                    if (boardGame.get(i).get(j).equals(boardGame.get(i - 1).get(j + 1))
                            && boardGame.get(i).get(j).equals(boardGame.get(i - 2).get(j + 2))
                            && boardGame.get(i).get(j).equals(boardGame.get(i - 3).get(j + 3))
                            && !boardGame.get(i).get(j).equals(Consts.EMPTY)) {
                        winner = boardGame.get(i).get(j);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Integer getEmptyRow(int col) {
        int lastRow = -1;
        for (int i = 0; i < rows; i++) {
            if (boardGame.get(i).get(col).equals(Consts.EMPTY)) lastRow = i;
        }
        return lastRow;
    }

    public boolean isFullCol(int col) {
        return !boardGame.get(0).get(col).equals(Consts.EMPTY);
    }

    public boolean canMove(int row, int col) {
        return (row > -1) && (col > -1) && (row <= 5) && (col <= 6);
    }
}
