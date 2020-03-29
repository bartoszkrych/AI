package krych.bartosz;

import krych.bartosz.abstra.Constraint;
import krych.bartosz.abstra.Heuristic;
import krych.bartosz.abstra.Problem;
import krych.bartosz.abstra.Variable;
import krych.bartosz.crossword.Crossword;
import krych.bartosz.crossword.CrosswordVariable;

import java.util.ArrayDeque;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static krych.bartosz.crossword.Orientation.VERTICAL;

public class Backtracking<P extends Problem, C extends Constraint<T, V>, V extends Variable<T>, T, H extends Heuristic<V, T>> {
    private P problem;
    private C constraint;
    private H heuristic;
    private ArrayDeque<V> dequeVar = new ArrayDeque<>();
    private List<V> variables;


    private int resultsCount = 0;
    private int reversionFirst;
    private int leavesFirst;
    private long timeFirst;
    private int reversion;
    private int leaves;
    private long startTime;


    public Backtracking(P problem, C constraint, H heuristic) {
        this.problem = problem;
        this.constraint = constraint;
        this.heuristic = heuristic;
    }

    public void start() {
        reversionFirst = 0;
        leavesFirst = 0;
        timeFirst = 0;
        reversion = 0;
        leaves = 0;
        variables = heuristic.sort(problem.getVariables());

        startTime = System.nanoTime();
        execute(0);
        System.out.println("results: " + resultsCount + "\nMethod executed      ->      reversions: " + reversion + ",  leaves: " + leaves + ", time:" + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + "ms");
        if (resultsCount > 0)
            System.out.println("First result         ->      reversions: " + reversionFirst + ",  leaves: " + leavesFirst + ", time:" + TimeUnit.NANOSECONDS.toMillis(timeFirst) + "ms");
        System.out.println();
    }

    private boolean execute(int n) {
        if (n == variables.size()) {
            resultsCount++;
            if (resultsCount == 1) {
                timeFirst = System.nanoTime() - startTime;
                reversionFirst = reversion;
                leavesFirst = leaves;
            }
//            printResult(dequeVar);
            return false;
        }

        if (variables.get(n).getValue() != null) {
            return execute(n + 1);
        }

        for (T k : variables.get(n).getDomain()) {
            leaves++;
            if (constraint.isGood(variables, variables.get(n), k)) {
                variables.get(n).setValue(k);
                if (execute(n + 1)) {
                    return true;
                } else {
                    variables.get(n).setValue(null);
                }
            }
        }
//        print();
        reversion++;
        variables.get(n).setValue(null);
        return false;
    }

    private void printResult(int[][] tab) {
        System.out.println("#####       RESULT - " + resultsCount + "       #####");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(tab[i][j] + " ");
                if (j == 2 || j == 5) System.out.print("| ");
            }
            if (i == 2 || i == 5) System.out.print("\n---------------------");
            System.out.println();
        }
        System.out.println("\n");
    }

    private void printResult(List<CrosswordVariable> list) {
        char[][] result = new char[((Crossword) problem).getHeight()][((Crossword) problem).getWidth()];

        System.out.println("#####       RESULT - " + resultsCount + "       #####");

        for (CrosswordVariable v : list) {
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
    }

}

