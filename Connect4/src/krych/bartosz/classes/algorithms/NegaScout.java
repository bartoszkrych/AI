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
    private Move scoutMove;

    public NegaScout(int depth, int player, EstimateFunction fitFun) {
        this.depth = depth;
        this.player = player;
        this.fitFun = fitFun;
    }

    @Override
    public Move findMove(State state) {
        scoutMove = new Move();
//        return negaScout(state, 0, new Move(Integer.MIN_VALUE), new Move(Integer.MAX_VALUE), player);
        negaScout2(state, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, player);
        return scoutMove;
    }

    private Move negaScout(State state, int curDepth, Move alpha, Move beta, Integer curPlayer) {
        if ((state.isEnd()) || (curDepth == depth)) {
            return new Move(state.getLastMove().getRow(), state.getLastMove().getCol(), fitFun.makeEstimate(state));
        }
        int opponent;
        if (curPlayer.equals(Consts.P_1)) opponent = Consts.P_2;
        else opponent = Consts.P_1;

        List<State> nodes = state.generateNodes(curPlayer);
        for (int i = 0; i < nodes.size(); i++) {
            Move move;
            //if child is first child then
            if (i == 0) {
                // score := −pvs(child, depth − 1, −β, −α, −color)
                move = negaScout(nodes.get(i), curDepth++, beta.scoutChange(), alpha.scoutChange(), opponent).scoutChange();
            } else {
                //  score := −pvs(child, depth − 1, −α − 1, −α, −color)
                move = negaScout(nodes.get(i), curDepth++, alpha.scoutChangeMinus(), alpha.scoutChange(), opponent).scoutChange();
                // if α < score < β then
                if (alpha.getEstimate() < move.getEstimate() && move.getEstimate() < beta.getEstimate()) {
                    // score := −pvs(child, depth − 1, −β, −score, −color)
                    move = negaScout(nodes.get(i), curDepth++, beta.scoutChange(), move.scoutChange(), opponent).scoutChange();
                }
            }
            // α := max(α, score)
            if (move.getEstimate() > alpha.getEstimate()) {
                Move node = nodes.get(i).getLastMove();
//                alpha = move;
                alpha.setCol(node.getCol());
                alpha.setRow(node.getRow());
                alpha.setEstimate(move.getEstimate());
            }
            // if α ≥ β then
            if (alpha.getEstimate() >= beta.getEstimate()) break;
        }
        return alpha;
    }


    private Integer negaScout2(State state, int curDepth, Integer alpha, Integer beta, Integer curPlayer) {
        if ((state.isEnd()) || (curDepth == depth)) {
            return fitFun.makeEstimate(state);
        }
        int opponent;
        if (curPlayer.equals(Consts.P_1)) opponent = Consts.P_2;
        else opponent = Consts.P_1;
        List<State> nodes = state.generateNodes(curPlayer);
        for (int i = 0; i < nodes.size(); i++) {
            int score;
            //if child is first child then
            if (i == 0) {
                // score := −pvs(child, depth − 1, −β, −α, −color)
                score = -1 * negaScout2(nodes.get(i), curDepth++, -1 * beta, -1 * alpha, opponent);
            } else {
                //  score := −pvs(child, depth − 1, −α − 1, −α, −color)
                score = -1 * negaScout2(nodes.get(i), curDepth++, -1 * alpha - 1, -1 * alpha, opponent);
                // if α < score < β then
                if (alpha < score && score < beta) {
                    // score := −pvs(child, depth − 1, −β, −score, −color)
                    score = -1 * negaScout2(nodes.get(i), curDepth++, -1 * beta, -1 * score, opponent);
                }
            }
            // α := max(α, score)
            if (score > alpha) {
                alpha = score;
                Move node = nodes.get(i).getLastMove();
                scoutMove.setCol(node.getCol());
                scoutMove.setRow(node.getRow());
                scoutMove.setEstimate(alpha);
            }
            // if α ≥ β then
            if (alpha >= beta) break;
        }
        return alpha;
    }

    @Override
    public int getPlayer() {
        return player;
    }
}
