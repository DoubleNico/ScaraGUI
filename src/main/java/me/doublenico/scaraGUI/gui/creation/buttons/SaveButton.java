package me.doublenico.scaraGUI.gui.creation.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.creation.components.operation.CreationOperation;

import javax.swing.*;
import java.awt.*;

public class SaveButton extends Button {
    public SaveButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name);
        setFont(new Font("Inter", Font.BOLD, 12));
        setBackground(new Color(0, 122, 204));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setPreferredSize(new Dimension(80, 30));
        setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
    }

    public void loadEventListener(CreationOperation operationPanel){
        addActionListener(e -> operationPanel.saveOperation());
    }
}
