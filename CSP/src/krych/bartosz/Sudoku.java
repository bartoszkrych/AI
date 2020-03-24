package krych.bartosz;

import java.util.Arrays;

public class Sudoku {

    int[][] board;
    String level;

    public Sudoku(int[][] board, String level) {
        this.board = board;
        this.level = level;
    }

    public void printBoard()
    {

        System.out.println("\n ######   LEVEL  -  "+level+"     ######");
        for(int i =0; i < 9; i++)
        {
            System.out.println(Arrays.toString(board[i]));
        }
    }
}
