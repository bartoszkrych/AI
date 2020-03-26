package krych.bartosz.crossword;

import krych.bartosz.abstra.Problem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Crossword implements Problem {
    private List<String> words;
    private Character[][] board;
    private List<CrosswordVariable> variables;

    public Crossword(List<String> words, Character[][] board) {
        this.words = words;
        this.board = board;
        this.variables = new ArrayList<>();
        findVariables();
    }

    public List<CrosswordVariable> getVariables() {
        return variables;
    }

    public void printBoard() {
//        for (Character[] characters : board) System.out.println(Arrays.toString(characters));
        for (Character[] chars : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (chars[j] == 0) System.out.print("# ");
                else System.out.print(chars[j] + " ");
            }
            System.out.println();
        }

        System.out.print("WORDS:    ");
        for (String word : words) System.out.print(word + "   ");
        System.out.println("\n");
    }

    private void findVariables() {
        int counter = 0;
        int iBeg = 0;
        int jBeg = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '_') {
                    if (counter == 0) {
                        iBeg = i;
                        jBeg = j;
                    }
                    counter++;
                } else counter = getCounter(counter, iBeg, jBeg, Orientation.HORIZONTAL);
            }
            counter = getCounter(counter, iBeg, jBeg, Orientation.HORIZONTAL);
        }

        for (int i = board[0].length - 1; i >= 0; i--) {
            for (int j = 0; j < board.length; j++) {
                if (board[j][i] == '_') {
                    if (counter == 0) {
                        iBeg = j;
                        jBeg = i;
                    }
                    counter++;
                } else {
                    counter = getCounter(counter, iBeg, jBeg, Orientation.VERTICAL);
                }
            }
            counter = getCounter(counter, iBeg, jBeg, Orientation.VERTICAL);
        }
    }

    private int getCounter(int counter, int iBeg, int jBeg, Orientation orientation) {
        if (counter > 1) {
            final int c = counter;
            variables.add(new CrosswordVariable(words.stream().filter(x -> x.length() == c).collect(Collectors.toList()), iBeg, jBeg, counter, orientation));
        }
        counter = 0;
        return counter;
    }

    public int getWidth() {
        return board[0].length;
    }

    public int getHeight() {
        return board.length;
    }
}
