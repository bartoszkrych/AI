package krych.bartosz.gui;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Settings extends JFrame {
    private static final long serialVersionUID = 235333L;

    private final JLabel aiDepthLabel;
    private final JLabel aiAlgLabel;
    private final JLabel aiEstLabel;

    SpinnerNumberModel model;

    private final JLabel aiDepthLabel2;
    private final JLabel aiAlgLabel2;
    private final JLabel aiEstLabel2;

    SpinnerNumberModel model2;

    private final JButton apply;
    private final JButton cancel;

    private final JComboBox<String> aiAlg;
    private final JComboBox<String> aiEst;

    private final JComboBox<String> aiAlg2;
    private final JComboBox<String> aiEst2;

    private final EventHandler handler;

    private final JLabel modeLabel;
    private final JComboBox<String> mode;

    public static int width = 600;
    public static int height = 260;

    public Settings() {
        super("Settings");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);

        handler = new EventHandler();

        aiDepthLabel = new JLabel("AI depth: ");
        aiAlgLabel = new JLabel("Algorithm: ");
        aiEstLabel = new JLabel("Estimate: ");


        modeLabel = new JLabel("Game mode: ");


        aiDepthLabel2 = new JLabel("AI depth(AIvsAI) - P1 : ");
        aiAlgLabel2 = new JLabel("Algorithm(AIvsAI) - P1 : ");
        aiEstLabel2 = new JLabel("Estimate(AIvsAI) - P1 : ");

        add(aiDepthLabel);
        add(aiAlgLabel);
        add(aiEstLabel);

        add(modeLabel);

        add(aiDepthLabel2);
        add(aiAlgLabel2);
        add(aiEstLabel2);


        model = new SpinnerNumberModel(GameGUI.maxDepth, 1, 20, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setBounds(155, 15, 50, 20);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
        add(spinner);

        aiDepthLabel.setBounds(25, 15, 175, 20);
        aiAlgLabel.setBounds(25, 45, 175, 20);
        aiEstLabel.setBounds(25, 75, 175, 20);


        aiAlg = new JComboBox<>();
        aiAlg.addItem("MinMax");
        aiAlg.addItem("AlphaBeta");
        aiAlg.setSelectedIndex(GameGUI.algorithm);
        add(aiAlg);
        aiAlg.setBounds(155, 45, 100, 20);


        aiEst = new JComboBox<>();
        aiEst.addItem("Just winner");
        aiEst.addItem("3 in line");
        aiEst.setSelectedIndex(GameGUI.estimate);
        add(aiEst);
        aiEst.setBounds(155, 75, 100, 20);


        model2 = new SpinnerNumberModel(GameGUI.maxDepth2, 1, 20, 1);
        JSpinner spinner2 = new JSpinner(model2);
        spinner2.setBounds(455, 15, 50, 20);
        ((JSpinner.DefaultEditor) spinner2.getEditor()).getTextField().setEditable(false);
        add(spinner2);

        aiDepthLabel2.setBounds(305, 15, 175, 20);
        aiAlgLabel2.setBounds(305, 45, 175, 20);
        aiEstLabel2.setBounds(305, 75, 175, 20);


        aiAlg2 = new JComboBox<>();
        aiAlg2.addItem("MinMax");
        aiAlg2.addItem("AlphaBeta");
        aiAlg2.setSelectedIndex(GameGUI.algorithm2);
        add(aiAlg2);
        aiAlg2.setBounds(455, 45, 100, 20);


        aiEst2 = new JComboBox<>();
        aiEst2.addItem("Just winner");
        aiEst2.addItem("3 in line");
        aiEst2.setSelectedIndex(GameGUI.estimate2);
        add(aiEst2);
        aiEst2.setBounds(455, 75, 100, 20);


        int distance = 10;

        mode = new JComboBox<>();
        mode.addItem("Human vs Ai");
        mode.addItem("Ai vs Ai");
        mode.setSelectedIndex(GameGUI.mode);
        add(mode);
        modeLabel.setBounds((width / 2) - 110 - (distance / 2), 105, 100, 20);
        mode.setBounds((width / 2) - 10 + (distance / 2), 105, 100, 20);


        apply = new JButton("Apply");
        cancel = new JButton("Cancel");
        add(apply);
        add(cancel);

        apply.setBounds((width / 2) - 110 - (distance / 2), 160, 100, 30);
        apply.addActionListener(handler);
        cancel.setBounds((width / 2) - 10 + (distance / 2), 160, 100, 30);
        cancel.addActionListener(handler);
    }


    private class EventHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {

            if (ev.getSource() == cancel) {
                dispose();
            } else if (ev.getSource() == apply) {
                try {
                    GameGUI.maxDepth = (int) model.getValue();
                    GameGUI.algorithm = aiAlg.getSelectedIndex();
                    GameGUI.estimate = aiEst.getSelectedIndex();
                    GameGUI.maxDepth2 = (int) model2.getValue();
                    GameGUI.algorithm2 = aiAlg2.getSelectedIndex();
                    GameGUI.estimate2 = aiEst2.getSelectedIndex();
                    GameGUI.mode = mode.getSelectedIndex();

                    JOptionPane.showMessageDialog(null,
                            "Start new game to apply changes.",
                            "", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (Exception e) {
                    System.err.println("ERROR : " + e.getMessage());
                }
            }
        }
    }
}

