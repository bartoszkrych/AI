import classes.*;
import interfaces.GameAlgorithm;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        State game = new State();
        int maxDepth = 6;
        GameAlgorithm ai = new MinMax(maxDepth, Consts.P_2, new FitFunThreeInLine());

        game.setLastPlayer(Consts.P_2);

        printBoard(game.getBoard());

        Scanner in = new Scanner(System.in);
        int col;
        while (!game.isEnd()) {
            switch (game.getLastPlayer()) {
                case 1:
                    System.out.println("Round for AI...");
                    Move aiMove = ai.findMove(game);
                    game.nextMove(aiMove.getCol(), Consts.P_2);
                    break;
                case 2:
                    try {
                        do {
                            System.out.print("\nSelect column: ");
                            col = in.nextInt();
                        } while (game.isFullCol(col - 1));
                    } catch (Exception e) {
                        System.err.println("Only [1,7].\n");
                        break;
                    }
                    game.nextMove(col - 1, Consts.P_1);
                    System.out.println();
                    break;
                default:
                    break;
            }
            printBoard(game.getBoard());
        }
        in.close();

        System.out.println();
        if (game.getWinner().equals(Consts.P_1)) {
            System.out.println("Player 1 wins!");
        } else if (game.getWinner().equals(Consts.P_2)) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }


    public static void printBoard(List<List<Integer>> board) {
        System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 | \n");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board.get(i).get(j).equals(Consts.P_1)) {
                    System.out.print("| 1 ");
                } else if (board.get(i).get(j).equals(Consts.P_2)) {
                    System.out.print("| 2 ");
                } else {
                    System.out.print("| - ");
                }
            }
            System.out.print("|\n");
        }
    }
}
