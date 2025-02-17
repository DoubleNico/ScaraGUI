package me.doublenico.scaraGUI.gui.settings.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.settings.SettingsGui;

import javax.swing.*;
import java.awt.*;

public class RefreshButton extends Button {
    public RefreshButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name);
        setFont(new Font("Inter", Font.BOLD, 12));
        setBackground(new Color(204, 0, 0));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setPreferredSize(new Dimension(100, 40));
        setBorder(BorderFactory.createEmptyBorder());
    }

    public void loadEventListener(SettingsGui settings){
        addActionListener(e -> settings.refreshDeviceList());
    }
}
