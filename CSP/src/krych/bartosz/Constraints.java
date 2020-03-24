package krych.bartosz;

public class Constraints {

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
}
