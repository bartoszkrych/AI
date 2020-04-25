package krych.bartosz.gui;

import krych.bartosz.classes.Consts;

public class MainGame {
    public static void main(String[] args) {
        GameGUI connect4 = new GameGUI();
        GameParams.gameMode = Consts.HumanVsAi;
//        GameParams.gameMode = Consts.AiVsAi;
        GameParams.maxDepth1 = 4;
        GameParams.maxDepth2 = 4;

        connect4.createNewGame();
    }
}
