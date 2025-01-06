package me.doublenico.scaraGUI.gui.settings;

import com.fazecast.jSerialComm.SerialPort;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.SystemInfo;
import me.doublenico.scaraGUI.arduino.ArduinoManager;
import me.doublenico.scaraGUI.arduino.serial.SerialPortParameters;
import me.doublenico.scaraGUI.arduino.serial.SerialPortTimeouts;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.frame.ApplicationFrameType;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;
import me.doublenico.scaraGUI.gui.settings.buttons.ConnectButton;
import me.doublenico.scaraGUI.gui.settings.buttons.DisconnectButton;
import me.doublenico.scaraGUI.gui.settings.buttons.RefreshButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsGui extends ApplicationFrame {

    private final ArduinoManager arduinoManager;
    private final JPanel bottomPanel;
    private JScrollPane scrollPane;
    private JPanel deviceListPanel;
    private JLabel selectedDeviceLabel;

    public SettingsGui(ScaraGUI owner) {
        super("Settings");

        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        SerialPortParameters serialPortParameters = new SerialPortParameters(115200, 8, 1, 0);
        SerialPortTimeouts serialPortTimeouts = new SerialPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
        arduinoManager = new ArduinoManager(this, selectedDeviceLabel, serialPortParameters, serialPortTimeouts);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(22, 22, 23));
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Settings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Inter", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        scrollPane = createDeviceList();
        centerPanel.add(scrollPane);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(22, 22, 23));

        RefreshButton refreshButton = new RefreshButton(owner.getButtonManager(), "Refresh", ButtonType.REFRESH_BUTTON, this);
        refreshButton.loadEventListener(this);
        ConnectButton connectButton = new ConnectButton(owner.getButtonManager(), "Connect", ButtonType.CONNECT_BUTTON, this);
        connectButton.loadEventListener(arduinoManager, serialPort -> {
            arduinoManager.setSelectedPort(serialPort);
            updateButtons(bottomPanel, owner);
        });

        bottomPanel.add(refreshButton);
        bottomPanel.add(connectButton);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        refreshDeviceList();
        updateButtons(bottomPanel, owner);

        setVisible(true);
    }

    public SettingsGui(ScaraGUI owner, SerialPort serialPort){
        this(owner);
        arduinoManager.setSelectedPort(serialPort);
        updateButtons(bottomPanel, owner);
        refreshDeviceList();
    }

    private JScrollPane createDeviceList() {
        deviceListPanel = new JPanel();
        deviceListPanel.setLayout(new BoxLayout(deviceListPanel, BoxLayout.Y_AXIS));
        deviceListPanel.setBackground(new Color(38, 38, 38));
        deviceListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(deviceListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(new Color(38, 38, 38));
        scrollPane.getViewport().setBackground(new Color(38, 38, 38));
        scrollPane.setMaximumSize(new Dimension(400, 400));

        return scrollPane;
    }

    public void refreshDeviceList() {
        deviceListPanel.removeAll();

        SerialPort[] portNames = SerialPort.getCommPorts();
        if (portNames.length == 0) {
            JLabel noDevicesLabel = new JLabel("No devices found", SwingConstants.CENTER);
            noDevicesLabel.setFont(new Font("Inter", Font.BOLD, 16));
            noDevicesLabel.setForeground(Color.WHITE);
            noDevicesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            deviceListPanel.add(noDevicesLabel);
        } else {
            for (SerialPort portName : portNames) {
                JLabel deviceLabel = createDeviceLabel(portName.getSystemPortName());
                deviceListPanel.add(deviceLabel);
                deviceListPanel.add(Box.createVerticalStrut(10));
            }
        }

        deviceListPanel.revalidate();
        deviceListPanel.repaint();
    }

    private JLabel createDeviceLabel(String deviceName) {
        JLabel label = new JLabel(deviceName, SwingConstants.CENTER);
        label.setFont(new Font("Inter", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(50, 50, 50));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setPreferredSize(new Dimension(300, 40));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (arduinoManager.getSelectedPort() != null && !arduinoManager.getSelectedPort().getSystemPortName().equals(deviceName)) {
                    JOptionPane.showMessageDialog(SettingsGui.this, "Please disconnect from the current device first.", "Device Already Connected", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (selectedDeviceLabel != null) {
                    selectedDeviceLabel.setBackground(new Color(50, 50, 50));
                    selectedDeviceLabel.setForeground(Color.WHITE);
                }
                selectedDeviceLabel = label;
                arduinoManager.setSelectedDeviceLabel(selectedDeviceLabel);
                selectedDeviceLabel.setBackground(new Color(0, 122, 204));
                selectedDeviceLabel.setForeground(Color.BLACK);
            }
        });

        if (arduinoManager.getSelectedPort() != null && arduinoManager.getSelectedPort().getSystemPortName().equals(deviceName)) {
            System.out.println("Selected device: " + deviceName);
            selectedDeviceLabel = label;
            selectedDeviceLabel.setBackground(new Color(0, 122, 204));
            selectedDeviceLabel.setForeground(Color.BLACK);
        }

        return label;
    }

    private void updateButtons(JPanel bottomPanel, ScaraGUI owner) {
        bottomPanel.removeAll();

        RefreshButton refreshButton = new RefreshButton(owner.getButtonManager(), "Refresh", ButtonType.REFRESH_BUTTON, this);
        refreshButton.loadEventListener(this);
        bottomPanel.add(refreshButton);

        if (arduinoManager.getSelectedPort() != null) {
            DisconnectButton disconnectButton = new DisconnectButton(owner.getButtonManager(), "Disconnect", ButtonType.DISCONNECT_BUTTON, this);
            disconnectButton.loadEventListener(arduinoManager, serialPort -> {
                arduinoManager.disconnectFromDevice();
                owner.getArduinoConfiguration().removeArduinoSerialPort();
                updateButtons(bottomPanel, owner);
            });
            bottomPanel.add(disconnectButton);
        } else {
            ConnectButton connectButton = new ConnectButton(owner.getButtonManager(), "Connect", ButtonType.CONNECT_BUTTON, this);
            connectButton.loadEventListener(arduinoManager, serialPort -> {
                arduinoManager.setSelectedPort(serialPort);
                owner.getArduinoConfiguration().addArduinoSerialPort(serialPort.getSystemPortName());
                updateButtons(bottomPanel, owner);
            });
            bottomPanel.add(connectButton);
        }

        bottomPanel.revalidate();
        bottomPanel.repaint();
    }

    @Override
    public ApplicationFrameType getFrameType() {
        return ApplicationFrameType.SETTINGS;
    }
}