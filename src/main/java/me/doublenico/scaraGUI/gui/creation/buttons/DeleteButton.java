package me.doublenico.scaraGUI.gui.creation.buttons;

import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.button.RoundedButton;
import me.doublenico.scaraGUI.frame.ApplicationFrame;

import java.awt.*;

public class DeleteButton extends RoundedButton {
    public DeleteButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name, 10);
        setFont(new Font("Inter", Font.BOLD, 12));
        setBackground(new Color(204, 0, 0));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setPreferredSize(new Dimension(100, 30));
    }

    public void loadEventListener(){

    }

}
