package me.doublenico.scaraGUI.utils;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortInvalidPortException;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;
import me.doublenico.scaraGUI.gui.settings.SettingsGui;

import javax.swing.*;

public class SettingsActionListenerUtils {

    private final JButton settingsButton;

    public SettingsActionListenerUtils(JButton settingsButton){
        this.settingsButton = settingsButton;
    }

    public void loadEventListener(ScaraGUI owner){
        settingsButton.addActionListener(e -> {
            String serialPort = owner.getArduinoConfiguration().getSerialPort();
            if (serialPort != null && !serialPort.isEmpty()) {
                try {
                    SerialPort port = SerialPort.getCommPort(serialPort);
                    new SettingsGui(owner, port).setVisible(true);
                } catch (SerialPortInvalidPortException ignored){
                    new SettingsGui(owner).setVisible(true);
                }
            } else new SettingsGui(owner).setVisible(true);
        });
    }

    public JButton getSettingsButton() {
        return settingsButton;
    }
}
