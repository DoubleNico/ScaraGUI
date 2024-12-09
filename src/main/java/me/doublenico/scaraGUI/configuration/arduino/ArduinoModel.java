package me.doublenico.scaraGUI.configuration.arduino;

public class ArduinoModel {

    private String serialPort;

    public ArduinoModel() {}

    public ArduinoModel(String serialPort) {
        this.serialPort = serialPort;
    }

    public String getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(String serialPort) {
        this.serialPort = serialPort;
    }
}
