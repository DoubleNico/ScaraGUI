package me.doublenico.scaraGUI.gui.creation.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.live.MotorTestGUI;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;

import javax.swing.*;
import java.awt.*;

public class MotorTestButton extends Button {

    public MotorTestButton(ButtonManager manager, ButtonType type, ApplicationFrame applicationFrame, String name) {
        super(manager, type, applicationFrame, name);
        setFont(new Font("Inter", Font.BOLD, 12));
        setBackground(new Color(35, 117, 30));
        setForeground(Color.WHITE);
        setFocusPainted(true);
        setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        setPreferredSize(new Dimension(80, 30));
    }

    public void loadEventListener(ScaraGUI owner) {
        addActionListener(e -> SwingUtilities.invokeLater(() -> new MotorTestGUI(owner)));
    }
}
