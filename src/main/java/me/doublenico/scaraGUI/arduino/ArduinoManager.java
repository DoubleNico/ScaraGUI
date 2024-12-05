package me.doublenico.scaraGUI.arduino;

import com.fazecast.jSerialComm.SerialPort;
import me.doublenico.scaraGUI.arduino.serial.SerialPortParameters;
import me.doublenico.scaraGUI.arduino.serial.SerialPortTimeouts;
import me.doublenico.scaraGUI.gui.settings.SettingsGui;

import javax.swing.*;

public class ArduinoManager {

    private final SettingsGui parent;
    private JLabel selectedDeviceLabel;
    private final SerialPortParameters serialPortParameters;
    private final SerialPortTimeouts serialPortTimeouts;
    private SerialPort selectedPort;

    public ArduinoManager(SettingsGui parent, JLabel selectedDeviceLabel, SerialPortParameters serialPortParameters, SerialPortTimeouts serialPortTimeouts) {
        this.selectedDeviceLabel = selectedDeviceLabel;
        this.parent = parent;
        this.serialPortParameters = serialPortParameters;
        this.serialPortTimeouts = serialPortTimeouts;
    }

    public SerialPort connectToDevice() {
        if (selectedDeviceLabel == null) {
            JOptionPane.showMessageDialog(parent, "Please select a device first.", "No Device Selected", JOptionPane.WARNING_MESSAGE);
        } else {
            String selectedDevice = selectedDeviceLabel.getText();
            JOptionPane.showMessageDialog(parent, "Connecting to: " + selectedDevice, "Connecting", JOptionPane.INFORMATION_MESSAGE);
            try {
                SerialPort selectedPort = SerialPort.getCommPort(selectedDevice);
                selectedPort.setComPortParameters(serialPortParameters.getBaudRate(), serialPortParameters.getDataBits(), serialPortParameters.getStopBits(), serialPortParameters.getParity());
                selectedPort.setComPortTimeouts(serialPortTimeouts.getTimeoutMode(), serialPortTimeouts.getReadTimeout(), serialPortTimeouts.getWriteTimeout());
                if (!selectedPort.openPort()) throw new Exception();
                JOptionPane.showMessageDialog(parent, "Connected to: " + selectedDevice, "Connected", JOptionPane.INFORMATION_MESSAGE);
                return selectedPort;
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Failed to connect to: " + selectedDevice, "Connection Failed", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return null;
    }

    public void disconnectFromDevice() {
        if (selectedPort != null) {
            selectedPort.closePort();
            JOptionPane.showMessageDialog(parent, "Disconnected from: " + selectedPort.getSystemPortName(), "Disconnected", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(parent, "No device connected.", "No Device Connected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public SerialPort getSelectedPort() {
        return selectedPort;
    }

    public void setSelectedPort(SerialPort selectedPort) {
        this.selectedPort = selectedPort;
    }

    public JLabel getSelectedDeviceLabel() {
        return selectedDeviceLabel;
    }

    public void setSelectedDeviceLabel(JLabel selectedDeviceLabel) {
        this.selectedDeviceLabel = selectedDeviceLabel;
    }
}
