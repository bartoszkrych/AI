import classes.Consts;
import classes.MinMax;
import classes.Move;
import classes.State;
import interfaces.GameAlgorithm;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int col;
        State game = new State();
        int maxDepth = 5;
        GameAlgorithm ai = new MinMax(maxDepth, Consts.P_2);

        game.setLastPlayer(Consts.P_2);

        printBoard(game.getBoard());

        System.out.println();
        Scanner in = new Scanner(System.in);

        while (!game.isEnd()) {
            System.out.println("\n*****************************");
            switch (game.getLastPlayer()) {
                case 2:
                    System.out.print("Player 1 moves.");
                    try {
                        do {
                            System.out.print("\nGive column (1-7): ");
                            col = in.nextInt();
                        } while (game.isFullCol(col - 1));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("\nValid numbers are 1, 2, 3, 4, 5, 6 or 7.\n");
                        break;
                    } catch (InputMismatchException e) {
                        System.err.println("\nInput an integer number.");
                        System.err.println("Valid numbers are 1, 2, 3, 4, 5, 6 or 7.\n");
                        break;
                    }
                    game.nextMove(col - 1, Consts.P_1);
                    System.out.println();
                    break;
                case 1:
                    System.out.println("Player 2 moves.");
//                    Random r = new Random();
//                    int randomNum = r.nextInt(7);
//                    game.nextMove(randomNum, Consts.P_2);

                    Move aiMove = ai.findMove(game);
                    game.nextMove(aiMove.getCol(), Consts.P_2);
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
        System.out.println("End Game.");
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
