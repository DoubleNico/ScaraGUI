package me.doublenico.scaraGUI.gui.live.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DirectionalButton extends Button {

    public DirectionalButton(ButtonManager manager, ButtonType type, ApplicationFrame applicationFrame, String name) {
        super(manager, type, applicationFrame, name);
    }

    public DirectionalButton(ButtonManager manager, ButtonType type, ApplicationFrame applicationFrame, String name, Color color) {
        super(manager, type, applicationFrame, name);
        setBackground(color);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setFont(new Font("Inter", Font.BOLD, 10));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 70, 70), 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                setBackground(new Color(
                    Math.min(color.getRed() + 20, 255),
                    Math.min(color.getGreen() + 20, 255),
                    Math.min(color.getBlue() + 20, 255)
                ));
            }

            public void mouseExited(MouseEvent evt) {
                setBackground(color);
            }


            public void mouseReleased(MouseEvent evt) {
                setBackground(color);
            }
        });
    }
}
