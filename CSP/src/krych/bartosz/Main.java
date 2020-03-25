package krych.bartosz;

import krych.bartosz.Crossword.Crossword;
import krych.bartosz.sudoku.Sudoku;
import krych.bartosz.sudoku.SudokuVariable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        List<Sudoku> s = getSudokuFromFile();
//        s.forEach(Sudoku::printBoard);

//        Crossword crossword = getCrosswordFromFile(1);
//        crossword.printBoard();

//        Backtracking backtracking = new Backtracking(s.get(0));

        for (int i = 0; i < 4; i++) {
            Backtracking backtracking = new Backtracking(getCrosswordFromFile(i));
            backtracking.start();
        }


//        for (Sudoku sudoku : s) {
//            Backtracking backtracking = new Backtracking(sudoku);
//            backtracking.start();
//        }
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
                SudokuVariable[][] board = new SudokuVariable[9][9];

                for (int i = 0; i < readString[2].length(); i++) {
                    board[y][x++] = readString[2].charAt(i) == '.' ? new SudokuVariable() :
                            new SudokuVariable(Character.getNumericValue(readString[2].charAt(i)));
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

    private static Crossword getCrosswordFromFile(int fileNumber) {
        File boardFile = new File("Files/Jolka/puzzle" + fileNumber);
        String line;
        List<List<Character>> board = new ArrayList<>();

        try (Scanner scanner = new Scanner(boardFile)) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                List<Character> row = new ArrayList<>();
                for (int i = 0; i < line.length(); i++) {
                    row.add(line.charAt(i));
                }
                board.add(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        File wordsFile = new File("Files/Jolka/words" + fileNumber);
        List<String> words = new ArrayList<>();

        try (Scanner scanner = new Scanner(wordsFile)) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                words.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Character[][] arrBord = board.stream().map(u -> u.toArray(new Character[0])).toArray(Character[][]::new);
        return new Crossword(words, arrBord);
    }
}
