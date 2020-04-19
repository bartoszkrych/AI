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
}
