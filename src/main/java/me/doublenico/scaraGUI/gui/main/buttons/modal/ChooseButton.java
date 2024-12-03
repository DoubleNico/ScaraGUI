package me.doublenico.scaraGUI.gui.main.buttons.modal;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ChooseButton extends Button {
    public ChooseButton(ButtonManager manager, String name, ButtonType type) {
        super(manager, name, type);
        setBackground(new Color(9, 125, 201));
        setForeground(Color.WHITE);
        setFont(new Font("Inter", Font.BOLD, 12));
        setFocusPainted(false);
    }

    public void loadEventListener(JTextField locationField){
        addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose a location to save the application");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (locationField.getText() != null && !locationField.getText().isEmpty()) fileChooser.setCurrentDirectory(new File(locationField.getText()));
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                locationField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
    }
}
