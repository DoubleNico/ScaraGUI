package me.doublenico.scaraGUI.gui.creation.components.operation;

import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;
import me.doublenico.scaraGUI.gui.creation.components.operation.buttons.AddButton;
import me.doublenico.scaraGUI.gui.creation.components.operation.buttons.DeleteButton;
import me.doublenico.scaraGUI.gui.creation.components.operation.buttons.MoveDownButton;
import me.doublenico.scaraGUI.gui.creation.components.operation.buttons.MoveUpButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OperationItem extends JPanel {

    private Operation operation;
    private final AppCreationGUI parent;
    private final DeleteButton deleteButton;
    private final MoveUpButton moveUpButton;
    private final MoveDownButton moveDownButton;

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

        deleteButton = new DeleteButton(parent.getOwner().getButtonManager(), "x", ButtonType.LOAD_APP, parent);
        deleteButton.loadEventListener(parent.getOperationsPanel(), this);

        AddButton addButton = new AddButton(parent.getOwner().getButtonManager(), "+", ButtonType.LOAD_APP, parent);
        addButton.addActionListener(e -> parent.getOperationsPanel().addOperation("Operation " + (parent.getOperationsPanel().getOperations().size())));

        moveUpButton = new MoveUpButton(parent.getOwner().getButtonManager(), "^", ButtonType.LOAD_APP, parent);
        moveUpButton.loadEventListener(parent.getOperationsPanel(), this);

        moveDownButton = new MoveDownButton(parent.getOwner().getButtonManager(), "v", ButtonType.LOAD_APP, parent);
        moveDownButton.loadEventListener(parent.getOperationsPanel(), this);

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
