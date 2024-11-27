package me.doublenico.scaraGUI.gui.main;

import me.doublenico.scaraGUI.configuration.application.ApplicationConfiguration;
import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;

class AppItem extends JPanel {
    private final File appFile;
    private final JPanel parentPanel;

    public AppItem(String appName, File appFile, JPanel parentPanel) {
        this.appFile = appFile;
        this.parentPanel = parentPanel;

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

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(204, 0, 0));
        deleteButton.setFont(new Font("Inter", Font.BOLD, 12));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setOpaque(true);
        deleteButton.setPreferredSize(new Dimension(80, 30));
        deleteButton.addActionListener(e -> deleteApp());

        JButton modifyButton = new JButton("Modify");
        modifyButton.setBackground(new Color(0, 122, 204));
        modifyButton.setFont(new Font("Inter", Font.BOLD, 12));
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setFocusPainted(false);
        modifyButton.setOpaque(true);
        modifyButton.setPreferredSize(new Dimension(80, 30));
        modifyButton.addActionListener(e -> modifyApp());

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

    private void deleteApp() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this application?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (appFile.delete()) {
                parentPanel.remove(this);
                parentPanel.revalidate();
                parentPanel.repaint();
            } else JOptionPane.showMessageDialog(this, "Failed to delete the application file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyApp() {
        ApplicationConfiguration configuration = new ApplicationConfiguration(appFile.getParentFile(), appFile.getName());
        new AppCreationGUI(configuration).setVisible(true);
    }
}