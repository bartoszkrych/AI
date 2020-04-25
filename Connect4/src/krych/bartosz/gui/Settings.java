package krych.bartosz.gui;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Settings extends JFrame {
    private static final long serialVersionUID = 235333L;

    private final JLabel maxDepth1Label;
    SpinnerNumberModel model;

    private final JButton apply;
    private final JButton cancel;

    private final EventHandler handler;

    public static int width = 300;
    public static int height = 150;

    public Settings() {
        super("Settings");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);

        handler = new EventHandler();

        maxDepth1Label = new JLabel("AI depth: ");

        add(maxDepth1Label);

        model = new SpinnerNumberModel(GameGUI.maxDepth, 1, 20, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setBounds(155, 15, 50, 20);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
        add(spinner);

        maxDepth1Label.setBounds(25, 15, 175, 20);

        apply = new JButton("Apply");
        cancel = new JButton("Cancel");
        add(apply);
        add(cancel);

        int distance = 10;
        apply.setBounds((width / 2) - 110 - (distance / 2), 70, 100, 30);
        apply.addActionListener(handler);
        cancel.setBounds((width / 2) - 10 + (distance / 2), 70, 100, 30);
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

