package me.doublenico.scaraGUI.gui.settings;

import com.fazecast.jSerialComm.SerialPort;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.SystemInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsGui extends JFrame {

    private JPanel deviceListPanel;
    private JLabel selectedDeviceLabel;

    public SettingsGui() {
        super("Settings");
        FlatMacDarkLaf.setup();
        if (SystemInfo.isMacFullWindowContentSupported) {
            getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        }

        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

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

        JScrollPane scrollPane = createDeviceList();
        centerPanel.add(scrollPane);
        contentPane.add(centerPanel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(22, 22, 23));

        JButton refreshButton = createStyledButton("Refresh", new Color(204, 0, 0));
        JButton connectButton = createStyledButton("Connect", new Color(0, 204, 0));

        refreshButton.addActionListener(e -> refreshDeviceList());
        connectButton.addActionListener(e -> connectToDevice());

        bottomPanel.add(refreshButton);
        bottomPanel.add(connectButton);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        refreshDeviceList();

        setVisible(true);
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

    private void refreshDeviceList() {
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
                if (selectedDeviceLabel != null) {
                    selectedDeviceLabel.setBackground(new Color(50, 50, 50));
                    selectedDeviceLabel.setForeground(Color.WHITE);
                }
                selectedDeviceLabel = label;
                selectedDeviceLabel.setBackground(new Color(0, 122, 204));
                selectedDeviceLabel.setForeground(Color.BLACK);
            }
        });

        return label;
    }

    private void connectToDevice() {
        if (selectedDeviceLabel == null) {
            JOptionPane.showMessageDialog(this, "Please select a device first.", "No Device Selected", JOptionPane.WARNING_MESSAGE);
        } else {
            String selectedDevice = selectedDeviceLabel.getText();
            JOptionPane.showMessageDialog(this, "Connecting to: " + selectedDevice, "Connecting", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Inter", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 40));
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }
}
