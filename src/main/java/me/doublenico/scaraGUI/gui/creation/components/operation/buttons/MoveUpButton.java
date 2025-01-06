package me.doublenico.scaraGUI.gui.creation.components.operation.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.creation.components.operation.CreationOperation;
import me.doublenico.scaraGUI.gui.creation.components.operation.OperationItem;

import javax.swing.*;
import java.awt.*;

public class MoveUpButton extends Button {
    public MoveUpButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name);
        setFont(new Font("Inter", Font.BOLD, 8));
        setForeground(Color.BLACK);
        setBackground(new Color(9, 125, 201));
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        setPreferredSize(new Dimension(25, 20));
        setToolTipText("Move this item up");
    }

    public void loadEventListener(CreationOperation operationPanel, OperationItem operationItem) {
        addActionListener(e -> operationPanel.moveOperationUp(operationItem));
    }
}
