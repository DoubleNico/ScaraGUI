package me.doublenico.scaraGUI.gui.main.buttons.appItem;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.gui.main.AppItem;

import java.awt.*;

public class DeleteButton extends Button {
    public DeleteButton(ButtonManager manager, String name, ButtonType type) {
        super(manager, name, type);
        setBackground(new Color(204, 0, 0));
        setFont(new Font("Inter", Font.BOLD, 12));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setOpaque(true);
        setPreferredSize(new Dimension(80, 30));
    }

    public void loadEventListener(AppItem appItem){
        addActionListener(e -> appItem.deleteApp());
    }
}
