package me.doublenico.scaraGUI.gui.settings.buttons;

import com.fazecast.jSerialComm.SerialPort;
import me.doublenico.scaraGUI.arduino.ArduinoManager;
import me.doublenico.scaraGUI.arduino.serial.SerialPortCallback;
import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;

import javax.swing.*;
import java.awt.*;

public class ConnectButton extends Button {
    public ConnectButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name);
        setFont(new Font("Inter", Font.BOLD, 12));
        setBackground(new Color(0, 204, 0));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setPreferredSize(new Dimension(100, 40));
        setBorder(BorderFactory.createEmptyBorder());
    }

    public void loadEventListener(ArduinoManager arduinoManager, SerialPortCallback callback) {
        addActionListener(e -> {
            SerialPort serialPort = arduinoManager.connectToDevice();
            if (serialPort != null) {
                callback.onSerialPortConnected(serialPort);
            }
        });
    }
}
