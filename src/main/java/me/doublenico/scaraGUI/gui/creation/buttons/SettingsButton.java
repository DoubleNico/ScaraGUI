package me.doublenico.scaraGUI.gui.creation.buttons;

import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.button.RoundedButton;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;
import me.doublenico.scaraGUI.utils.SettingsActionListenerUtils;

import java.awt.*;

public class SettingsButton extends RoundedButton {

    public SettingsButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name, 10);
        setFont(new Font("Inter", Font.BOLD, 12));
        setBackground(new Color(37, 41, 45));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setPreferredSize(new Dimension(100, 30));
    }

    public void loadEventListener(ScaraGUI owner){
        new SettingsActionListenerUtils(this).loadEventListener(owner);
    }
}
