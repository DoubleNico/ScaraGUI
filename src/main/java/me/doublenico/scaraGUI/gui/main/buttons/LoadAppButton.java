package me.doublenico.scaraGUI.gui.main.buttons;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.configuration.application.ApplicationConfiguration;
import me.doublenico.scaraGUI.configuration.application.ApplicationModel;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;
import me.doublenico.scaraGUI.utils.FileUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoadAppButton extends Button {
    public LoadAppButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name);
        setBackground(new Color(9, 125, 201));
        setDefaultDesign();
        setFont(new Font("Inter", Font.BOLD, 12));
        setPreferredSize(new Dimension(105, 33));
        setMinimumSize(new Dimension(105, 33));
        setMaximumSize(new Dimension(105, 33));
    }

    public void loadEventListener(ScaraGUI owner){
        addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select an application file");
            String userDir = System.getProperty("user.dir");
            if (userDir != null) fileChooser.setCurrentDirectory(new File(userDir));
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".yml");
                }

                @Override
                public String getDescription() {
                    return "YAML Files (*.yml)";
                }
            });

            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

                if (selectedFile == null) {
                    JOptionPane.showMessageDialog(this, "No file selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (selectedFile.exists() && selectedFile.isDirectory()) {
                    JOptionPane.showMessageDialog(this, "No file selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (new FileUtils().handleSelectedFile(selectedFile))
                    owner.getLocationsConfiguration().addApplication(selectedFile.getAbsolutePath());

                try {
                    ApplicationModel application = mapper.readValue(selectedFile, ApplicationModel.class);
                    if (application != null && application.getName() != null) {
                        ApplicationConfiguration configuration = new ApplicationConfiguration(selectedFile.getParentFile(), selectedFile.getName());
                        new AppCreationGUI(configuration, owner).setVisible(true);
                    } else JOptionPane.showMessageDialog(this, "Invalid application file.", "Error", JOptionPane.ERROR_MESSAGE);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to load application: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

    }

}
