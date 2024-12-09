package me.doublenico.scaraGUI.configuration.arduino;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class ArduinoConfiguration {

    private final File arduinoFile;
    private ArduinoModel arduinoModel;

    public ArduinoConfiguration(File folder) {
        this.arduinoFile = new File(folder, "arduino.yml");
        if (!folder.exists()) if (!folder.mkdirs()) throw new RuntimeException("Failed to create configuration folder");
        loadConfiguration();
    }

    public void loadConfiguration() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        if (!arduinoFile.exists()) {
            arduinoModel = new ArduinoModel("");
            saveConfiguration();
        } else {
            try {
                arduinoModel = mapper.readValue(arduinoFile, ArduinoModel.class);
            } catch (IOException e) {
                e.printStackTrace();
                arduinoModel = new ArduinoModel("");
            }
        }
    }

    public void saveConfiguration() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            mapper.writeValue(arduinoFile, arduinoModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addArduinoSerialPort(String serialPort) {
        System.out.println("Adding arduino serial: " + serialPort);
        arduinoModel.setSerialPort(serialPort);
        saveConfiguration();
    }

    public void removeArduinoSerialPort() {
        System.out.println("Removing arduino serial");
        arduinoModel.setSerialPort("");
        saveConfiguration();
    }
    public String getSerialPort() {
        return arduinoModel.getSerialPort();
    }
}
