package me.doublenico.scaraGUI.configuration.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import me.doublenico.scaraGUI.utils.ExtensionUtils;

import java.io.File;
import java.io.IOException;

public class ApplicationConfiguration {

    private final File folder;
    private final String fileName;

    public ApplicationConfiguration(File folder, String fileName) {
        this.folder = folder;
        this.fileName = fileName;
    }

    public void loadConfiguration() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (!created) {
                System.err.println("Failed to create folder: " + folder.getAbsolutePath());
                return;
            }
        }

        File configFile = new File(folder, fileName);
        if (!configFile.exists()) {
            try {
                ApplicationModel application = new ApplicationModel();
                application.setName(new ExtensionUtils(fileName).getFileNameWithoutExtension());
                application.setOperations(null);

                mapper.writeValue(configFile, application);
                System.out.println("Blank configuration created: " + configFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to create configuration file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            try {
                ApplicationModel application = mapper.readValue(configFile, ApplicationModel.class);
                System.out.println("Configuration loaded: " + application.getName());
            } catch (IOException e) {
                System.err.println("Failed to load configuration file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void saveConfiguration(ApplicationModel applicationModel) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File configFile = new File(folder, fileName);

        try {
            mapper.writeValue(configFile, applicationModel);
            System.out.println("Configuration saved to: " + configFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save configuration file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ApplicationModel loadCurrentApplication() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File configFile = new File(folder, fileName);

        try {
            return mapper.readValue(configFile, ApplicationModel.class);
        } catch (IOException e) {
            System.err.println("Failed to load configuration file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public File getFolder() {
        return folder;
    }

    public String getFileName() {
        return fileName;
    }
}
