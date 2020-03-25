package krych.bartosz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Crossword {
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
        for (int i = 0; i < board.length; i++)
            System.out.println(Arrays.toString(board[i]));

        System.out.print("WORDS:    ");
        for (int i = 0; i < words.size(); i++)
            System.out.print(words.get(i) + "   ");
        System.out.println();

        for (CrosswordVariable cv : variables)
            System.out.println(cv.toString());

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
                } else if (counter > 1) {
                    final int c = counter;
                    variables.add(new CrosswordVariable(words.stream().filter(x -> x.length() == c).collect(Collectors.toList()), iBeg, jBeg, counter, Orientation.HORIZONTAL));
                    counter = 0;
                } else {
                    counter = 0;
                }
            }
            if (counter > 1) {
                final int c = counter;
                variables.add(new CrosswordVariable(words.stream().filter(x -> x.length() == c).collect(Collectors.toList()), iBeg, jBeg, counter, Orientation.HORIZONTAL));
                counter = 0;
            } else {
                counter = 0;
            }
        }

        for (int i = board[0].length - 1; i >= 0; i--) {
            for (int j = 0; j < board.length; j++) {
                if (board[j][i] == '_') {
                    if (counter == 0) {
                        iBeg = j;
                        jBeg = i;
                    }
                    counter++;
                } else if (counter > 1) {
                    final int c = counter;
                    variables.add(new CrosswordVariable(words.stream().filter(x -> x.length() == c).collect(Collectors.toList()), iBeg, jBeg, counter, Orientation.VERTICAL));
                    counter = 0;
                } else {
                    counter = 0;
                }
            }
            if (counter > 1) {
                final int c = counter;
                variables.add(new CrosswordVariable(words.stream().filter(x -> x.length() == c).collect(Collectors.toList()), iBeg, jBeg, counter, Orientation.VERTICAL));
                counter = 0;
            } else {
                counter = 0;
            }
        }
    }
}
