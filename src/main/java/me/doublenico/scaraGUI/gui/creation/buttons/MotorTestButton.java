package me.doublenico.scaraGUI.gui.creation.buttons;

import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.button.RoundedButton;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.live.MotorTestGUI;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;
import me.doublenico.scaraGUI.gui.settings.SettingsGui;

import javax.swing.*;
import java.awt.*;

public class MotorTestButton extends RoundedButton {

    public MotorTestButton(ButtonManager manager, ButtonType type, ApplicationFrame applicationFrame, String name) {
        super(manager, type, applicationFrame, name, 10);
        setFont(new Font("Inter", Font.BOLD, 12));
        setBackground(new Color(35, 117, 30));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setPreferredSize(new Dimension(100, 30));
    }

    public void loadEventListener(ScaraGUI owner) {
        addActionListener(e -> {
            if (owner.getArduinoManager() == null) {
                JOptionPane.showMessageDialog(owner,
                    "No Arduino connected. Please connect an Arduino first.",
                    "Connection Required",
                    JOptionPane.WARNING_MESSAGE);
                new SettingsGui(owner);
                return;
            }
            SwingUtilities.invokeLater(() -> new MotorTestGUI(owner));
        });
    }
}
