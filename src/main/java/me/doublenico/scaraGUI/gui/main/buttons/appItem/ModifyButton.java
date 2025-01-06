package me.doublenico.scaraGUI.gui.main.buttons.appItem;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.main.AppItem;

import java.awt.*;

public class ModifyButton extends Button {
    public ModifyButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name);
        setBackground(new Color(0, 122, 204));
        setFont(new Font("Inter", Font.BOLD, 12));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setOpaque(true);
        setPreferredSize(new Dimension(80, 30));
    }

    public void loadEventListener(AppItem appItem){
        addActionListener(e -> appItem.modifyApp());
    }
}
