package krych.bartosz;

import krych.bartosz.abstra.*;
import krych.bartosz.crossword.CrosswordVariable;
import krych.bartosz.sudoku.SudokuVariable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static krych.bartosz.crossword.Orientation.VERTICAL;

public class Backtracking<P extends Problem<V>, C extends Constraint<T, V>, V extends Variable<T>, T, H extends VarHeuristic<V>, D extends DomHeuristic<V>> {
    private P problem;
    private C constraint;
    private H varHeuristic;
    private D domHeuristic;
    private List<V> variables;


    private int resultsCount;
    private int reversionsFirst;
    private int nodesFirst;
    private long timeFirst;
    private int reversions;
    private int nodes;
    private long startTime;

    public Backtracking(P problem, C constraint, H varHeuristic, D domHeuristic) {
        this.problem = problem;
        this.constraint = constraint;
        this.varHeuristic = varHeuristic;
        this.domHeuristic = domHeuristic;
    }

    public void start() {
        initValues();
        startTime = System.nanoTime();
        execute(0);
        System.out.println("results: " + resultsCount + "\nMethod executed      ->      reversions: " + reversions + ",  nodes: " + nodes + ", time:" + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + "ms");
        if (resultsCount > 0)
            System.out.println("First result         ->      reversions: " + reversionsFirst + ",  nodes: " + nodesFirst + ", time:" + TimeUnit.NANOSECONDS.toMillis(timeFirst) + "ms");
        System.out.println();
    }

    private boolean execute(int n) {
        if (n == variables.size()) {
            resultsCount++;
            if (resultsCount == 1) {
                timeFirst = System.nanoTime() - startTime;
                reversionsFirst = reversions;
                nodesFirst = nodes;
            }
//            printResult(variables);
            return false;
        }

        //special for sudoku but sudoku dont working
        if (variables.get(n).getValue() != null) {
            return execute(n + 1);
        }

        for (T k : variables.get(n).getDomain()) {
            nodes++;
            if (constraint.isGood(variables, variables.get(n), k)) {
                variables.get(n).setValue(k);
                if (execute(n + 1))
                    return true;
            }
        }
        reversions++;
        variables.get(n).setValue(null);
        return false;
    }

    private void initValues() {
        resultsCount = 0;
        reversionsFirst = 0;
        nodesFirst = 0;
        timeFirst = 0;
        reversions = 0;
        nodes = 0;
        variables = varHeuristic.sort(problem.getVariables());
        domHeuristic.sort(variables);
    }

    private void printResult(List<V> list) {
        System.out.println("#####       RESULT - " + resultsCount + "       #####");
        if (list.get(0) instanceof CrosswordVariable) {
            char[][] result = new char[problem.getHeight()][problem.getWidth()];
            List<CrosswordVariable> listA = (List<CrosswordVariable>) list;

            for (CrosswordVariable v : listA) {
                int col = v.getjBegin();
                int row = v.getiBegin();
                int len = v.getLength();

                if (v.getOrientation() == VERTICAL) {
                    for (int i = 0; i < len; i++)
                        result[i + row][col] = v.getValue().charAt(i);
                } else {
                    for (int i = 0; i < len; i++)
                        result[row][i + col] = v.getValue().charAt(i);
                }
            }

            for (char[] chars : result) {
                for (int j = 0; j < result[0].length; j++) {
                    if (chars[j] == 0) System.out.print("# ");
                    else System.out.print(chars[j] + " ");
                }
                System.out.println();
            }

            System.out.println("\n");
        } else {
            List<SudokuVariable> listA = (List<SudokuVariable>) list;
            int i = 0;
            for (SudokuVariable v : listA) {
                Integer val = v.getValue();
                System.out.print(val == null ? 0 : val);
                System.out.print(" ");
                if (v.getJ() == 2 || v.getJ() == 5) System.out.print("| ");
                if (v.getI() == 2 || v.getI() == 5) System.out.print("\n---------------------");
                if (v.getJ() == 8) System.out.println();
            }

        }
    }


}

