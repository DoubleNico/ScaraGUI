package me.doublenico.scaraGUI.gui.main;

import me.doublenico.scaraGUI.configuration.application.ApplicationConfiguration;
import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;
import me.doublenico.scaraGUI.utils.ExtensionUtils;
import me.doublenico.scaraGUI.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CreateAppModal extends JDialog {

    private final JPanel panel;
    private final ScaraGUI owner;

    public CreateAppModal(ScaraGUI owner, JPanel panel, String title){
        super(owner, title, true);
        this.panel = panel;
        this.owner = owner;
    }

    public void createModal(){
        setResizable(false);
        setSize(400, 300);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Inter", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        JTextField nameField = new JTextField("Insert Name");
        nameField.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nameField, gbc);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(new Font("Inter", Font.BOLD, 16));
        locationLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(locationLabel, gbc);

        JTextField locationField = new JTextField();
        locationField.setPreferredSize(new Dimension(200, 30));
        locationField.setEditable(false);
        String userDir = System.getProperty("user.dir");
        if (userDir != null) locationField.setText(System.getProperty("user.dir"));
        gbc.gridx = 1;
        gbc.gridy = 1;

        JButton chooseButton = new JButton("Browse");
        chooseButton.setBackground(new Color(9, 125, 201));
        chooseButton.setForeground(Color.WHITE);
        chooseButton.setFont(new Font("Inter", Font.BOLD, 12));
        chooseButton.setFocusPainted(false);

        chooseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose a location to save the application");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (locationField.getText() != null && !locationField.getText().isEmpty()) fileChooser.setCurrentDirectory(new File(locationField.getText()));
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                locationField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        JPanel locationPanel = new JPanel(new BorderLayout());
        locationPanel.add(locationField, BorderLayout.CENTER);
        locationPanel.add(chooseButton, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(locationPanel, gbc);

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(42, 255, 13));
        saveButton.setForeground(Color.BLACK);
        saveButton.setFont(new Font("Inter", Font.BOLD, 14));
        saveButton.setFocusPainted(false);
        saveButton.setPreferredSize(new Dimension(100, 30));
        saveButton.addActionListener(e -> {
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

                        dispose();
                        new AppCreationGUI(configuration).setVisible(true);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Failed to save the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(saveButton, gbc);
        getContentPane().setBackground(new Color(22, 22, 23));

        setVisible(true);
    }

    public JPanel getPanel() {
        return panel;
    }
}
