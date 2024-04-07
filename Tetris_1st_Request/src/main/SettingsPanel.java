package main;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private JFrame parentFrame;
    private static final Dimension SMALL_SIZE = new Dimension(300, 400);
    private static final Dimension MEDIUM_SIZE = new Dimension(400, 500);
    private static final Dimension LARGE_SIZE = new Dimension(500, 600);
    public SettingsPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        initializeUI();
    }

    private void initializeUI() {
        addTitleLabel();
        addWindowSizeDropdown();
        add(createStyledButton("Set Move Key"));
        add(createStyledButton("Reset Scoreboard"));
        add(createStyledCheckBox("Colorblind Mode"));
        add(createStyledButton("Reset to Defaults"));
        add(createStyledButton("Back to Menu"));
    }

    private void addTitleLabel() {
        JLabel settingsTitle = new JLabel("SETTINGS");
        settingsTitle.setForeground(Color.WHITE);
        settingsTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        settingsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(settingsTitle);
        add(Box.createVerticalStrut(20));
    }
    private void addWindowSizeDropdown() {
        JLabel label = new JLabel("Change Window Size:");
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        JComboBox<String> sizeOptions = new JComboBox<>(new String[]{"Small", "Medium", "Large"});
        sizeOptions.setAlignmentX(Component.CENTER_ALIGNMENT);
        sizeOptions.setMaximumSize(new Dimension(150, 30));
        sizeOptions.addActionListener(e -> adjustWindowSize((String) sizeOptions.getSelectedItem()));
        // Style the dropdown if needed here

        JPanel windowSizePanel = new JPanel();
        windowSizePanel.setLayout(new BoxLayout(windowSizePanel, BoxLayout.Y_AXIS));
        windowSizePanel.add(label);
        windowSizePanel.add(sizeOptions);
        windowSizePanel.setBackground(Color.BLACK); // Match the background
        windowSizePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(windowSizePanel);
    }
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        styleComponent(button, 150, 50);
        button.addActionListener(e -> performAction(text));
        return button;
    }

    private JComboBox<String> createStyledComboBox(String[] options, String label) {
        JLabel comboLabel = new JLabel(label);
        styleComponent(comboLabel, 150, 50);

        JComboBox<String> comboBox = new JComboBox<>(options);
        styleComponent(comboBox, 150, 50);
        comboBox.addActionListener(e -> adjustWindowSize((String) comboBox.getSelectedItem()));

        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.Y_AXIS));
        comboPanel.add(comboLabel);
        comboPanel.add(comboBox);
        comboPanel.setBackground(Color.BLACK);
        comboPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return comboBox;
    }

    private JCheckBox createStyledCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        styleComponent(checkBox, 150, 50);
        checkBox.addItemListener(e -> toggleColorblindMode(checkBox.isSelected()));
        return checkBox;
    }

    private void styleComponent(JComponent component, int width, int height) {
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        component.setMaximumSize(new Dimension(width, height));
        component.setForeground(Color.WHITE);
        component.setBackground(Color.DARK_GRAY);
        component.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        if (component instanceof AbstractButton) {
            ((AbstractButton) component).setFocusPainted(false);
        }
    }

    private void performAction(String text) {
        // The action command can be set to a unique string if needed.
        switch (text) {
            case "Back to Menu":
                showMainMenu();
                break;
            case "Set Move Key":
                // TODO: Implement key setting functionality
                break;
            case "Reset Scoreboard":
                // TODO: Implement scoreboard reset functionality
                break;
            case "Reset to Defaults":
                // TODO: Implement reset functionality
                break;
            // Add additional cases for other buttons as needed.
        }
    }

    // Placeholder methods for each action
    private void showMainMenu() {
        // TODO: Implement logic to switch back to the main menu
    }

    private void adjustWindowSize(String size) {
        // TODO: Implement logic for adjusting window size
        // Change the window size based on the selected option
        if (size.equals("Small")) {
            parentFrame.setSize(SMALL_SIZE);
        } else if (size.equals("Medium")) {
            parentFrame.setSize(MEDIUM_SIZE);
        } else if (size.equals("Large")) {
            parentFrame.setSize(LARGE_SIZE);
        }
        parentFrame.setLocationRelativeTo(null); // Center the window

    }

    // ... Other placeholder methods ...
    private void waitForKeyPress() {
        // Placeholder for key binding logic
        // Implement the logic to capture a key press and assign it to an action
    }

    private void resetScoreboard() {
        // Placeholder for scoreboard reset logic
        // Implement the logic to reset the scoreboard
    }

    private void toggleColorblindMode(boolean enabled) {
        // Placeholder for colorblind mode toggle logic
        // Implement the logic to switch color schemes
    }

    private void resetToDefaults() {
        // Placeholder for resetting to default settings
        // Implement the logic to reset all settings to their defaults
    }
}
