package me.doublenico.scaraGUI.arduino.serial;

import com.fazecast.jSerialComm.SerialPort;

@FunctionalInterface
public interface SerialPortCallback {
    void onSerialPortConnected(SerialPort serialPort);
}
