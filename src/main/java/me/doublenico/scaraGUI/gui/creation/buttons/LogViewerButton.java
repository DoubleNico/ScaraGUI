package me.doublenico.scaraGUI.gui.creation.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.logs.LogViewerGUI;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;

import javax.swing.*;
import java.awt.*;

public class LogViewerButton extends Button {

    public LogViewerButton(ButtonManager manager, ButtonType type, ApplicationFrame applicationFrame, String name) {
        super(manager, type, applicationFrame, name);
        setFont(new Font("Inter", Font.BOLD, 12));
        setBackground(new Color(117, 117, 197));
        setForeground(Color.WHITE);
        setFocusPainted(true);
        setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        setPreferredSize(new Dimension(80, 30));
    }

    public void loadEventListener(ScaraGUI owner) {
        addActionListener(e -> {
            if (owner.getArduinoManager() != null)
                SwingUtilities.invokeLater(() -> new LogViewerGUI(owner, owner.getArduinoManager()));
        });
    }
}
