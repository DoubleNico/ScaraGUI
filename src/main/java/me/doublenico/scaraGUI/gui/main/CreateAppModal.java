package me.doublenico.scaraGUI.gui.main;

import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.gui.main.buttons.modal.ChooseButton;
import me.doublenico.scaraGUI.gui.main.buttons.modal.SaveButton;

import javax.swing.*;
import java.awt.*;

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

        ChooseButton chooseButton = new ChooseButton(owner.getButtonManager(), "Browse", ButtonType.LOAD_APP, owner);
        chooseButton.loadEventListener(locationField);

        JPanel locationPanel = new JPanel(new BorderLayout());
        locationPanel.add(locationField, BorderLayout.CENTER);
        locationPanel.add(chooseButton, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(locationPanel, gbc);

        SaveButton saveButton = new SaveButton(owner.getButtonManager(), "Save", ButtonType.LOAD_APP, owner);
        saveButton.loadEventListener(owner, this, nameField, locationField);

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
