package krych.bartosz.gui;

import krych.bartosz.classes.Consts;
import krych.bartosz.classes.Move;
import krych.bartosz.classes.State;
import krych.bartosz.classes.algorithms.MinMax;
import krych.bartosz.classes.functions.ThreeInLineEstFun;
import krych.bartosz.interfaces.GameAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameGUI {
    static final int DEFAULT_WIDTH = 570;
    static final int DEFAULT_HEIGHT = 525;

    static JFrame frameMainWindow;
    static JPanel panelMain;
    static JPanel panelStateNumbers;
    static JLayeredPane layeredGameState;
    public static JLabel pieceLabel = null;

    // Menu bars and items
    static JMenuBar menuBar;
    static JMenu fileMenu;
    static JMenuItem newGameItem;
    static JMenuItem settingsItem;
    static JMenuItem exitItem;
    static JButton[] buttons;

    static State state;
    static GameAlgorithm ai;
    static boolean firstGame = true;

    public GameGUI() {
        buttons = new JButton[Consts.COLS];
        for (int i = 0; i < Consts.COLS; i++) {
            buttons[i] = new JButton(i + 1 + "");
        }
    }

    private static void checkToDisable(int col) {
        Integer player = state.getBoard().get(0).get(col);
        if (!player.equals(Consts.EMPTY)) buttons[col].setEnabled(false);
    }

    public static Component createContentComponents() {
        // Create a panel to set up the State buttons.
        panelStateNumbers = new JPanel();
        panelStateNumbers.setLayout(new GridLayout(1, Consts.COLS, 6, 4));

        if (firstGame) {
            for (int i = 0; i < buttons.length; i++) {
                JButton button = buttons[i];
                int column = i;

                button.addActionListener(e -> {
                    nextMove(column);
                    boolean isGameOver = makeMoveOnGUI();
                    checkToDisable(column);
                    if (!isGameOver)
                        aiMove(ai);
                    frameMainWindow.requestFocusInWindow();
                });
            }
            firstGame = false;
        }
        for (JButton button : buttons) {
            panelStateNumbers.add(button);
        }
        layeredGameState = createLayeredState();
        panelMain = new JPanel();

        panelMain.add(panelStateNumbers, BorderLayout.NORTH);
        panelMain.add(layeredGameState, BorderLayout.CENTER);

        frameMainWindow.setResizable(false);
        return panelMain;
    }

    private static void AddMenus() {

        // Add the menu bar.
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        newGameItem = new JMenuItem("New Game");
        settingsItem = new JMenuItem("Settings");
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
        frameMainWindow.setJMenuBar(menuBar);
        frameMainWindow.setVisible(true);
    }

    public static void centerWindow(Window frame, int width, int height) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() - frame.getWidth() - width) / 2;
        int y = (int) (dimension.getHeight() - frame.getHeight() - height) / 2;
        frame.setLocation(x, y);
    }

    public static JLayeredPane createLayeredState() {
        layeredGameState = new JLayeredPane();
        layeredGameState.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        layeredGameState.setBorder(BorderFactory.createTitledBorder("Connect-4"));
        ImageIcon imageState = new ImageIcon("res/images/Board.png");
        JLabel imageStateLabel = new JLabel(imageState);
        imageStateLabel.setBounds(20, 20, imageState.getIconWidth(), imageState.getIconHeight());
        layeredGameState.add(imageStateLabel, 0, 1);
        return layeredGameState;
    }

    public static void setAllButtonsEnabled(boolean b) {
        for (JButton button : buttons) {
            button.setEnabled(b);
        }
    }

    public static boolean makeMoveOnGUI() {
        int row = state.getLastMove().getRow();
        int col = state.getLastMove().getCol();
        int currentPlayer = state.getLastPlayer();
        if (currentPlayer == Consts.P_1) {
            putPiece("RED", row, col);
        } else if (currentPlayer == Consts.P_2) {
            putPiece("YELLOW", row, col);
        }
        boolean isGameOver = state.isEnd();
        if (isGameOver) {
            gameOver();
        }
        return isGameOver;
    }

    public static void putPiece(String colorString, int row, int col) {
        int xOffset = 75 * col;
        int yOffset = 75 * row;
        ImageIcon checkerIcon = new ImageIcon("res/images/" + colorString + ".png");
        pieceLabel = new JLabel(checkerIcon);
        pieceLabel.setBounds(27 + xOffset, 27 + yOffset, checkerIcon.getIconWidth(), checkerIcon.getIconHeight());
        layeredGameState.add(pieceLabel, 0, 0);
        frameMainWindow.paint(frameMainWindow.getGraphics());
    }

    public static void createNewGame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        state = new State();
        state.setLastPlayer(Consts.P_2);
        setAllButtonsEnabled(true);
        frameMainWindow = new JFrame("Connect-4");
        // make the main window appear on the center
        centerWindow(frameMainWindow, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        Component compMainWindowContents = createContentComponents();
        frameMainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);

        frameMainWindow.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frameMainWindow.setFocusable(true);

        // show window
        frameMainWindow.pack();
        AddMenus();

        ai = new MinMax(GameParams.maxDepth1, Consts.P_2, new ThreeInLineEstFun());
    }

    public static void nextMove(int col) {
        if (state.getLastPlayer().equals(Consts.P_2)) {
            state.nextMove(col, Consts.P_1);
        } else {
            state.nextMove(col, Consts.P_2);
        }
    }

    public static void aiMove(GameAlgorithm ai) {
        Move aiMove = ai.findMove(state);
        state.nextMove(aiMove.getCol(), ai.getPlayer());
        makeMoveOnGUI();
    }

    public static void gameOver() {
        String message;
        if (state.getWinner().equals(Consts.P_1)) {
            message = "Player 1 wins! Start a new game?";
        } else if (state.getWinner().equals(Consts.P_2)) {
            message = "Player 2 wins! Start a new game?";
        } else {
            message = "It's a draw! Start a new game?";
        }
        int choice = JOptionPane.showConfirmDialog(null,
                message,
                "Game Over", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            createNewGame();
        } else {
            setAllButtonsEnabled(false);
        }
    }
}