package me.doublenico.scaraGUI.gui.creation.buttons;

import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.button.RoundedButton;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.logs.LogViewerGUI;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;

import javax.swing.*;
import java.awt.*;

public class LogViewerButton extends RoundedButton {

    public LogViewerButton(ButtonManager manager, ButtonType type, ApplicationFrame applicationFrame, String name) {
        super(manager, type, applicationFrame, name, 10);
        setFont(new Font("Inter", Font.BOLD, 12));
        setBackground(new Color(117, 117, 197));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setPreferredSize(new Dimension(100, 30));
    }

    public void loadEventListener(ScaraGUI owner) {
        addActionListener(e -> {
            if (owner.getArduinoManager() != null)
                SwingUtilities.invokeLater(() -> new LogViewerGUI(owner, owner.getArduinoManager()));
        });
    }
}
