package me.doublenico.scaraGUI.gui.creation.components.operation;

import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OperationItem extends JPanel {

    private Operation operation;
    private final AppCreationGUI parent;
    private final JButton deleteButton;
    private final JButton moveUpButton;
    private final JButton moveDownButton;

    public OperationItem(String appName, Operation operation, AppCreationGUI parent) {
        this.operation = operation;
        this.parent = parent;
        setLayout(new GridBagLayout());
        setOpaque(false);
        setPreferredSize(new Dimension(200, 60));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (parent.getFormPanel().validateForm()) {
                    if (!checkSavedConfirm()) return;
                    parent.getOperationsPanel().saveOperation();
                    if (parent.getOperationsPanel().getSelectedOperation() != null) parent.getOperationsPanel().getSelectedOperation().setBorder(BorderFactory.createEmptyBorder());
                    parent.getOperationsPanel().loadOperation(OperationItem.this);
                }
            }
        });

        JTextArea appLabel = getJTextArea(appName, parent);

        deleteButton = createButton("x", new Color(204, 0, 0), "Delete this item");
        deleteButton.addActionListener(e -> parent.getOperationsPanel().deleteOperation(this));
        JButton addButton = createButton("+", new Color(0, 204, 0), "Add a new item");
        addButton.addActionListener(e -> parent.getOperationsPanel().addOperation("Operation " + (parent.getOperationsPanel().getOperations().size())));
        moveUpButton = createButton("^", new Color(9, 125, 201), "Move this item up");
        moveUpButton.addActionListener(e -> parent.getOperationsPanel().moveOperationUp(this));
        moveDownButton = createButton("v", new Color(9, 125, 201), "Move this item down");
        moveDownButton.addActionListener(e -> parent.getOperationsPanel().moveOperationDown(this));

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
                if (parent.getFormPanel().validateForm()) {
                    if (!checkSavedConfirm()) return;
                    parent.getOperationsPanel().saveOperation();
                    if (parent.getOperationsPanel().getSelectedOperation() != null) parent.getOperationsPanel().getSelectedOperation().setBorder(BorderFactory.createEmptyBorder());
                    parent.getOperationsPanel().loadOperation(OperationItem.this);
                }
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

    private boolean checkSavedConfirm(){
        if (!parent.getOperationsPanel().hasSaved()) {
            int option = JOptionPane.showConfirmDialog(parent, "You have not saved your progress, accept if you wanna save it. Are you sure you want to switch operations?", "Save Changes", JOptionPane.YES_NO_OPTION);
            return option == JOptionPane.YES_OPTION;
        }
        return true;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
