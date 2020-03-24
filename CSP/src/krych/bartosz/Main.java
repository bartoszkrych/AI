package krych.bartosz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<Sudoku> s = getSudokuFromFile();
        s.forEach(Sudoku::printBoard);

    }

    private static List<Sudoku> getSudokuFromFile() {
        File file = new File("Files/Sudoku.csv");
        List<Sudoku> sudokus = new ArrayList<>();
        String[] readString;
        String line;

        try (Scanner scanner = new Scanner(file)) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();

                readString = line.split(";");

                int x = 0;
                int y = 0;
                int[][] board = new int[9][9];

                for (int i = 0; i < readString[2].length(); i++) {
                    board[y][x++] = readString[2].charAt(i) == '.' ? 0 : Character.getNumericValue(readString[2].charAt(i));
                    if (x == 9) {
                        x = 0;
                        y++;
                    }
                }
                sudokus.add(new Sudoku(board, readString[1]));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sudokus;
    }
}
