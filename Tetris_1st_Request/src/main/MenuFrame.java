package main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


public class MenuFrame extends JFrame {

    private JButton[] buttons;
    private int currentButtonIndex = 0;

    public MenuFrame() {
        setTitle("Tetris Game Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add title
        JLabel titleLabel = new JLabel("TETRIS");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(20));
        add(titleLabel);

        // Menu buttons
        String[] buttonLabels = {"Start Game", "Settings", "Scoreboard", "Exit"};
        buttons = new JButton[buttonLabels.length];

        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            buttons[i].setMaximumSize(new Dimension(150, 50));
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBackground(Color.DARK_GRAY);
            buttons[i].setBorder(BorderFactory.createLineBorder(Color.WHITE));
            buttons[i].setFocusPainted(false);
            buttons[i].setFocusable(true);

            buttons[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    JButton button = (JButton) evt.getSource();
                    button.setBackground(Color.LIGHT_GRAY);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    JButton button = (JButton) evt.getSource();
                    button.setBackground(Color.DARK_GRAY);
                }
            });

            buttons[i].addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    JButton button = (JButton) e.getSource();
                    button.setBorder(BorderFactory.createLineBorder(Color.YELLOW)); // Highlight
                }

                @Override
                public void focusLost(FocusEvent e) {
                    JButton button = (JButton) e.getSource();
                    button.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Back to normal
                }
            });
            buttons[i].addActionListener(this::buttonAction);

            add(Box.createVerticalStrut(10));
            add(buttons[i]);


        }

        // Set the first button to be selected
        buttons[0].requestFocusInWindow();

        setupKeyBindings();

        setVisible(true);
    }

    private void buttonAction(ActionEvent e) {
        // Button action logic
        JButton source = (JButton) e.getSource();
        if ("Start Game".equals(source.getText())) {
            startGame();
        } else if ("Settings".equals(source.getText())) {
            showSettings();
        } else if ("Scoreboard".equals(source.getText())) {
            showScoreboard();
        } else if ("Exit".equals(source.getText())) {
            System.exit(0);
        }
    }

    private void setupKeyBindings() {
        Action upAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentButtonIndex = (currentButtonIndex > 0) ? currentButtonIndex - 1 : buttons.length - 1;
                buttons[currentButtonIndex].requestFocus();
            }
        };
        Action downAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentButtonIndex = (currentButtonIndex < buttons.length - 1) ? currentButtonIndex + 1 : 0;
                buttons[currentButtonIndex].requestFocus();
            }
        };
        Action enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttons[currentButtonIndex].doClick();
            }
        };

        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "upAction");
        actionMap.put("upAction", upAction);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "downAction");
        actionMap.put("downAction", downAction);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterAction");
        actionMap.put("enterAction", enterAction);
    }

    private void startGame() {
        // Start the game

        getContentPane().removeAll(); // Remove all components (the menu)
        Board gameBoard = new Board(); // Create the game board
        getContentPane().add(gameBoard); // Add the game board to this frame
        getContentPane().revalidate(); // Revalidate the content pane
        getContentPane().repaint(); // Repaint the frame
        gameBoard.requestFocusInWindow();
        pack(); // Adjust the window size based on its content
        setLocationRelativeTo(null); // Center the window again, if desired

    }



    private void showSettings() {
        SettingsPanel settingsPanel = new SettingsPanel(this);
        switchToPanel(settingsPanel);
    }

    private void showScoreboard() {
        // Show scoreboard
    }
    public void switchToPanel(JPanel panel) {
        getContentPane().removeAll(); // Remove the current panel
        getContentPane().add(panel); // Add the new panel
        getContentPane().revalidate();
        getContentPane().repaint();
        pack(); // Optional, if you want to resize the frame to fit the content
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuFrame());
    }
}
