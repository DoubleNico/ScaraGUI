package me.doublenico.scaraGUI.gui.main.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;
import me.doublenico.scaraGUI.utils.SettingsActionListenerUtils;

import java.awt.*;

public class SettingsButton extends Button {
    
    public SettingsButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name);
        setBackground(new Color(37, 41, 45));
        setForeground(Color.WHITE);
        setFont(new Font("Inter", Font.BOLD, 12));
        setFocusPainted(false);
        setOpaque(true);
        setPreferredSize(new Dimension(105, 33));
        setMinimumSize(new Dimension(105, 33));
        setMaximumSize(new Dimension(105, 33));
    }

    public void loadEventListener(ScaraGUI owner){
        new SettingsActionListenerUtils(this).loadEventListener(owner);
    }
}
