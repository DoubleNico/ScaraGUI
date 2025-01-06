package me.doublenico.scaraGUI.gui.main;

import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.configuration.application.ApplicationConfiguration;
import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;
import me.doublenico.scaraGUI.gui.main.buttons.appItem.DeleteButton;
import me.doublenico.scaraGUI.gui.main.buttons.appItem.ModifyButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AppItem extends JPanel {
    private final File appFile;
    private final JPanel parentPanel;
    private final ScaraGUI owner;

    public AppItem(String appName, File appFile, JPanel parentPanel, ScaraGUI owner) {
        this.appFile = appFile;
        this.parentPanel = parentPanel;
        this.owner = owner;

        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(new Color(50, 50, 50));
        setPreferredSize(new Dimension(468, 40));

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
        labelPanel.setOpaque(false);

        JLabel appLabel = new JLabel(appName);
        appLabel.setFont(new Font("Inter", Font.BOLD, 18));
        appLabel.setForeground(Color.WHITE);
        appLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        labelPanel.add(appLabel);
        labelPanel.add(Box.createHorizontalGlue());

        DeleteButton deleteButton = new DeleteButton(owner.getButtonManager(), "Delete", ButtonType.DELETE_BUTTON, owner);
        deleteButton.loadEventListener(this);

        ModifyButton modifyButton = new ModifyButton(owner.getButtonManager(), "Modify", ButtonType.MODIFY_BUTTON, owner);
        modifyButton.loadEventListener(this);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.VERTICAL;
        buttonPanel.add(deleteButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(modifyButton, gbc);

        add(labelPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    public void deleteApp() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this application?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (appFile.delete()) {
                parentPanel.remove(this);
                parentPanel.revalidate();
                parentPanel.repaint();
            } else JOptionPane.showMessageDialog(this, "Failed to delete the application file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modifyApp() {
        ApplicationConfiguration configuration = new ApplicationConfiguration(appFile.getParentFile(), appFile.getName());
        owner.dispose();
        new AppCreationGUI(configuration, owner).setVisible(true);
    }

    public File getAppFile() {
        return appFile;
    }

    public JPanel getParentPanel() {
        return parentPanel;
    }

    public ScaraGUI getOwner() {
        return owner;
    }
}