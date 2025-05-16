package me.doublenico.scaraGUI.gui.settings.dialogs;

import com.fazecast.jSerialComm.SerialPort;
import me.doublenico.scaraGUI.arduino.ArduinoManager;
import me.doublenico.scaraGUI.arduino.serial.SerialPortParameters;
import me.doublenico.scaraGUI.arduino.serial.SerialPortTimeouts;
import me.doublenico.scaraGUI.gui.settings.SettingsGui;

import javax.swing.*;
import java.awt.*;

public class ConnectDialog extends JDialog {

    public ConnectDialog(SettingsGui parent, ArduinoManager arduinoManager, JLabel selectedDeviceLabel, String title){
        super(parent, title, true);
        SerialPortParameters serialPortParameters = arduinoManager.getSerialPortParameters();
        SerialPortTimeouts serialPortTimeouts = arduinoManager.getSerialPortTimeouts();
        String selectedDevice = selectedDeviceLabel.getText();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setResizable(false);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(22, 22, 23));

        JLabel loadingLabel = new JLabel("Connecting to " + selectedDevice + "...", SwingConstants.CENTER);
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

        Thread connectionThread = new Thread(() -> {
            try {
                Thread.sleep(500);
                SerialPort port = SerialPort.getCommPort(selectedDevice);
                port.setComPortParameters(serialPortParameters.getBaudRate(),
                    serialPortParameters.getDataBits(),
                    serialPortParameters.getStopBits(),
                    serialPortParameters.getParity());
                port.setComPortTimeouts(serialPortTimeouts.getTimeoutMode(),
                    serialPortTimeouts.getReadTimeout(),
                    serialPortTimeouts.getWriteTimeout());

                boolean success = port.openPort();

                SwingUtilities.invokeLater(() -> {
                    if (success) {
                        loadingLabel.setText("Connected to " + selectedDevice);
                        arduinoManager.setSelectedPort(port);
                        arduinoManager.setOpened(true);

                        Timer timer = new Timer(1000, e -> dispose());
                        timer.setRepeats(false);
                        timer.start();
                    } else {
                        loadingLabel.setText("Failed to connect to " + selectedDevice);
                        buttonPanel.setVisible(true);
                        pack();
                        setSize(300, 150);
                    }
                    loadingLabel.revalidate();
                    loadingLabel.repaint();
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    loadingLabel.setText("Error during connection: " + e.getMessage());
                    buttonPanel.setVisible(true);
                    pack();
                    setSize(300, 150);
                    loadingLabel.revalidate();
                    loadingLabel.repaint();
                });
            }
        });

        connectionThread.start();
    }


}
