package me.doublenico.scaraGUI.gui.creation.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;
import me.doublenico.scaraGUI.utils.SettingsActionListenerUtils;

import javax.swing.*;
import java.awt.*;

public class SettingsButton extends Button {

    public SettingsButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name);
        setFont(new Font("Inter", Font.BOLD, 12));
        setBackground(new Color(37, 41, 45));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setPreferredSize(new Dimension(80, 30));
        setBorder(BorderFactory.createEmptyBorder());
    }

    public void loadEventListener(ScaraGUI owner){
        new SettingsActionListenerUtils(this).loadEventListener(owner);
    }
}
