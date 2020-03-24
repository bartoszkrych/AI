package krych.bartosz;

import java.util.Arrays;

public class Backtracking {

    private Constraints c = new Constraints();

    public void start(Sudoku sudoku) {
        Sudoku s = sudoku;
        int[][] result = s.getInt2D();
        execute(result, 0);
        printTab2D(result);
    }

    private boolean execute(int sudoku[][], int position) {
        if (position == 9 * 9) {
            return true;
        }

        int i = position / 9;
        int j = position % 9;

        if (sudoku[i][j] != 0) {
            return execute(sudoku, position + 1);
        }

        for (int k = 1; k <= 9; k++) {
            if (!c.existInRow(sudoku, i, k) && !c.existInCol(sudoku, j, k) && !c.existInBlock(sudoku, i, j, k)) {
                sudoku[i][j] = k;
                if (execute(sudoku, position + 1)) {
                    return true;
                }
            }
        }
        sudoku[i][j] = 0;
        return false;
    }

    private void printTab2D(int[][] tab) {
        for (int i = 0; i < 9; i++) {
            System.out.println(Arrays.toString(tab[i]));
        }
    }
}
