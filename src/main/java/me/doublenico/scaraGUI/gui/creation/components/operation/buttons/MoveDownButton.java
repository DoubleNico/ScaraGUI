package me.doublenico.scaraGUI.gui.creation.components.operation.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.gui.creation.components.operation.CreationOperation;
import me.doublenico.scaraGUI.gui.creation.components.operation.OperationItem;

import javax.swing.*;
import java.awt.*;

public class MoveDownButton extends Button {
    public MoveDownButton(ButtonManager manager, String name, ButtonType type) {
        super(manager, name, type);
        setFont(new Font("Inter", Font.BOLD, 8));
        setForeground(Color.BLACK);
        setBackground(new Color(9, 125, 201));
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        setPreferredSize(new Dimension(25, 20));
        setToolTipText("Move this item down");
    }

    public void loadEventListener(CreationOperation operationPanel, OperationItem operationItem) {
        addActionListener(e -> operationPanel.moveOperationDown(operationItem));
    }
}
