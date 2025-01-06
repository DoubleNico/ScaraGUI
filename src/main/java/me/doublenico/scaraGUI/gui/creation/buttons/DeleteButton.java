package me.doublenico.scaraGUI.gui.creation.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;

import javax.swing.*;
import java.awt.*;

public class DeleteButton extends Button {
    public DeleteButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name);
        setFont(new Font("Inter", Font.BOLD, 12));
        setBackground(new Color(204, 0, 0));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setPreferredSize(new Dimension(80, 30));
        setBorder(BorderFactory.createEmptyBorder());
    }

    public void loadEventListener(){

    }
}
