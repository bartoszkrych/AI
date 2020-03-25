package krych.bartosz;

import krych.bartosz.Crossword.Crossword;
import krych.bartosz.Crossword.CrosswordConstraint;
import krych.bartosz.Crossword.CrosswordVariable;
import krych.bartosz.sudoku.Sudoku;
import krych.bartosz.sudoku.SudokuConstraint;
import krych.bartosz.sudoku.SudokuVariable;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Backtracking {

    Stack<int[][]> boardsStack = new Stack<>();
    Sudoku sudoku;
    int[][] board;
    int iteration = 0;
    private Stack<SudokuVariable> sudokuVariableStack = new Stack<>();
    int results = 0;
    private SudokuConstraint c = new SudokuConstraint();
    private CrosswordConstraint cc = new CrosswordConstraint();
    private Stack<CrosswordVariable> crosswordVariableStack = new Stack<>();
    private List<CrosswordVariable> crosswordVariables;
    private Crossword crossword;

    public Backtracking(Sudoku sudoku) {
        this.sudoku = sudoku;
        board = sudoku.getInt2D();
    }

    public Backtracking(Crossword crossword) {
        this.crossword = crossword;
        this.crosswordVariables = crossword.getVariables();
    }

    public void start() {
        //start
//        do {
//            printTab2D();
//        execute(board, 0);
//        } while (variableStack.empty());

        execute(0);

        System.out.println("results: " + results + ",  stack.si(): " + crosswordVariableStack.size());
        //koniec
//        printTab2D(result);
    }

    private boolean execute(int sudokuT[][], int position) {
        if (position == 9 * 9) {
            results++;
            return false;
        }
        int i = position / 9;
        int j = position % 9;

        if (sudoku.getVariable(i, j).getValue() != 0) {
            return execute(sudokuT, position + 1);
        }
        sudokuVariableStack.push(sudoku.getVariable(i, j));

        for (Integer val : sudokuVariableStack.peek().getDomain()) {
            if (c.isGood(sudokuT, i, j, val)) {
                sudokuT[i][j] = val;
                if (execute(sudokuT, position + 1)) {
                    return true;
                } else {
                    sudokuT[i][j] = 0;
                }
            }
        }
        sudokuVariableStack.pop();
        sudokuT[i][j] = 0;
        return false;
    }

    private boolean execute(int n) {
        if (n == crosswordVariables.size()) {
            results++;
//            crosswordVariableStack.forEach( x -> {
//                System.out.println(x.toString());
//            });
            return false;
        }
        crosswordVariableStack.push(crosswordVariables.get(n));

        for (String k : crosswordVariableStack.peek().getDomain()) {
            if (cc.isGood(crosswordVariableStack, k)) {
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

