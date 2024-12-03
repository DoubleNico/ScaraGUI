package me.doublenico.scaraGUI.gui.main.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;
import me.doublenico.scaraGUI.gui.settings.SettingsGui;

import java.awt.*;

public class SettingsButton extends Button {
    
    public SettingsButton(ButtonManager manager, String name, ButtonType type) {
        super(manager, name, type);
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
        addActionListener(e -> new SettingsGui(owner).setVisible(true));
    }
}
