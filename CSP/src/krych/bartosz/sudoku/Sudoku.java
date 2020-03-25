package krych.bartosz.sudoku;

public class Sudoku {

    SudokuVariable[][] board;
    String level;

    public Sudoku(SudokuVariable[][] board, String level) {
        this.board = board;
        this.level = level;
    }

    public void printBoard()
    {

        System.out.println("\n ######   LEVEL  -  "+level+"     ######");
        for(int i =0; i < 9; i++)
        {
            for ( int j = 0; j < 9; j++)
            {
                System.out.print(board[i][j].getValue()+" ");
            }
            System.out.println();
        }
    }

    public int[][] getInt2D()
    {
        int[][] result = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                result[i][j] = board[i][j].getValue();
            }
        }
        return result;
    }

    public SudokuVariable getVariable(int i, int j) {
        return board[i][j];
    }
}
