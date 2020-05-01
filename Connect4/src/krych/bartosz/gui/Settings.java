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

    private final JButton apply;
    private final JButton cancel;

    private final JComboBox<String> aiAlg;
    private final JComboBox<String> aiEst;

    private final EventHandler handler;

    public static int width = 300;
    public static int height = 210;

    public Settings() {
        super("Settings");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);

        handler = new EventHandler();

        int selectedAlg = GameGUI.algorithm;
        int selectedEst = GameGUI.estimate;
        aiDepthLabel = new JLabel("AI depth: ");
        aiAlgLabel = new JLabel("Algorithm: ");
        aiEstLabel = new JLabel("Estimate: ");

        add(aiDepthLabel);
        add(aiAlgLabel);
        add(aiEstLabel);

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
        aiAlg.setSelectedIndex(selectedAlg);
        add(aiAlg);
        aiAlg.setBounds(155, 45, 100, 20);


        aiEst = new JComboBox<>();
        aiEst.addItem("Just winner");
        aiEst.addItem("3 in line");
        aiEst.setSelectedIndex(selectedEst);
        add(aiEst);
        aiEst.setBounds(155, 75, 100, 20);


        apply = new JButton("Apply");
        cancel = new JButton("Cancel");
        add(apply);
        add(cancel);

        int distance = 10;
        apply.setBounds((width / 2) - 110 - (distance / 2), 130, 100, 30);
        apply.addActionListener(handler);
        cancel.setBounds((width / 2) - 10 + (distance / 2), 130, 100, 30);
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

