package krych.bartosz.classes.algorithms;

import krych.bartosz.classes.Consts;
import krych.bartosz.classes.Move;
import krych.bartosz.classes.State;
import krych.bartosz.interfaces.EstimateFunction;
import krych.bartosz.interfaces.GameAlgorithm;

import java.util.List;

public class NegaScout implements GameAlgorithm {

    private int depth;
    private int player;
    private EstimateFunction fitFun;

    public NegaScout(int depth, int player, EstimateFunction fitFun) {
        this.depth = depth;
        this.player = player;
        this.fitFun = fitFun;
    }

    @Override
    public Move findMove(State state) {
        return negaScout(state, 0, new Move(Integer.MIN_VALUE), new Move(Integer.MAX_VALUE), player);
    }

    private Move negaScout(State state, int curDepth, Move alpha, Move beta, int curPlayer) {
        if ((state.isEnd()) || (curDepth == depth)) {
            return new Move(state.getLastMove().getRow(), state.getLastMove().getCol(), fitFun.makeEstimate(state));
        }
        int opponent;
        if (curPlayer == Consts.P_1) opponent = Consts.P_2;
        else opponent = Consts.P_1;
        List<State> nodes = state.generateNodes(curPlayer);
        for (int i = 0; i < nodes.size(); i++) {
            Move move;
            if (i == 0) {
                move = negaScout(nodes.get(i), curDepth++, beta.scoutChange(), alpha.scoutChange(), opponent).scoutChange();
                move.setEstimate(-1 * move.getEstimate());
                minMaxHelper(move, nodes.get(i), move);
            } else {
                move = negaScout(nodes.get(i), curDepth++, alpha.scoutChangeMinus(), alpha.scoutChange(), opponent).scoutChange();
                move.setEstimate(-1 * move.getEstimate());
                minMaxHelper(move, nodes.get(i), move);
                if (alpha.getEstimate() < move.getEstimate() && move.getEstimate() < beta.getEstimate()) {
                    move = negaScout(nodes.get(i), curDepth++, beta.scoutChange(), move.scoutChange(), opponent).scoutChange();
                    move.setEstimate(-1 * move.getEstimate());
                    minMaxHelper(move, nodes.get(i), move);
                }
            }
            if (move.getEstimate() > alpha.getEstimate())
                minMaxHelper(alpha, nodes.get(i), move);
            if (alpha.getEstimate() >= beta.getEstimate()) break;
        }
        return alpha;
    }

    @Override
    public int getPlayer() {
        return player;
    }


    private void minMaxHelper(Move minMaxMove, State node, Move move) {
        minMaxMove.setRow(node.getLastMove().getRow());
        minMaxMove.setCol(node.getLastMove().getCol());
        minMaxMove.setEstimate(move.getEstimate());
    }
}
