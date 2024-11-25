package me.doublenico.scaraGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OperationItem extends JPanel {

    private Operation operation;
    private JButton deleteButton;
    private JButton addButton;
    private JButton moveUpButton;
    private JButton moveDownButton;

    public OperationItem(String appName, Operation operation, AppCreationGUI parent) {
        this.operation = operation;
        setLayout(new GridBagLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(200, 60));

        JTextArea appLabel = getJTextArea(appName, parent);

        deleteButton = createButton("x", new Color(204, 0, 0), "Delete this item");
        deleteButton.addActionListener(e -> parent.deleteOperation(this));
        addButton = createButton("+", new Color(0, 204, 0), "Add a new item");
        addButton.addActionListener(e -> parent.addOperation("Operation " + (parent.getOperations().size() + 1)));
        moveUpButton = createButton("^", new Color(9, 125, 201), "Move this item up");
        moveUpButton.addActionListener(e -> parent.moveOperationUp(this));
        moveDownButton = createButton("v", new Color(9, 125, 201), "Move this item down");
        moveDownButton.addActionListener(e -> parent.moveOperationDown(this));

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

    private JTextArea getJTextArea(String appName, AppCreationGUI parent) {
        JTextArea appLabel = new JTextArea(appName);
        appLabel.setFont(new Font("Inter", Font.BOLD, 16));
        appLabel.setForeground(Color.WHITE);
        appLabel.setBackground(new Color(38, 38, 38));
        appLabel.setLineWrap(true);
        appLabel.setWrapStyleWord(true);
        appLabel.setEditable(false);

        appLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parent.loadOperation(OperationItem.this);
            }
        });
        return appLabel;
    }

    public void setMoveUpEnabled(boolean enabled) {
        moveUpButton.setEnabled(enabled);
    }

    public void setMoveDownEnabled(boolean enabled) {
        moveDownButton.setEnabled(enabled);
    }

    public void setDeleteEnabled(boolean enabled) {
        deleteButton.setEnabled(enabled);
    }

    private JButton createButton(String text, Color bgColor, String toolTip) {
        JButton button = new JButton(text);
        button.setFont(new Font("Inter", Font.BOLD, 8));
        button.setForeground(Color.BLACK);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        button.setPreferredSize(new Dimension(25, 20));
        button.setToolTipText(toolTip);
        return button;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
