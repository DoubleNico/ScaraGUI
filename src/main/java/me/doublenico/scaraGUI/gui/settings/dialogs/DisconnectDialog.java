package me.doublenico.scaraGUI.gui.settings.dialogs;

import me.doublenico.scaraGUI.arduino.ArduinoManager;
import me.doublenico.scaraGUI.gui.settings.SettingsGui;

import javax.swing.*;
import java.awt.*;

public class DisconnectDialog extends JDialog {

    public DisconnectDialog(SettingsGui parent, ArduinoManager arduinoManager, JLabel selectedDeviceLabel, String title) {
        super(parent, title, true);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setResizable(false);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(22, 22, 23));

        JLabel loadingLabel = new JLabel("Disconnecting from " + arduinoManager.getSelectedPort().getSystemPortName() + "...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        contentPanel.add(loadingLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(22, 22, 23));
        buttonPanel.setVisible(false);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(contentPanel);

        Thread disconnectThread = new Thread(() -> {
            try {
                Thread.sleep(500);
                boolean success = arduinoManager.getSelectedPort().closePort();

                SwingUtilities.invokeLater(() -> {
                    if (success) {
                        loadingLabel.setText("Disconnected from " + arduinoManager.getSelectedPort().getSystemPortName());
                        arduinoManager.setOpened(false);
                        arduinoManager.setSelectedPort(null);
                        arduinoManager.setSelectedDeviceLabel(null);

                        Timer timer = new Timer(1000, e -> dispose());
                        timer.setRepeats(false);
                        timer.start();
                    } else {
                        loadingLabel.setText("Failed to disconnect from " + arduinoManager.getSelectedPort().getSystemPortName());
                        buttonPanel.setVisible(true);
                        pack();
                        setSize(300, 150);
                    }
                    loadingLabel.revalidate();
                    loadingLabel.repaint();
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    loadingLabel.setText("Error during disconnection: " + e.getMessage());
                    buttonPanel.setVisible(true);
                    pack();
                    setSize(300, 150);
                    loadingLabel.revalidate();
                    loadingLabel.repaint();
                });
            }
        });

        disconnectThread.start();
    }
}
