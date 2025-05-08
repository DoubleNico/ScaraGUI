package me.doublenico.scaraGUI.gui.live;

import com.fazecast.jSerialComm.SerialPort;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.live.buttons.DirectionalButton;
import me.doublenico.scaraGUI.gui.live.buttons.HomeButton;
import me.doublenico.scaraGUI.gui.live.buttons.PresetButton;
import me.doublenico.scaraGUI.gui.live.fields.ClickAwayBehavior;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;
import me.doublenico.scaraGUI.gui.settings.SettingsGui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MotorTestControlPanel extends JPanel {

    private final MotorTestGUI motorTestGUI;
    private final ScaraGUI owner;
    private final ApplicationFrame frame;
    private int currentSpeed = 500;
    private int currentAcceleration = 1000;
    private boolean isProcessingMove = false;
    private boolean isCheckingHome = true;

    public MotorTestControlPanel(MotorTestGUI motorTestGUI) {
        this.motorTestGUI = motorTestGUI;
        this.frame = motorTestGUI;
        this.owner = motorTestGUI.getOwner();
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            "Control",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Inter", Font.BOLD, 14),
            Color.WHITE));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setOpaque(false);

        JPanel stepsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        stepsPanel.setOpaque(false);
        JLabel stepsLabel = new JLabel("Steps:");
        stepsLabel.setForeground(Color.WHITE);
        stepsLabel.setFont(new Font("Inter", Font.BOLD, 14));
        JTextField customStepsField = new JTextField("100", 4);
        customStepsField.setHorizontalAlignment(JTextField.CENTER);
        customStepsField.setBackground(new Color(50, 50, 50));
        customStepsField.setForeground(Color.WHITE);
        stepsPanel.add(stepsLabel);
        stepsPanel.add(customStepsField);
        inputPanel.add(stepsPanel);
        new ClickAwayBehavior().addClickAwayBehavior(frame, customStepsField);

        JPanel speedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        speedPanel.setOpaque(false);
        JLabel speedLabel = new JLabel("Speed:");
        speedLabel.setForeground(Color.WHITE);
        speedLabel.setFont(new Font("Inter", Font.BOLD, 14));
        JTextField speedField = new JTextField(Integer.toString(currentSpeed), 4);
        speedField.setHorizontalAlignment(JTextField.CENTER);
        speedField.setBackground(new Color(50, 50, 50));
        speedField.setForeground(Color.WHITE);
        new ClickAwayBehavior().addClickAwayBehavior(frame, speedField);
        speedPanel.add(speedLabel);
        speedPanel.add(speedField);
        inputPanel.add(speedPanel);

        JPanel accelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        accelPanel.setOpaque(false);
        JLabel accelLabel = new JLabel("Accel:");
        accelLabel.setForeground(Color.WHITE);
        accelLabel.setFont(new Font("Inter", Font.BOLD, 14));
        JTextField accelField = new JTextField(Integer.toString(currentAcceleration), 4);
        accelField.setHorizontalAlignment(JTextField.CENTER);
        accelField.setBackground(new Color(50, 50, 50));
        accelField.setForeground(Color.WHITE);
        new ClickAwayBehavior().addClickAwayBehavior(frame, accelField);
        accelPanel.add(accelLabel);
        accelPanel.add(accelField);
        inputPanel.add(accelPanel);

        inputPanel.add(Box.createVerticalStrut(10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        DirectionalButton sendBackwardButton = new DirectionalButton(owner.getButtonManager(), ButtonType.DIRECTIONAL_BUTTON, frame, "◀ Backward", new Color(225, 48, 64));
        sendBackwardButton.addActionListener(e -> sendBackward(customStepsField, speedField, accelField));
        DirectionalButton sendForwardButton = new DirectionalButton(owner.getButtonManager(), ButtonType.DIRECTIONAL_BUTTON, frame, "Forward ▶", new Color(50, 80, 50));
        sendForwardButton.addActionListener(e -> sendForward(customStepsField, speedField, accelField));
        buttonPanel.add(sendBackwardButton);
        buttonPanel.add(sendForwardButton);
        inputPanel.add(buttonPanel);

        JPanel homeButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        homeButtonsPanel.setOpaque(false);

        HomeButton homeMotorButton = new HomeButton(owner.getButtonManager(), ButtonType.HOME_BUTTON, frame, "Home Current Motor", new Color(0, 122, 204));
        homeMotorButton.addActionListener(e -> homeMotor());

        HomeButton homeAllButton = new HomeButton(owner.getButtonManager(), ButtonType.HOME_BUTTON, frame, "Home All Motors", new Color(0, 100, 170));
        homeAllButton.addActionListener(e -> homeAllMotors());

        homeButtonsPanel.add(homeMotorButton);
        homeButtonsPanel.add(homeAllButton);
        inputPanel.add(homeButtonsPanel);

        setupKeyboardShortcuts(customStepsField, speedField, accelField);

        JPanel presetPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        presetPanel.setOpaque(false);

        JLabel presetLabel = new JLabel("Quick Steps:");
        presetLabel.setForeground(Color.WHITE);
        presetLabel.setFont(new Font("Inter", Font.BOLD, 12));
        presetPanel.add(presetLabel);

        String[] presets = {"10", "50", "100", "500", "1000"};
        for (String preset : presets) {
            PresetButton presetButton = new PresetButton(owner.getButtonManager(), preset, ButtonType.PRESET_BUTTON, frame);
            presetButton.loadEventListener(preset, customStepsField);
            presetPanel.add(presetButton);
        }

        JPanel speedPresetPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        speedPresetPanel.setOpaque(false);

        JLabel speedPresetLabel = new JLabel("Quick Speed:");
        speedPresetLabel.setForeground(Color.WHITE);
        speedPresetLabel.setFont(new Font("Inter", Font.BOLD, 12));
        speedPresetPanel.add(speedPresetLabel);

        String[] speedPresets = {"1000", "2000", "4000", "8000", "16000"};
        for (String preset : speedPresets) {
            PresetButton presetButton = new PresetButton(owner.getButtonManager(), preset, ButtonType.PRESET_BUTTON, frame);
            currentSpeed = presetButton.loadIntegerEventListener(preset, speedField);
            speedPresetPanel.add(presetButton);
        }

        JPanel accelPresetPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        accelPresetPanel.setOpaque(false);

        JLabel accelPresetLabel = new JLabel("Quick Accel:");
        accelPresetLabel.setForeground(Color.WHITE);
        accelPresetLabel.setFont(new Font("Inter", Font.BOLD, 12));
        accelPresetPanel.add(accelPresetLabel);

        String[] accelPresets = {"500", "1000", "2000", "4000", "8000"};
        for (String preset : accelPresets) {
            PresetButton presetButton = new PresetButton(owner.getButtonManager(), preset, ButtonType.PRESET_BUTTON, frame);
            currentAcceleration = presetButton.loadIntegerEventListener(preset, speedField);
            accelPresetPanel.add(presetButton);
        }

        JPanel shortcutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        shortcutPanel.setOpaque(false);
        JLabel shortcutInfo = new JLabel("Shortcuts: W = Forward | S = Backward");
        shortcutInfo.setForeground(new Color(180, 180, 180));
        shortcutInfo.setFont(new Font("Inter", Font.ITALIC, 12));
        JCheckBox autoScrollCheckbox = new JCheckBox("Homing Check", true);
        autoScrollCheckbox.setForeground(Color.WHITE);
        autoScrollCheckbox.setBackground(new Color(22, 22, 23));
        autoScrollCheckbox.addActionListener(e -> isCheckingHome = autoScrollCheckbox.isSelected());
        shortcutPanel.add(autoScrollCheckbox);
        shortcutPanel.add(shortcutInfo);

        inputPanel.add(presetPanel);
        inputPanel.add(speedPresetPanel);
        inputPanel.add(accelPresetPanel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(shortcutPanel);

        add(inputPanel, BorderLayout.CENTER);
    }


    private void setupKeyboardShortcuts(JTextField customStepsField, JTextField speedField, JTextField accelField) {
        InputMap inputMap = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = frame.getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke('w'), "forward");
        actionMap.put("forward", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isProcessingMove) {
                    sendForward(customStepsField, speedField, accelField);
                }
            }
        });

        inputMap.put(KeyStroke.getKeyStroke('s'), "backward");
        actionMap.put("backward", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isProcessingMove) {
                    sendBackward(customStepsField, speedField, accelField);
                }
            }
        });
    }

    private void sendForward(JTextField customStepsField, JTextField speedField, JTextField accelField) {
        try {
            if (isProcessingMove) return;

            isProcessingMove = true;
            int steps = Integer.parseInt(customStepsField.getText());
            updateSpeedAndAcceleration(speedField, accelField);
            sendSteps(steps);
            new MotorTestKeybinds(frame).flashFeedback(true);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid number of steps",
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE);
            isProcessingMove = false;
        }
    }

    private void sendBackward(JTextField customStepsField, JTextField speedField, JTextField accelField) {
        try {
            if (isProcessingMove) return;

            isProcessingMove = true;
            int steps = -Integer.parseInt(customStepsField.getText());
            updateSpeedAndAcceleration(speedField, accelField);
            sendSteps(steps);
            new MotorTestKeybinds(frame).flashFeedback(false);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid number of steps",
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE);
            isProcessingMove = false;
        }
    }

    private void updateSpeedAndAcceleration(JTextField speedField, JTextField accelField) {

        try {
            currentSpeed = Integer.parseInt(speedField.getText());
            if (currentSpeed < 0) {
                currentSpeed = 4000;
                speedField.setText("4000");
            }
        } catch (NumberFormatException ex)
        {
            currentSpeed = 4000;
            speedField.setText("4000");
        }

        try {
            currentAcceleration = Integer.parseInt(accelField.getText());
            if (currentAcceleration < 0) {
                currentAcceleration = 2000;
                accelField.setText("2000");
            }
        } catch (NumberFormatException ex) {
            currentAcceleration = 2000;
            accelField.setText("2000");
        }
    }

    private void sendSteps(int steps) {
        SerialPort port = owner.getArduinoManager().getSelectedPort();
        if (port != null && port.isOpen()) {
            try {
                OutputStream outputStream = port.getOutputStream();

                int newPosition = motorTestGUI.getCurrentPosition() + steps;

                if (motorTestGUI.getMotorPanel().getSelectedMotor().equals("Z") && newPosition < 0 && isCheckingHome) {
                    JOptionPane.showMessageDialog(this,
                        "Z axis cannot go below position 0",
                        "Movement Error",
                        JOptionPane.WARNING_MESSAGE);
                    isProcessingMove = false;
                    return;
                }
                if (motorTestGUI.getMotorPanel().getSelectedMotor().equals("Joint3") && newPosition < 0 && isCheckingHome) {
                    JOptionPane.showMessageDialog(this,
                        "Joint3 cannot go below position 0",
                        "Movement Error",
                        JOptionPane.WARNING_MESSAGE);
                    isProcessingMove = false;
                    return;
                }

                if (motorTestGUI.getMotorPanel().getSelectedMotor().equals("Gripper") && newPosition < 0) {
                    JOptionPane.showMessageDialog(this,
                        "Gripper cannot go below position 0",
                        "Movement Error",
                        JOptionPane.WARNING_MESSAGE);
                    isProcessingMove = false;
                    return;
                }

                if (motorTestGUI.getMotorPanel().getSelectedMotor().equals("Gripper") && newPosition > 180) {
                    JOptionPane.showMessageDialog(this,
                        "Gripper cannot go above position 180",
                        "Movement Error",
                        JOptionPane.WARNING_MESSAGE);
                    isProcessingMove = false;
                    return;
                }


                // Format: MOTOR:steps,speed,acceleration
                String command = motorTestGUI.getMotorPanel().getSelectedMotor() + ":" + steps + "," + currentSpeed + "," + currentAcceleration + "\n";

                System.out.println("Sending command: " + command);

                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                new Thread(() -> {
                    try {
                        outputStream.write(command.getBytes(StandardCharsets.UTF_8));
                        outputStream.flush();

                        Thread.sleep(1000);

                        SwingUtilities.invokeLater(() -> {
                            motorTestGUI.setCurrentPosition(newPosition);
                            motorTestGUI.getCoordinateDisplay().setText("Position: " + motorTestGUI.getCurrentPosition());

                            setCursor(Cursor.getDefaultCursor());
                            isProcessingMove = false;
                        });

                    } catch (IOException ex) {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(frame,
                                "Error sending command: " + ex.getMessage(),
                                "Communication Error",
                                JOptionPane.ERROR_MESSAGE);
                            setCursor(Cursor.getDefaultCursor());
                            isProcessingMove = false;
                        });
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        SwingUtilities.invokeLater(() -> {
                            setCursor(Cursor.getDefaultCursor());
                            isProcessingMove = false;
                        });
                    }
                }).start();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Error sending command: " + ex.getMessage(),
                    "Communication Error",
                    JOptionPane.ERROR_MESSAGE);
                isProcessingMove = false;
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "No Arduino connected or port is not open.\nPlease connect to an Arduino first.",
                "Connection Required",
                JOptionPane.WARNING_MESSAGE);

            new SettingsGui(owner);
            isProcessingMove = false;
        }
    }

    private void homeMotor() {
        SerialPort port = owner.getArduinoManager().getSelectedPort();
        if (port != null && port.isOpen()) {
            try {
                OutputStream outputStream = port.getOutputStream();
                String command = "HOME:" + motorTestGUI.getMotorPanel().getSelectedMotor() + "\n";
                System.out.println("Sending home command: " + command);
                outputStream.write(command.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();

                motorTestGUI.setCurrentPosition(0);
                motorTestGUI.getCoordinateDisplay().setText("Position: 0");

                JOptionPane.showMessageDialog(this,
                    "Homing command sent for " + motorTestGUI.getMotorPanel().getSelectedMotor(),
                    "Homing",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Failed to send home command: " + ex.getMessage(),
                    "Communication Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "No Arduino connected or port is not open",
                "Connection Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void homeAllMotors() {
        SerialPort port = owner.getArduinoManager().getSelectedPort();
        if (port != null && port.isOpen()) {
            try {
                OutputStream outputStream = port.getOutputStream();
                String command = "HOME\n";
                System.out.println("Sending home all motors command: " + command);
                outputStream.write(command.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();

                motorTestGUI.setCurrentPosition(0);
                motorTestGUI.getCoordinateDisplay().setText("Position: 0");

                JOptionPane.showMessageDialog(this,
                    "Command sent to home all motors",
                    "Homing All Motors",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Failed to send home all command: " + ex.getMessage(),
                    "Communication Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "No Arduino connected or port is not open.",
                "Connection Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public int getCurrentAcceleration() {
        return currentAcceleration;
    }

    public void setCurrentAcceleration(int currentAcceleration) {
        this.currentAcceleration = currentAcceleration;
    }
}
