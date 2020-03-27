package krych.bartosz;

import krych.bartosz.abstra.Constraint;
import krych.bartosz.abstra.Problem;
import krych.bartosz.crossword.Crossword;
import krych.bartosz.crossword.CrosswordConstraint;
import krych.bartosz.crossword.CrosswordVariable;
import krych.bartosz.sudoku.Sudoku;
import krych.bartosz.sudoku.SudokuConstraint;
import krych.bartosz.sudoku.SudokuVariable;

import java.util.ArrayDeque;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static krych.bartosz.crossword.Orientation.VERTICAL;

public class Backtracking {
    private Problem problem;
    private Constraint c;
    private int resultsCount = 0;
    private ArrayDeque<SudokuVariable> sudokuVariableStack = new ArrayDeque<>();
    private ArrayDeque<CrosswordVariable> dequeCrossVar = new ArrayDeque<>();
    private List<CrosswordVariable> crosswordVariables;
    private int reversionFirst;
    private int leavesFirst;
    private long timeFirst;
    private int reversion;
    private int leaves;
    private long startTime;


    public Backtracking(Sudoku sudoku) {
        this.problem = sudoku;
        this.c = new SudokuConstraint();
    }

    public Backtracking(Crossword crossword) {
        this.problem = crossword;
        this.crosswordVariables = crossword.getVariables();
        this.c = new CrosswordConstraint();
    }

    public void start() {
        reversionFirst = 0;
        leavesFirst = 0;
        timeFirst = 0;
        reversion = 0;
        leaves = 0;
        startTime = System.nanoTime();
        if (problem instanceof Sudoku) {
            int[][] board = ((Sudoku) problem).getInt2D();
            execute(board, 0);
        } else {
            crosswordVariables = VariableHeuristic.sortDesc(crosswordVariables);
            execute(0);
        }
        System.out.println("results: " + resultsCount + ",    reversions: " + reversion + ",  leaves: " + leaves + ", time:" + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + "ms");
        System.out.println("First result:       reversions: " + reversionFirst + ",  leaves: " + leavesFirst + ", time:" + TimeUnit.NANOSECONDS.toMillis(timeFirst) + "ms");
    }

    private boolean execute(int[][] result, int position) {
        if (position == 9 * 9) {
            resultsCount++;
            if (resultsCount == 1) {
                timeFirst = System.nanoTime() - startTime;
                reversionFirst = reversion;
                leavesFirst = leaves;
            }
            printResult(result);
            return false;
        }
        int i = position / 9;
        int j = position % 9;

        if (((Sudoku) problem).getVariable(i, j).getValue() != 0) {
            return execute(result, position + 1);
        }
        sudokuVariableStack.push(((Sudoku) problem).getVariable(i, j));

        assert sudokuVariableStack.peek() != null;
        for (Integer val : sudokuVariableStack.peek().getDomain()) {
            leaves++;
            if (((SudokuConstraint) c).isGood(result, i, j, val)) {
                assert sudokuVariableStack.peek() != null;
                sudokuVariableStack.peek().setValue(val);
                result[i][j] = val;
                if (execute(result, position + 1)) {
                    return true;
                } else {
                    assert sudokuVariableStack.peek() != null;
                    sudokuVariableStack.peek().setValue(0);
                    result[i][j] = 0;
                }
            }
        }
        reversion++;
        sudokuVariableStack.pop();
        result[i][j] = 0;
        return false;
    }

    private boolean execute(int n) {
        if (n == crosswordVariables.size()) {
            resultsCount++;
            if (resultsCount == 1) {
                timeFirst = System.nanoTime() - startTime;
                reversionFirst = reversion;
                leavesFirst = leaves;
            }
            printResult(dequeCrossVar);
            return false;
        }
        dequeCrossVar.push(crosswordVariables.get(n));

        assert dequeCrossVar.peek() != null;
        for (String k : dequeCrossVar.peek().getDomain()) {
            leaves++;
            if (((CrosswordConstraint) c).isGood(dequeCrossVar, k)) {
                assert dequeCrossVar.peek() != null;
                dequeCrossVar.peek().setValue(k);
                if (execute(n + 1)) {
                    return true;
                } else {
                    assert dequeCrossVar.peek() != null;
                    dequeCrossVar.peek().setValue(null);
                }
            }
        }
        reversion++;
        dequeCrossVar.pop();
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

    private void printResult(ArrayDeque<CrosswordVariable> stack) {
        char[][] result = new char[((Crossword) problem).getHeight()][((Crossword) problem).getWidth()];

        for (CrosswordVariable v : stack) {
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

