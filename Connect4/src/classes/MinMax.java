package classes;

import interfaces.FitnessFunction;
import interfaces.GameAlgorithm;

import java.util.List;
import java.util.Random;

public class MinMax implements GameAlgorithm {

    private int depth;
    private int player;
    private FitnessFunction fitFun;
    private Random r = new Random();

    public MinMax(int depth, int player, FitnessFunction fitFun) {
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
            return new Move(state.getLastMove().getRow(), state.getLastMove().getCol(), fitFun.calcFitness(state));
        }
        List<State> nodes = state.generateNodes(Consts.P_1);
        Move maxMove = new Move(Integer.MIN_VALUE);
        for (State node : nodes) {
            Move move = min(node, curDepth + 1);
            if (move.getFitness() >= maxMove.getFitness()) minMaxHelper(maxMove, node, move);
        }
        return maxMove;
    }

    public Move min(State state, int curDepth) {
        if ((state.isEnd()) || (curDepth == depth)) {
            return new Move(state.getLastMove().getRow(), state.getLastMove().getCol(), fitFun.calcFitness(state));
        }
        List<State> nodes = state.generateNodes(Consts.P_2);
        Move minMove = new Move(Integer.MAX_VALUE);
        for (State node : nodes) {
            Move move = max(node, curDepth + 1);
            if (move.getFitness() <= minMove.getFitness()) minMaxHelper(minMove, node, move);
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
