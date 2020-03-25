package krych.bartosz;

import krych.bartosz.Crossword.Crossword;
import krych.bartosz.Crossword.CrosswordConstraint;
import krych.bartosz.Crossword.CrosswordVariable;
import krych.bartosz.abstra.Constraint;
import krych.bartosz.abstra.Problem;
import krych.bartosz.sudoku.Sudoku;
import krych.bartosz.sudoku.SudokuConstraint;
import krych.bartosz.sudoku.SudokuVariable;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Backtracking {
    private Problem problem;
    private Constraint c;
    private int[][] board;
    private int results = 0;
    private Stack<SudokuVariable> sudokuVariableStack = new Stack<>();
    private Stack<CrosswordVariable> crosswordVariableStack = new Stack<>();
    private List<CrosswordVariable> crosswordVariables;

    public Backtracking(Sudoku sudoku) {
        this.problem = sudoku;
        board = sudoku.getInt2D();
        this.c = new SudokuConstraint();
    }

    public Backtracking(Crossword crossword) {
        this.problem = crossword;
        this.crosswordVariables = crossword.getVariables();
        this.c = new CrosswordConstraint();
    }

    public void start() {
        if (problem instanceof Sudoku) {
            execute(board, 0);
        } else {
            execute(0);
        }
        System.out.println("results: " + results);
    }

    private boolean execute(int result[][], int position) {
        if (position == 9 * 9) {
            results++;
            return false;
        }
        int i = position / 9;
        int j = position % 9;

        if (((Sudoku) problem).getVariable(i, j).getValue() != 0) {
            return execute(result, position + 1);
        }
        sudokuVariableStack.push(((Sudoku) problem).getVariable(i, j));

        for (Integer val : sudokuVariableStack.peek().getDomain()) {
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
        sudokuVariableStack.pop();
        result[i][j] = 0;
        return false;
    }

    private boolean execute(int n) {
        if (n == crosswordVariables.size()) {
            results++;
            return false;
        }
        crosswordVariableStack.push(crosswordVariables.get(n));

        for (String k : crosswordVariableStack.peek().getDomain()) {
            if (((CrosswordConstraint) c).isGood(crosswordVariableStack, k)) {
                crosswordVariableStack.peek().setValue(k);
                if (execute(n + 1)) {
                    return true;
                } else {
                    crosswordVariableStack.peek().setValue(null);
                }
            }
        }
        crosswordVariableStack.pop();
        return false;
    }

    private <T> void printTab2D(T[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.println(Arrays.toString(tab[i]));
        }
    }

}

