package classes;

import interfaces.GameAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinMax implements GameAlgorithm {

    private int depth;
    private int player;
    private Random r = new Random();

    public MinMax(int depth, int player) {
        this.depth = depth;
        this.player = player;
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
            return new Move(state.getLastMove().getRow(), state.getLastMove().getCol(), state.calcFitness());
        }
        List<State> children = new ArrayList(state.generateNodes(Consts.P_1));
        Move maxMove = new Move(Integer.MIN_VALUE);
        for (State child : children) {
            Move move = min(child, curDepth + 1);
            if (move.getFitness() >= maxMove.getFitness()) minMaxHelper(maxMove, child, move);
        }
        return maxMove;
    }

    public Move min(State state, int curDepth) {
        if ((state.isEnd()) || (curDepth == depth)) {
            return new Move(state.getLastMove().getRow(), state.getLastMove().getCol(), state.calcFitness());
        }
        List<State> children = new ArrayList(state.generateNodes(Consts.P_2));
        Move minMove = new Move(Integer.MAX_VALUE);
        for (State child : children) {
            Move move = max(child, curDepth+ 1);
            if (move.getFitness() <= minMove.getFitness()) minMaxHelper(minMove, child, move);
        }
        return minMove;
    }

    private void minMaxHelper(Move minMove, State child, Move move) {
        if ((move.getFitness().equals(minMove.getFitness()))) {
            if (r.nextInt(2) == 0) {
                minMove.setRow(child.getLastMove().getRow());
                minMove.setCol(child.getLastMove().getCol());
                minMove.setFitness(move.getFitness());
            }
        } else {
            minMove.setRow(child.getLastMove().getRow());
            minMove.setCol(child.getLastMove().getCol());
            minMove.setFitness(move.getFitness());
        }
    }
}
