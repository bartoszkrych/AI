package krych.bartosz.gui;

import krych.bartosz.classes.Consts;
import krych.bartosz.classes.State;
import krych.bartosz.classes.algorithms.MinMax;
import krych.bartosz.classes.functions.ThreeInLineEstFun;
import krych.bartosz.interfaces.GameAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameGUI {
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 520;
    static int maxDepth = 4;

    static JFrame mainWindow;
    static JPanel panelMain;
    static JPanel panelNumbers;
    static JLayeredPane boardPane;
    static JLabel pieceLabel;

    static JMenuBar menuBar;
    static JMenu fileMenu;
    static JMenuItem newGameItem;
    static JMenuItem settingsItem;
    static JMenuItem exitItem;
    static JButton[] buttons;

    private static State state;
    private static GameAlgorithm ai;
    private static boolean firstGame;


    public GameGUI() {
        buttons = new JButton[Consts.COLS];
        for (int i = 0; i < Consts.COLS; i++) {
            buttons[i] = new JButton(i + 1 + "");
        }
        pieceLabel = null;
        firstGame = true;
    }

    public static void gameOver() {
        String message;
        setEnableButtons(false);
        if (state.getWinner().equals(Consts.P_1)) {
            message = "YOU WIN! Start a new game?";
        } else if (state.getWinner().equals(Consts.P_2)) {
            message = "AI wins :( Start a new game?";
        } else {
            message = "Draw. Start a new game?";
        }
        int choice = JOptionPane.showConfirmDialog(null,
                message,
                "THE END", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            createNewGame();
        }
    }

    public static void createNewGame() {
        //FOR GAME
        state = new State();
        state.setLastPlayer(Consts.P_2);
        ai = new MinMax(maxDepth, Consts.P_2, new ThreeInLineEstFun());
        setEnableButtons(true);

        // WINDOW
        if (mainWindow != null) mainWindow.dispose();
        mainWindow = new JFrame("Connect-4");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        mainWindow.setLocation(
                (int) (dimension.getWidth() - mainWindow.getWidth() - DEFAULT_WIDTH) / 2
                , (int) (dimension.getHeight() - mainWindow.getHeight() - DEFAULT_HEIGHT) / 2);
        Component compMainWindowContents = createComponentsOnGUI();
        mainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);

        mainWindow.setFocusable(true);
        mainWindow.pack();
        setBarOnGUI();
    }

    public static boolean makeMoveOnGUI() {
        int row = state.getLastMove().getRow();
        int col = state.getLastMove().getCol();
        int currentPlayer = state.getLastPlayer();

        Integer player = state.getBoard().get(0).get(col);
        if (!player.equals(Consts.EMPTY)) buttons[col].setEnabled(false);

        if (currentPlayer == Consts.P_1) {
            putPieceOnGUI("RED", row, col);
        } else if (currentPlayer == Consts.P_2) {
            putPieceOnGUI("BLACK", row, col);
        }
        if (state.isEnd()) {
            gameOver();
        }
        return false;
    }

    public static void putPieceOnGUI(String colorString, int row, int col) {
        int xOffset = 75 * col;
        int yOffset = 75 * row;
        ImageIcon checkerIcon = new ImageIcon("res/images/" + colorString + ".png");
        pieceLabel = new JLabel(checkerIcon);
        pieceLabel.setBounds(27 + xOffset, 27 + yOffset, checkerIcon.getIconWidth(), checkerIcon.getIconHeight());
        boardPane.add(pieceLabel, 0, 0);
        mainWindow.paint(mainWindow.getGraphics());
    }

    public static Component createComponentsOnGUI() {
        panelNumbers = new JPanel();
        panelNumbers.setLayout(new GridLayout(1, Consts.COLS, 6, 4));

        if (firstGame) {
            for (int i = 0; i < buttons.length; i++) {
                JButton button = buttons[i];
                int column = i;
                button.addActionListener(e -> {
                    state.nextMove(column, Consts.P_1);
                    boolean isGameOver = makeMoveOnGUI();
                    if (!isGameOver) {
                        state.nextMove(ai.findMove(state).getCol(), ai.getPlayer());
                        makeMoveOnGUI();
                    }
                    mainWindow.requestFocusInWindow();
                });
            }
            firstGame = false;
        }
        for (JButton button : buttons) {
            panelNumbers.add(button);
        }
        boardPane = setBoardOnGUI();
        panelMain = new JPanel();
        JLabel label = new JLabel();
        label.setText("Select a column:");
        panelMain.add(label, BorderLayout.NORTH);
        panelMain.add(panelNumbers, BorderLayout.NORTH);
        panelMain.add(boardPane, BorderLayout.CENTER);

        mainWindow.setResizable(false);
        return panelMain;
    }

    private static void setBarOnGUI() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        newGameItem = new JMenuItem("New Game");
        settingsItem = new JMenuItem("AI depth");
        exitItem = new JMenuItem("Exit");

        newGameItem.addActionListener(e -> createNewGame());
        settingsItem.addActionListener(e -> {
            Settings settings = new Settings();
            settings.setVisible(true);
        });
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(newGameItem);
        fileMenu.add(settingsItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        mainWindow.setJMenuBar(menuBar);
        mainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        mainWindow.setVisible(true);
    }

    public static JLayeredPane setBoardOnGUI() {
        boardPane = new JLayeredPane();
        boardPane.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        ImageIcon imageState = new ImageIcon("res/images/Board.png");
        JLabel imageStateLabel = new JLabel(imageState);
        imageStateLabel.setBounds(0, 0, imageState.getIconWidth(), imageState.getIconHeight());
        boardPane.add(imageStateLabel, 0, 1);
        return boardPane;
    }

    public static void setEnableButtons(boolean b) {
        for (JButton button : buttons) {
            button.setEnabled(b);
        }
    }
}