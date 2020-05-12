package krych.bartosz.interfaces;

import krych.bartosz.classes.Consts;
import krych.bartosz.classes.Move;
import krych.bartosz.classes.State;

import java.util.ArrayList;
import java.util.List;


public abstract class GameAlgorithm {
    abstract public Move findMove(State state);

    abstract public int getPlayer();

    abstract public String getNameAlg();

    protected List<State> generateNodes(int player, State state) {
        List<State> nodes = new ArrayList<>();
        List<List<Integer>> board = state.getBoard();
        for (int i = 0; i < Consts.COLS; i++) {
            if (board.get(0).get(i).equals(Consts.EMPTY)) {
                State node = new State(state);
                node.nextMove(i, player);
                nodes.add(node);
            }
        }
        return nodes;
    }

    protected void setNewValues(Move to, State node, Move from) {
        to.setRow(node.getLastMove().getRow());
        to.setCol(node.getLastMove().getCol());
        to.setEstimate(from.getEstimate());
    }
}
