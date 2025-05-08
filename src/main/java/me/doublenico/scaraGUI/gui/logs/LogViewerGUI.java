package me.doublenico.scaraGUI.gui.logs;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;
import me.doublenico.scaraGUI.arduino.ArduinoManager;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.frame.ApplicationFrameType;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;
import me.doublenico.scaraGUI.gui.settings.SettingsGui;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogViewerGUI extends ApplicationFrame implements SerialPortMessageListener {

    private final ScaraGUI owner;
    private JTextArea logArea;
    private ArduinoManager arduinoManager;
    private boolean autoScroll = true;

    public LogViewerGUI(ScaraGUI owner, ArduinoManager arduinoManager) {
        super("Arduino Log Viewer");
        this.owner = owner;
        this.arduinoManager = arduinoManager;

        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        SerialPort serialPort = owner.getArduinoManager().getSelectedPort();
        if (serialPort == null) {
            JOptionPane.showMessageDialog(this,
                "No Arduino connected. Please connect an Arduino first.",
                "Connection Required",
                JOptionPane.WARNING_MESSAGE);
            new SettingsGui(owner);
            setVisible(false);
            dispose();
            return;
        }
        if (!owner.getArduinoManager().isOpened()) {
            JOptionPane.showMessageDialog(this,
                "Failed to open serial port. Please check your connection.",
                "Connection Error",
                JOptionPane.ERROR_MESSAGE);
            setVisible(false);
            dispose();
            return;
        }

        serialPort.addDataListener(this);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(22, 22, 23));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create title
        JLabel titleLabel = new JLabel("Arduino Log Viewer", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Inter", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create log area
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(new Color(38, 38, 38));
        logArea.setForeground(Color.WHITE);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        logArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Auto-scroll setup
        DefaultCaret caret = (DefaultCaret) logArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 1));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        // Auto-scroll toggle
        JCheckBox autoScrollCheckbox = new JCheckBox("Auto-scroll", true);
        autoScrollCheckbox.setForeground(Color.WHITE);
        autoScrollCheckbox.setBackground(new Color(22, 22, 23));
        autoScrollCheckbox.addActionListener(e -> {
            autoScroll = autoScrollCheckbox.isSelected();
            if (autoScroll) {
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            } else {
                caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
            }
        });
        buttonPanel.add(autoScrollCheckbox);

        // Clear button
        JButton clearButton = new JButton("Clear Logs");
        styleButton(clearButton);
        clearButton.addActionListener(e -> logArea.setText(""));
        buttonPanel.add(clearButton);

        // Save button
        JButton saveButton = new JButton("Save Logs");
        styleButton(saveButton);
        saveButton.addActionListener(e -> saveLogsToFile());
        buttonPanel.add(saveButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        logMessage("Log viewer started. Waiting for Arduino messages...");
        logMessage("Connected to port: " + serialPort.getSystemPortName());
        setVisible(true);

        getArduinoPrint();
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(38, 38, 38));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 50, 50), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void saveLogsToFile() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());
        String filename = "arduino_log_" + timestamp + ".txt";

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(filename));

        int choice = fileChooser.showSaveDialog(this);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(logArea.getText());
                JOptionPane.showMessageDialog(this,
                        "Logs saved to " + file.getAbsolutePath(),
                        "Save Successful",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error saving logs: " + ex.getMessage(),
                        "Save Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void logMessage(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        SwingUtilities.invokeLater(() -> {
            logArea.append("[" + timestamp + "] " + message + "\n");
        });
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
            byte[] newData = event.getReceivedData();
            String message = new String(newData, StandardCharsets.UTF_8);
            logMessage(message.trim());
        }
    }

    public void getArduinoPrint() {
        SerialPort serialPort = arduinoManager.getSelectedPort();
        if (serialPort == null) {
            logMessage("Error: No serial port connected or port is not open");
            return;
        }

        Thread readThread = getThread(serialPort);

        JButton stopButton = new JButton("Stop Direct Monitor");
        styleButton(stopButton);
        stopButton.addActionListener(e -> {
            readThread.interrupt();
            Container parent = ((JButton)e.getSource()).getParent();
            parent.remove((JButton)e.getSource());
            parent.revalidate();
            parent.repaint();
        });

        for (Component c : getContentPane().getComponents()) {
            if (c instanceof JPanel && ((JPanel)c).getLayout() instanceof BorderLayout) {
                for (Component innerC : ((JPanel)c).getComponents()) {
                    if (innerC instanceof JPanel && innerC == ((BorderLayout)((JPanel)c).getLayout()).getLayoutComponent(BorderLayout.SOUTH)) {
                        ((JPanel)innerC).add(stopButton);
                        innerC.revalidate();
                        break;
                    }
                }
            }
        }
    }

    private Thread getThread(SerialPort serialPort) {
        Thread readThread = new Thread(() -> {
            InputStream inputStream = serialPort.getInputStream();
            byte[] buffer = new byte[1024];
            StringBuilder messageBuilder = new StringBuilder();

            try {
                logMessage("Starting direct Arduino monitor...");
                while (!Thread.currentThread().isInterrupted() && serialPort.isOpen()) {
                    if (inputStream.available() > 0) {
                        int bytesRead = inputStream.read(buffer);
                        if (bytesRead > 0) {
                            String data = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
                            messageBuilder.append(data);

                            int newlineIndex;
                            while ((newlineIndex = messageBuilder.indexOf("\n")) >= 0) {
                                final String completeMessage = messageBuilder.substring(0, newlineIndex).trim();
                                if (!completeMessage.isEmpty()) {
                                    SwingUtilities.invokeLater(() -> logMessage(completeMessage));
                                }
                                messageBuilder.delete(0, newlineIndex + 1);
                            }
                        }
                    }
                    Thread.sleep(50);
                }
            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> {
                    logMessage("Error reading from serial port: " + e.getMessage());
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                logMessage("Arduino monitor stopped");
            }
        });

        readThread.setDaemon(true);
        readThread.start();
        return readThread;
    }
    @Override
    public byte[] getMessageDelimiter() {
        return new byte[] { (byte)'\n' };
    }

    @Override
    public boolean delimiterIndicatesEndOfMessage() {
        return true;
    }

    @Override
    public void dispose() {
        SerialPort serialPort = arduinoManager.getSelectedPort();
        if (serialPort != null) {
            serialPort.removeDataListener();
        }
        super.dispose();
    }

    @Override
    public ApplicationFrameType getFrameType() {
        return ApplicationFrameType.LOG_VIEWER;
    }

    @Override
    public ScaraGUI getOwner() {
        return owner;
    }
}