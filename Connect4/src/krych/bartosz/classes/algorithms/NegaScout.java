package krych.bartosz.classes.algorithms;

import krych.bartosz.Consts;
import krych.bartosz.classes.Move;
import krych.bartosz.classes.State;
import krych.bartosz.classes.functions.EstimateFunction;

import java.util.List;

public class NegaScout extends GameAlgorithm {

    private final int depth;
    private final int player;
    private final EstimateFunction fitFun;
    private Move scoutMove;
    private final String nameAlg = "NegaScout";

    public NegaScout(int depth, int player, EstimateFunction fitFun) {
        this.depth = depth;
        this.player = player;
        this.fitFun = fitFun;
    }

    public Move findMove(State state) {
        scoutMove = new Move();
        return negaScout2(new State(state), 0, new Move(Integer.MIN_VALUE), new Move(Integer.MAX_VALUE), player);
//        negaScoutInt(new State(state), 0, Integer.MIN_VALUE, Integer.MAX_VALUE, player);
//        return scoutMove;
    }

    private Move negaScout(State state, int curDepth, Move alpha, Move beta, Integer curPlayer) {
        if ((state.isEnd()) || (curDepth == depth)) {
            return new Move(state.getLastMove().getRow(), state.getLastMove().getCol(), fitFun.makeEstimate(state));
        }
        int opponent;
        if (curPlayer.equals(Consts.P_1)) opponent = Consts.P_2;
        else opponent = Consts.P_1;

        List<State> nodes = generateNodes(curPlayer, state);
        for (int i = 0; i < nodes.size(); i++) {
            Move move;
            //if child is first child then
            if (i == 0) {
                // score := −pvs(child, depth − 1, −β, −α, −color)
                move = negaScout(nodes.get(i), curDepth + 1, beta.scoutChange(), alpha.scoutChange(), opponent).scoutChange();
            } else {
                //  score := −pvs(child, depth − 1, −α − 1, −α, −color)
                move = negaScout(nodes.get(i), curDepth + 1, alpha.scoutChangeMinus(), alpha.scoutChange(), opponent).scoutChange();
                // if α < score < β then
                if (alpha.getEstimate() < move.getEstimate() && move.getEstimate() < beta.getEstimate()) {
                    // score := −pvs(child, depth − 1, −β, −score, −color)
                    move = negaScout(nodes.get(i), curDepth + 1, beta.scoutChange(), move.scoutChange(), opponent).scoutChange();
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

    private int negaScoutInt(State state, int curDepth, int alpha, int beta, int curPlayer) {
        if ((state.isEnd()) || (curDepth == depth)) {
            return fitFun.makeEstimate(state);
        }
        int opponent;
        if (curPlayer == Consts.P_1) opponent = Consts.P_2;
        else opponent = Consts.P_1;
        List<State> nodes = generateNodes(curPlayer, state);
        for (int i = 0; i < nodes.size(); i++) {
            int score = 0;
            //if child is first child then
            if (i == 0) {
                // score := −pvs(child, depth − 1, −β, −α, −color)
                score = -negaScoutInt(nodes.get(i), curDepth + 1, -beta, -alpha, opponent);
            } else {
                //  score := −pvs(child, depth − 1, −α − 1, −α, −color)
                score = -negaScoutInt(nodes.get(i), curDepth + 1, -alpha - 1, -alpha, opponent);
                // if α < score < β then
                if (alpha < score && score < beta) {
                    // score := −pvs(child, depth − 1, −β, −score, −color)
                    score = -negaScoutInt(nodes.get(i), curDepth + 1, -beta, -score, opponent);
                }
            }
            // α := max(α, score)
            if (score > alpha) {
                alpha = score;
                Move node = nodes.get(i).getLastMove();
                scoutMove.setCol(node.getCol());
                scoutMove.setRow(node.getRow());
                scoutMove.setEstimate(score);
            }
            // if α ≥ β then
            if (alpha >= beta) break;
        }
        return alpha;
    }

    private Move negaScout2(State state, int curDepth, Move alpha, Move beta, Integer curPlayer) {
        if ((state.isEnd()) || (curDepth == depth)) {
            return new Move(state.getLastMove().getRow(), state.getLastMove().getCol(), fitFun.makeEstimate(state));
        }
        int opponent;
        if (curPlayer.equals(Consts.P_1)) opponent = Consts.P_2;
        else opponent = Consts.P_1;

        List<State> nodes = generateNodes(curPlayer, state);
        for (int i = 0; i < nodes.size(); i++) {
            Move move = new Move();
            //if child is first child then
            if (i == 0) {
                // score := −pvs(child, depth − 1, −β, −α, −color)
                setNewValues(move, nodes.get(i), negaScout2(nodes.get(i), curDepth + 1, beta.scoutChange(), alpha.scoutChange(), opponent).scoutChange());
            } else {
                //  score := −pvs(child, depth − 1, −α − 1, −α, −color)
                setNewValues(move, nodes.get(i), negaScout2(nodes.get(i), curDepth + 1, alpha.scoutChangeMinus(), alpha.scoutChange(), opponent).scoutChange());
                // if α < score < β then
                if (alpha.getEstimate() < move.getEstimate() && move.getEstimate() < beta.getEstimate()) {
                    // score := −pvs(child, depth − 1, −β, −score, −color)
                    setNewValues(move, nodes.get(i), negaScout2(nodes.get(i), curDepth + 1, beta.scoutChange(), move.scoutChange(), opponent).scoutChange());
                }
            }
            // α := max(α, score)
            if (move.getEstimate() > alpha.getEstimate()) {
                setNewValues(alpha, nodes.get(i), move);
            }
            // if α ≥ β then
            if (alpha.getEstimate() >= beta.getEstimate()) break;
        }
        return alpha;
    }

    public int getPlayer() {
        return player;
    }

    public String getNameAlg() {
        return nameAlg;
    }
}
