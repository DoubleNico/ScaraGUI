package me.doublenico.scaraGUI;

import javax.swing.*;
import java.awt.*;

public class OperationItem extends JPanel {

    private JButton deleteButton;
    private JButton addButton;
    private JButton moveUpButton;
    private JButton moveDownButton;

    public OperationItem(String appName) {
        setLayout(new GridBagLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(200, 60));

        JTextArea appLabel = new JTextArea(appName);
        appLabel.setFont(new Font("Inter", Font.BOLD, 16));
        appLabel.setForeground(Color.WHITE);
        appLabel.setBackground(new Color(38, 38, 38));
        appLabel.setLineWrap(true);
        appLabel.setWrapStyleWord(true);
        appLabel.setEditable(false);

        deleteButton = createButton("x", new Color(204, 0, 0), "Delete this item");
        addButton = createButton("+", new Color(0, 204, 0), "Add a new item");
        moveUpButton = createButton("^", new Color(9, 125, 201), "Move this item up");
        moveDownButton = createButton("v", new Color(9, 125, 201), "Move this item down");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        add(appLabel, gbc);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        buttonPanel.add(moveUpButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(moveDownButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonPanel.add(deleteButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        buttonPanel.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        add(buttonPanel, gbc);
    }

    private JButton createButton(String text, Color bgColor, String toolTip) {
        JButton button = new JButton(text);
        button.setFont(new Font("Inter", Font.BOLD, 8));
        button.setForeground(Color.BLACK);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(2, 0,2, 0));
        button.setPreferredSize(new Dimension(25, 20));
        button.setToolTipText(toolTip);
        return button;
    }
}