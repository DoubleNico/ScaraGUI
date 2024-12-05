package me.doublenico.scaraGUI.arduino.serial;

public class SerialPortTimeouts {

    private int timeoutMode;
    private int readTimeout;
    private int writeTimeout;

    public SerialPortTimeouts(int timeoutMode, int readTimeout, int writeTimeout) {
        this.timeoutMode = timeoutMode;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
    }

    public int getTimeoutMode() {
        return timeoutMode;
    }

    public void setTimeoutMode(int timeoutMode) {
        this.timeoutMode = timeoutMode;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }
}
