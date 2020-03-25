package krych.bartosz.sudoku;

import krych.bartosz.abstra.Constraint;

public class SudokuConstraint implements Constraint {

    public boolean existInRow(int[][] sudoku, int i, int val) {
        for (int j = 0; j < sudoku[i].length; j++) {
            if (sudoku[i][j] == val) {
                return true;
            }
        }
        return false;
    }

    public boolean existInCol(int[][] sudoku, int j, int val) {
        for (int[] ints : sudoku) {
            if (ints[j] == val) {
                return true;
            }
        }
        return false;
    }

    public boolean existInBlock(int[][] sudoku, int i, int j, int val) {
        int sudokuBlock = (int) Math.sqrt(sudoku.length);
        int iBlock = sudokuBlock * (i / sudokuBlock);
        int jBlock = sudokuBlock * (j / sudokuBlock);

        for (i = iBlock; i < iBlock + sudokuBlock; i++) {
            for (j = jBlock; j < jBlock + sudokuBlock; j++) {
                if (sudoku[i][j] == val) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isGood(int[][] sudoku, int i, int j, int val) {
        return !existInRow(sudoku, i, val) && !existInCol(sudoku, j, val) && !existInBlock(sudoku, i, j, val);
    }
}
