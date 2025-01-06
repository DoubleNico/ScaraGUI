package me.doublenico.scaraGUI.gui.creation.components.sidebar.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;

import java.awt.*;

public class BackButton extends Button {
    public BackButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name);
        setFont(new Font("Inter", Font.BOLD, 12));
        setToolTipText("Go back to the main menu");
        setBackground(new Color(204, 0, 0));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setPreferredSize(new Dimension(40, 30));
    }

    public void loadEventListener(AppCreationGUI parent){
        addActionListener(e -> {
            new ScaraGUI().setVisible(true);
            parent.dispose();
        });
    }
}
