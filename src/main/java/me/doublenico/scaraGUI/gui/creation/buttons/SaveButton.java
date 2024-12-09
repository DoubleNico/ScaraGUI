package me.doublenico.scaraGUI.gui.creation.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.gui.creation.components.operation.CreationOperation;

import javax.swing.*;
import java.awt.*;

public class SaveButton extends Button {
    public SaveButton(ButtonManager manager, String name, ButtonType type) {
        super(manager, name, type);
        setFont(new Font("Inter", Font.BOLD, 12));
        setBackground(new Color(0, 122, 204));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setPreferredSize(new Dimension(80, 30));
        setBorder(BorderFactory.createEmptyBorder());
    }

    public void loadEventListener(CreationOperation operationPanel){
        addActionListener(e -> operationPanel.saveOperation());
    }
}
