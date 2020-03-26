package krych.bartosz;

import krych.bartosz.Crossword.Crossword;
import krych.bartosz.Crossword.CrosswordConstraint;
import krych.bartosz.Crossword.CrosswordVariable;
import krych.bartosz.abstra.Constraint;
import krych.bartosz.abstra.Problem;
import krych.bartosz.sudoku.Sudoku;
import krych.bartosz.sudoku.SudokuConstraint;
import krych.bartosz.sudoku.SudokuVariable;

import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class Backtracking {
    private Problem problem;
    private Constraint c;
    private int resultsCount = 0;
    private Stack<SudokuVariable> sudokuVariableStack = new Stack<>();
    private Stack<CrosswordVariable> crosswordVariableStack = new Stack<>();
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

        for (Integer val : sudokuVariableStack.peek().getDomain()) {
            leaves++;
            if (((SudokuConstraint) c).isGood(result, i, j, val)) {
                sudokuVariableStack.peek().setValue(val);
                result[i][j] = val;
                if (execute(result, position + 1)) {
                    return true;
                } else {
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
            printResult(crosswordVariableStack);
            return false;
        }
        crosswordVariableStack.push(crosswordVariables.get(n));

        for (String k : crosswordVariableStack.peek().getDomain()) {
            leaves++;
            if (((CrosswordConstraint) c).isGood(crosswordVariableStack, k)) {
                crosswordVariableStack.peek().setValue(k);
                if (execute(n + 1)) {
                    return true;
                } else {
                    crosswordVariableStack.peek().setValue(null);
                }
            }
        }
        reversion++;
        crosswordVariableStack.pop();
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

    private void printResult(Stack<CrosswordVariable> stack) {
        char[][] result = new char[((Crossword) problem).getHeight()][((Crossword) problem).getWidth()];

        for (CrosswordVariable v : stack) {
            int col = v.getjBegin();
            int row = v.getiBegin();
            int len = v.getLength();

            switch (v.getOrientation()) {
                case VERTICAL: {
                    for (int i = 0; i < len; i++) {
                        result[i + row][col] = v.getValue().charAt(i);
                    }
                    break;
                }
                case HORIZONTAL: {
                    for (int i = 0; i < len; i++) {
                        result[row][i + col] = v.getValue().charAt(i);
                    }
                    break;
                }
            }
        }

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                if (result[i][j] == 0) System.out.print("# ");
                else System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("\n");
    }

}

