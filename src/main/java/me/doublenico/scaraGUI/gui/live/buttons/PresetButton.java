package me.doublenico.scaraGUI.gui.live.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;

import javax.swing.*;
import java.awt.*;

public class PresetButton extends Button {

    public PresetButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name);
        setBackground(new Color(60, 60, 60));
        setForeground(Color.WHITE);
        setFont(new Font("Inter", Font.PLAIN, 10));
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
    }


    public void loadEventListener(String preset, JTextField textField) {
        addActionListener(e -> textField.setText(preset));
    }

    public int loadIntegerEventListener(String preset, JTextField textField){
        addActionListener(e -> textField.setText(preset));
        return Integer.parseInt(textField.getText());
    }

    
}
