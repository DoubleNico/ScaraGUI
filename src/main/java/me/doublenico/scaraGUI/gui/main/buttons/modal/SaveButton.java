package me.doublenico.scaraGUI.gui.main.buttons.modal;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.configuration.application.ApplicationConfiguration;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;
import me.doublenico.scaraGUI.utils.ExtensionUtils;
import me.doublenico.scaraGUI.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SaveButton extends Button {
    public SaveButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name);
        setBackground(new Color(42, 255, 13));
        setForeground(Color.BLACK);
        setFont(new Font("Inter", Font.BOLD, 14));
        setFocusPainted(false);
        setPreferredSize(new Dimension(100, 30));
    }

    public void loadEventListener(ScaraGUI owner, JDialog modal, JTextField nameField, JTextField locationField){
        addActionListener(e -> {
            String name = nameField.getText();
            String location = locationField.getText();
            if (name == null || name.isEmpty() || location == null || location.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and location cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                File file = new File(location + File.separator + new ExtensionUtils(name).getFileNameWithoutExtension() + ".yml");
                if (file.exists()) {
                    JOptionPane.showMessageDialog(this, "A file with the same name already exists at the specified location.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        File directory = new File(location);
                        if (!directory.exists()) {
                            if (!directory.mkdirs()) {
                                JOptionPane.showMessageDialog(this, "Failed to create the directory.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        if (new FileUtils().handleSelectedFile(file))
                            owner.getLocationsConfiguration().addApplication(file.getAbsolutePath());

                        ApplicationConfiguration configuration = new ApplicationConfiguration(directory, new ExtensionUtils(name).getFileNameWithoutExtension() + ".yml");
                        configuration.loadConfiguration();

                        modal.dispose();
                        new AppCreationGUI(configuration, owner).setVisible(true);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Failed to save the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });

    }
}
