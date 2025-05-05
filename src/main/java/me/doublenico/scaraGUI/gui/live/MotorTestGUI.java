package me.doublenico.scaraGUI.gui.live;

import com.fazecast.jSerialComm.SerialPort;
import me.doublenico.scaraGUI.arduino.ArduinoManager;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.frame.ApplicationFrameType;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;
import me.doublenico.scaraGUI.gui.settings.SettingsGui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MotorTestGUI extends ApplicationFrame {

    private final ScaraGUI owner;
    private JLabel coordinateDisplay;
    private ButtonGroup motorGroup;
    private String selectedMotor = "Joint1";
    private int currentPosition = 0;

    public MotorTestGUI(ScaraGUI owner) {
        super("Motor Test");
        this.owner = owner;

        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);

        // Check if Arduino is connected, if not, open settings
        SerialPort serialPort = SerialPort.getCommPort(owner.getArduinoConfiguration().getSerialPort());
        if (serialPort == null) {
            JOptionPane.showMessageDialog(this,
                    "No Arduino connected. Please connect an Arduino first.",
                    "Connection Required",
                    JOptionPane.WARNING_MESSAGE);
            new SettingsGui(owner);
            dispose();
            return;
        }

        // Create Arduino manager
        SerialPort connectedPort = SerialPort.getCommPort(owner.getArduinoConfiguration().getSerialPort());

        // Create main panel with dark background
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(22, 22, 23));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create title
        JLabel titleLabel = new JLabel("Motor Test Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Inter", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create motor selection panel
        JPanel motorSelectionPanel = createMotorSelectionPanel();

        // Create control panel
        JPanel controlPanel = createControlPanel();

        // Create center panel to hold both
        JPanel centerPanel = new JPanel(new BorderLayout(10, 20));
        centerPanel.setOpaque(false);
        centerPanel.add(motorSelectionPanel, BorderLayout.NORTH);
        centerPanel.add(controlPanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Create coordinate display
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

    private JPanel createMotorSelectionPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 0, 5));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(50, 50, 50), 1),
                "Select Motor"
        ));
        ((javax.swing.border.TitledBorder) panel.getBorder()).setTitleColor(Color.WHITE);

        motorGroup = new ButtonGroup();

        String[] motors = {"Joint1", "Joint2", "Joint3", "Z", "Gripper"};
        for (String motor : motors) {
            JRadioButton radioButton = new JRadioButton(motor);
            radioButton.setForeground(Color.WHITE);
            radioButton.setOpaque(false);
            radioButton.addActionListener(e -> {
                selectedMotor = motor;
                currentPosition = 0;
                coordinateDisplay.setText("Position: " + currentPosition);
            });
            motorGroup.add(radioButton);
            panel.add(radioButton);

            // Select first motor by default
            if (motor.equals("Joint1")) {
                radioButton.setSelected(true);
            }
        }

        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(50, 50, 50), 1),
                "Control"
        ));
        ((javax.swing.border.TitledBorder) panel.getBorder()).setTitleColor(Color.WHITE);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonsPanel.setOpaque(false);

        JButton backwardButton = new JButton("◄ Backward");
        styleButton(backwardButton);
        backwardButton.addActionListener(e -> moveMotor(-1));

        JButton forwardButton = new JButton("Forward ►");
        styleButton(forwardButton);
        forwardButton.addActionListener(e -> moveMotor(1));

        buttonsPanel.add(backwardButton);
        buttonsPanel.add(forwardButton);

        // Step size selector
        JPanel stepPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepPanel.setOpaque(false);

        JLabel stepLabel = new JLabel("Step Size:");
        stepLabel.setForeground(Color.WHITE);
        stepPanel.add(stepLabel);

        JComboBox<Integer> stepSizeCombo = new JComboBox<>(new Integer[]{1, 5, 10, 25, 50});
        stepSizeCombo.setBackground(new Color(38, 38, 38));
        stepSizeCombo.setForeground(Color.WHITE);
        stepPanel.add(stepSizeCombo);

        panel.add(buttonsPanel, BorderLayout.CENTER);
        panel.add(stepPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(38, 38, 38));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Inter", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 50, 50), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
    }

    private void moveMotor(int direction) {
        // Get the step size from the combo box
        JComboBox<?> stepSizeCombo = null;
        Container parent = coordinateDisplay.getParent();
        while (parent != null) {
            for (Component c : parent.getComponents()) {
                if (c instanceof JPanel) {
                    for (Component c2 : ((JPanel) c).getComponents()) {
                        if (c2 instanceof JPanel && ((JPanel) c2).getBorder() != null &&
                                ((JPanel) c2).getBorder().toString().contains("Control")) {
                            for (Component c3 : ((JPanel) c2).getComponents()) {
                                if (c3 instanceof JPanel) {
                                    for (Component c4 : ((JPanel) c3).getComponents()) {
                                        if (c4 instanceof JComboBox) {
                                            stepSizeCombo = (JComboBox<?>) c4;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            parent = parent.getParent();
        }

        int stepSize = 1;
        if (stepSizeCombo != null) {
            stepSize = (Integer) stepSizeCombo.getSelectedItem();
        }

        // Update position
        currentPosition += (direction * stepSize);
        coordinateDisplay.setText("Position: " + currentPosition);

        // Send command to Arduino
//        SerialPort port = arduinoManager.getSelectedPort();
//        if (port != null && port.isOpen()) {
//            try {
//                String command = selectedMotor + ":" + (direction * stepSize) + "\n";
//                OutputStream outputStream = port.getOutputStream();
//                outputStream.write(command.getBytes(StandardCharsets.UTF_8));
//                outputStream.flush();
//            } catch (IOException ex) {
//                JOptionPane.showMessageDialog(this,
//                        "Failed to send command to Arduino: " + ex.getMessage(),
//                        "Communication Error",
//                        JOptionPane.ERROR_MESSAGE);
//            }
//        }
    }

    @Override
    public ApplicationFrameType getFrameType() {
        return ApplicationFrameType.MOTOR_TEST;
    }

    @Override
    public ScaraGUI getOwner() {
        return owner;
    }
}