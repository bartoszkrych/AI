package krych.bartosz;

import java.util.Arrays;
import java.util.Stack;

public class Backtracking<V, X> {

    Stack<int[][]> boardsStack = new Stack<>();
    Sudoku sudoku;
    int[][] board;
    int iteration = 0;
    private Stack<Variable> variableStack = new Stack<>();
    int results = 0;
    private Constraints c = new Constraints();

    public Backtracking(Sudoku sudoku) {
        this.sudoku = sudoku;
        board = sudoku.getInt2D();
    }

    public void start() {
        //start
//        do {
//            printTab2D();
        execute(board, 0);
//        } while (variableStack.empty());

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


    private void printTab2D(int[][] tab) {
        for (int i = 0; i < 9; i++) {
            System.out.println(Arrays.toString(tab[i]));
        }
    }
}

