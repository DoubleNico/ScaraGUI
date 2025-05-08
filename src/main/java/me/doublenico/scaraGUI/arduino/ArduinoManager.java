package me.doublenico.scaraGUI.arduino;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortInvalidPortException;
import me.doublenico.scaraGUI.arduino.serial.SerialPortParameters;
import me.doublenico.scaraGUI.arduino.serial.SerialPortTimeouts;
import me.doublenico.scaraGUI.gui.settings.SettingsGui;

import javax.swing.*;

public class ArduinoManager {

    private SettingsGui parent;
    private JLabel selectedDeviceLabel;
    private final SerialPortParameters serialPortParameters = new SerialPortParameters(115200, 8, 1, 0);
    private final SerialPortTimeouts serialPortTimeouts = new SerialPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
    private SerialPort selectedPort;
    private boolean isOpened = false;

    public ArduinoManager(SettingsGui parent, JLabel selectedDeviceLabel) {
        this.selectedDeviceLabel = selectedDeviceLabel;
        this.parent = parent;
    }

    public ArduinoManager(String port){
        try {
            SerialPort selectedPort = SerialPort.getCommPort(port);
            selectedPort.setComPortParameters(serialPortParameters.getBaudRate(), serialPortParameters.getDataBits(), serialPortParameters.getStopBits(), serialPortParameters.getParity());
            selectedPort.setComPortTimeouts(serialPortTimeouts.getTimeoutMode(), serialPortTimeouts.getReadTimeout(), serialPortTimeouts.getWriteTimeout());
            if (selectedPort.openPort()) {
                System.out.println("This " + selectedPort.getSystemPortName() + " is opened.");
                this.selectedPort = selectedPort;
                this.isOpened = true;
            } else {this.selectedPort = null;
                System.out.println(port + " is not opened");}
        } catch (SerialPortInvalidPortException ignored) {}
    }

    public SerialPort connectToDevice() {
        if (isOpened) return this.selectedPort;
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
                this.selectedPort = selectedPort;
                this.isOpened = true;
                return selectedPort;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Failed to connect to: " + selectedDevice, "Connection Failed", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return null;
    }

    public void disconnectFromDevice() {
        if (selectedPort != null) {
            String portName = selectedPort.getSystemPortName();
            selectedPort.closePort();
            JOptionPane.showMessageDialog(parent, "Disconnected from: " + portName, "Disconnected", JOptionPane.INFORMATION_MESSAGE);
            isOpened = false;
            selectedPort = null;
            selectedDeviceLabel = null;
        } else {
            JOptionPane.showMessageDialog(parent, "No device connected.", "No Device Connected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public SerialPort getSelectedPort() {
        return selectedPort;
    }

    public SerialPortParameters getSerialPortParameters() {
        return serialPortParameters;
    }

    public SerialPortTimeouts getSerialPortTimeouts() {
        return serialPortTimeouts;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
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