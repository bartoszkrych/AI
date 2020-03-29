package krych.bartosz.sudoku;

import krych.bartosz.abstra.Problem;

import java.util.ArrayList;
import java.util.List;

public class Sudoku implements Problem {
    SudokuVariable[][] board;
    String level;

    public Sudoku(SudokuVariable[][] board, String level) {
        this.board = board;
        this.level = level;
    }

    public void printBoard() {
        System.out.println("\n ######   LEVEL  -  "+level+"     ######");
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Integer val = board[i][j].getValue();
                System.out.print(val == null ? 0 : val);
                System.out.print(" ");
                if (j == 2 || j == 5) System.out.print("| ");
            }
            if (i == 2 || i == 5) System.out.print("\n---------------------");
            System.out.println();
        }
    }

    public int[][] getInt2D() {
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

    public List<SudokuVariable> getVariables() {
        ArrayList<SudokuVariable> variables = new ArrayList<>();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                variables.add(board[x][y]);
            }
        }
        return variables;
    }
}
