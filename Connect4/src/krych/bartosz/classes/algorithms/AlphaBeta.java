package krych.bartosz.classes.algorithms;

import krych.bartosz.Consts;
import krych.bartosz.classes.Move;
import krych.bartosz.classes.State;
import krych.bartosz.classes.functions.EstimateFunction;

import java.util.List;

public class AlphaBeta extends GameAlgorithm {
    private final int depth;
    private final int player;
    private final EstimateFunction fitFun;
    private final String nameAlg = "AlphaBeta";

    public AlphaBeta(int depth, int player, EstimateFunction fitFun) {
        this.depth = depth;
        this.player = player;
        this.fitFun = fitFun;
    }

    public Move findMove(State state) {
        if (player == Consts.P_1) {
            return max(new State(state), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        return min(new State(state), 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public int getPlayer() {
        return player;
    }

    public String getNameAlg() {
        return nameAlg;
    }

    public Move max(State state, int curDepth, int alpha, int beta) {
        if ((state.isEnd()) || (curDepth == depth)) {
            return new Move(state.getLastMove().getRow(), state.getLastMove().getCol(), fitFun.makeEstimate(state));
        }
        List<State> nodes = generateNodes(Consts.P_1, state);
        Move maxMove = new Move(Integer.MIN_VALUE);
        for (State node : nodes) {
            Move move = min(node, curDepth + 1, alpha, beta);
            if (move.getEstimate() > alpha) {
                setNewValues(maxMove, node, move);
                alpha = move.getEstimate();
            }
            if (alpha >= beta) break;
        }
        return maxMove;
    }

    public Move min(State state, int curDepth, int alpha, int beta) {
        if ((state.isEnd()) || (curDepth == depth)) {
            return new Move(state.getLastMove().getRow(), state.getLastMove().getCol(), fitFun.makeEstimate(state));
        }
        List<State> nodes = generateNodes(Consts.P_2, state);
        Move minMove = new Move(Integer.MAX_VALUE);
        for (State node : nodes) {
            Move move = max(node, curDepth + 1, alpha, beta);
            if (move.getEstimate() < beta) {
                setNewValues(minMove, node, move);
                beta = move.getEstimate();
            }
            if (alpha >= beta) break;
        }
        return minMove;
    }
}
