package me.doublenico.scaraGUI.gui.live;

import com.fazecast.jSerialComm.SerialPort;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.frame.ApplicationFrameType;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;
import me.doublenico.scaraGUI.gui.settings.SettingsGui;

import javax.swing.*;
import java.awt.*;

public class MotorTestGUI extends ApplicationFrame {

    private final ScaraGUI owner;
    private JLabel coordinateDisplay;
    private MotorTestControlPanel controlPanel;
    private MotorTestMotorPanel motorPanel;
    private int currentPosition = 0;

    public MotorTestGUI(ScaraGUI owner) {
        super("Motor Test", ApplicationFrameType.MOTOR_TEST);
        this.owner = owner;

        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 900);
        setLocationRelativeTo(null);

        SerialPort serialPort = owner.getArduinoManager().getSelectedPort();
        if (serialPort == null) {
            JOptionPane.showMessageDialog(this,
                "No Arduino connected. Please connect an Arduino first.",
                "Connection Required",
                JOptionPane.WARNING_MESSAGE);
            new SettingsGui(owner);
            dispose();
            return;
        }

        if (!owner.getArduinoManager().isOpened()) {
            JOptionPane.showMessageDialog(this,
                "Failed to open serial port. Please check your connection.",
                "Connection Error",
                JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(22, 22, 23));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Motor Test Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Inter", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        controlPanel = new MotorTestControlPanel(this);

        motorPanel = new MotorTestMotorPanel(this);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 20));
        centerPanel.setOpaque(false);
        centerPanel.add(motorPanel, BorderLayout.NORTH);
        centerPanel.add(controlPanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        coordinateDisplay = new JLabel("Position: 0", SwingConstants.CENTER);
        coordinateDisplay.setFont(new Font("Inter", Font.BOLD, 18));
        coordinateDisplay.setForeground(Color.WHITE);
        coordinateDisplay.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 50), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        coordinateDisplay.setOpaque(true);
        coordinateDisplay.setBackground(new Color(38, 38, 38));
        mainPanel.add(coordinateDisplay, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setVisible(true);
    }

    @Override
    public ScaraGUI getOwner() {
        return owner;
    }

    public MotorTestControlPanel getControlPanel() {
        return controlPanel;
    }

    public MotorTestMotorPanel getMotorPanel() {
        return motorPanel;
    }

    public JLabel getCoordinateDisplay() {
        return coordinateDisplay;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }


}