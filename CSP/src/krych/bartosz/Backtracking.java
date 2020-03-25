package krych.bartosz;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Backtracking {

    Stack<int[][]> boardsStack = new Stack<>();
    Sudoku sudoku;
    int[][] board;
    int iteration = 0;
    private Stack<Variable> variableStack = new Stack<>();
    int results = 0;
    private Constraints c = new Constraints();
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

        System.out.println("results: " + results + ",  stack.si(): " + variableStack.size());
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
        variableStack.push(sudoku.getVariable(i, j));

        for (Integer k : variableStack.peek().getDomain()) {
            if (!c.existInRow(sudokuT, i, k) && !c.existInCol(sudokuT, j, k) && !c.existInBlock(sudokuT, i, j, k)) {
                sudokuT[i][j] = k;
                if (execute(sudokuT, position + 1)) {
                    return true;
                } else {
                    sudokuT[i][j] = 0;
                }
            }
        }
        variableStack.pop();
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
            if (!cc.contains(crosswordVariableStack, k) && !cc.isWrongIntersection(crosswordVariableStack, k)) {
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

