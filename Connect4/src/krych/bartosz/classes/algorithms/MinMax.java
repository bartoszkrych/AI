package krych.bartosz.classes.algorithms;

import krych.bartosz.classes.Consts;
import krych.bartosz.classes.Move;
import krych.bartosz.classes.State;
import krych.bartosz.interfaces.EstimateFunction;
import krych.bartosz.interfaces.GameAlgorithm;

import java.util.List;

public class MinMax implements GameAlgorithm {

    private int depth;
    private int player;
    private EstimateFunction fitFun;

    public MinMax(int depth, int player, EstimateFunction fitFun) {
        this.depth = depth;
        this.player = player;
        this.fitFun = fitFun;
    }

    @Override
    public Move findMove(State state) {
        if (player == Consts.P_1) {
            return max(new State(state), 0);
        }
        return min(new State(state), 0);
    }

    public Move max(State state, int curDepth) {
        if ((state.isEnd()) || (curDepth == depth)) {
            return new Move(state.getLastMove().getRow(), state.getLastMove().getCol(), fitFun.makeEstimate(state));
        }
        List<State> nodes = state.generateNodes(Consts.P_1);
        Move maxMove = new Move(Integer.MIN_VALUE);
        for (State node : nodes) {
            Move move = min(node, curDepth + 1);
            if (move.getEstimate() > maxMove.getEstimate()) minMaxHelper(maxMove, node, move);
        }
        return maxMove;
    }

    public Move min(State state, int curDepth) {
        if ((state.isEnd()) || (curDepth == depth)) {
            return new Move(state.getLastMove().getRow(), state.getLastMove().getCol(), fitFun.makeEstimate(state));
        }
        List<State> nodes = state.generateNodes(Consts.P_2);
        Move minMove = new Move(Integer.MAX_VALUE);
        for (State node : nodes) {
            Move move = max(node, curDepth + 1);
            if (move.getEstimate() < minMove.getEstimate()) minMaxHelper(minMove, node, move);
        }
        return minMove;
    }

    private void minMaxHelper(Move minMaxMove, State node, Move move) {
        minMaxMove.setRow(node.getLastMove().getRow());
        minMaxMove.setCol(node.getLastMove().getCol());
        minMaxMove.setEstimate(move.getEstimate());
    }

    public int getPlayer() {
        return player;
    }
}
