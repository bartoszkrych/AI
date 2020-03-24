package krych.bartosz;

import java.util.Arrays;

public class Crossword {
    private String[] words;
    private Character[][] board;

    public Crossword(String[] words, Character[][] board) {
        this.words = words;
        this.board = board;
    }


    public void printBoard() {
        for (int i = 0; i < board.length; i++)
            System.out.println(Arrays.toString(board[i]));

        System.out.print("WORDS:    ");
        for (int i = 0; i < words.length; i++)
            System.out.print(words[i] + "   ");
        System.out.println();
    }
}
