package krych.bartosz.gui;


import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Settings extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 235333L;

    private final JLabel maxDepth1Label;

    private final JComboBox<Integer> max_depth1_drop_down;

    private final JButton apply;
    private final JButton cancel;

    private final EventHandler handler;

    public static int width = 400;
    public static int height = 320;

    private IntTextField intFiled;

    class IntTextField extends JTextField {
        public IntTextField(int defval, int size) {
            super("" + defval, size);
        }

        protected Document createDefaultModel() {
            return new IntTextDocument();
        }

        public boolean isValid() {
            try {
                Integer.parseInt(getText());
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public int getValue() {
            try {
                return Integer.parseInt(getText());
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        class IntTextDocument extends PlainDocument {
            public void insertString(int offs, String str, AttributeSet a)
                    throws BadLocationException {
                if (str == null)
                    return;
                String oldString = getText(0, getLength());
                String newString = oldString.substring(0, offs) + str
                        + oldString.substring(offs);
                try {
                    Integer.parseInt(newString + "0");
                    super.insertString(offs, str, a);
                } catch (NumberFormatException e) {
                }
            }
        }
    }

    public Settings() {
        super("Settings");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);

        handler = new EventHandler();

        maxDepth1Label = new JLabel("AI1 depth: ");

        add(maxDepth1Label);

        max_depth1_drop_down = new JComboBox<>();
        max_depth1_drop_down.addItem(1);
        max_depth1_drop_down.addItem(2);
        max_depth1_drop_down.addItem(3);
        max_depth1_drop_down.addItem(4);
        max_depth1_drop_down.addItem(5);
        max_depth1_drop_down.addItem(6);

        max_depth1_drop_down.setSelectedIndex(GameGUI.maxDepth - 1);

        add(max_depth1_drop_down);

        intFiled = new IntTextField(12, 3);
//        add(intFiled);

        maxDepth1Label.setBounds(25, 85, 175, 20);

        max_depth1_drop_down.setBounds(195, 85, 160, 20);

        apply = new JButton("Apply");
        cancel = new JButton("Cancel");
        add(apply);
        add(cancel);

        int distance = 10;
        apply.setBounds((width / 2) - 110 - (distance / 2), 230, 100, 30);
        apply.addActionListener(handler);
        cancel.setBounds((width / 2) - 10 + (distance / 2), 230, 100, 30);
        cancel.addActionListener(handler);
    }


    private class EventHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ev) {

            if (ev.getSource() == cancel) {
                dispose();
            } else if (ev.getSource() == apply) {
                try {
                    GameGUI.maxDepth = (int) max_depth1_drop_down.getSelectedItem();

                    JOptionPane.showMessageDialog(null,
                            "START NEW GAME!",
                            "", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (Exception e) {
                    System.err.println("ERROR : " + e.getMessage());
                }
            }
        }
    }
}

