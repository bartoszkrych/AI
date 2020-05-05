package krych.bartosz.classes;

import krych.bartosz.interfaces.GameAlgorithm;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameConsole {
    public String[] startAiVsAi(GameAlgorithm player1, GameAlgorithm player2, boolean isFirstRandom) {
        State game = new State();
        int P1_moves = 0;
        int P2_moves = 0;

        game.setLastPlayer(Consts.P_2);

        printBoard(game.getBoard());

        Scanner in = new Scanner(System.in);
        long endTime = 0;
        long startTime = System.currentTimeMillis();
        while (!game.isEnd()) {
            switch (game.getLastPlayer()) {
                case 1:
                    P2_moves++;
                    System.out.println("Round for AI...");
                    Move aiMove1 = player2.findMove(game);
                    game.nextMove(aiMove1.getCol(), Consts.P_2);
                    break;
                case 2:
                    P1_moves++;
                    if (P1_moves == 1 && isFirstRandom) {
                        game.nextMove(new Random().nextInt(7), Consts.P_1);
                        break;
                    }
                    System.out.println("Round for AI...");
                    Move aiMove2 = player1.findMove(game);
                    game.nextMove(aiMove2.getCol(), Consts.P_1);
                    break;
                default:
                    break;
            }
            endTime = System.currentTimeMillis() - startTime;
            printBoard(game.getBoard());
        }
        in.close();

        int winnerMove = -1;
        String winnerName = "-";
        if (game.getWinner().equals(Consts.P_1)) {
            System.out.println("Player 1 wins!  After:   " + P1_moves + " moves");
            winnerMove = P1_moves;
            winnerName = player1.getNameAlg();
        } else if (game.getWinner().equals(Consts.P_2)) {
            System.out.println("Player 2 wins!  After:   " + P2_moves + " moves");
            winnerMove = P2_moves;
            winnerName = player2.getNameAlg();
        } else {
            System.out.println("It's a draw!");
        }
        System.out.println("With time:  " + endTime + "ms");
        return new String[]{winnerName, String.valueOf(winnerMove), String.valueOf(endTime)};
    }

    public void startHumVsAi(GameAlgorithm player2) {
        State game = new State();
        int P1_moves = 0;
        int P2_moves = 0;
        int result;

        game.setLastPlayer(Consts.P_2);

        printBoard(game.getBoard());

        Scanner in = new Scanner(System.in);
        int col;
        long endTime = 0;
        long startTime = System.currentTimeMillis();
        while (!game.isEnd()) {
            switch (game.getLastPlayer()) {
                case 1:
                    P2_moves++;
                    System.out.println("Round for AI...");
                    Move aiMove1 = player2.findMove(game);
                    game.nextMove(aiMove1.getCol(), Consts.P_2);
                    break;
                case 2:
                    P1_moves++;
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
                    break;
                default:
                    break;
            }
            endTime = System.currentTimeMillis() - startTime;
            printBoard(game.getBoard());
        }
        in.close();

        if (game.getWinner().equals(Consts.P_1)) {
            System.out.println("You win!  After:   " + P1_moves + " moves");
            result = P1_moves;
        } else if (game.getWinner().equals(Consts.P_2)) {
            System.out.println("AI wins!  After:   " + P2_moves + " moves");
            result = P2_moves;
        } else {
            System.out.println("It's a draw!");
        }
        System.out.println("With time:  " + endTime + "ms");
    }

    private void printBoard(List<List<Integer>> board) {
        System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 | \n");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (board.get(i).get(j).equals(Consts.P_1)) {
                    System.out.print("| 1 ");
                } else if (board.get(i).get(j).equals(Consts.P_2)) {
                    System.out.print("| 2 ");
                } else {
                    System.out.print("|   ");
                }
            }
            System.out.print("|\n");
        }
    }
}
