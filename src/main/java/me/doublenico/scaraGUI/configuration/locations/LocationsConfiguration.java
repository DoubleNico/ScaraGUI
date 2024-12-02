package me.doublenico.scaraGUI.configuration.locations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import me.doublenico.scaraGUI.configuration.application.ApplicationModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationsConfiguration {

    private final File locationsFile;
    private LocationsModel locationsModel;

    public LocationsConfiguration(File folder) {
        this.locationsFile = new File(folder, "locations.yml");
        if (!folder.exists()) if (!folder.mkdirs()) throw new RuntimeException("Failed to create configuration folder");
        loadConfiguration();
    }

    public void loadConfiguration() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        if (!locationsFile.exists()) {
            locationsModel = new LocationsModel(new ArrayList<>());
            saveConfiguration();
        } else {
            try {
                locationsModel = mapper.readValue(locationsFile, LocationsModel.class);
            } catch (IOException e) {
                e.printStackTrace();
                locationsModel = new LocationsModel(new ArrayList<>());
            }
        }
    }

    public void saveConfiguration() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            mapper.writeValue(locationsFile, locationsModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addApplication(String filePath) {
        System.out.println("Adding application: " + filePath);
        if (!locationsModel.getApplications().contains(filePath)) {
            locationsModel.getApplications().add(filePath);
            saveConfiguration();
        }
    }

    public void removeApplication(String filePath) {
        System.out.println("Removing application: " + filePath);
        locationsModel.getApplications().remove(filePath);
        saveConfiguration();
    }

    public List<String> getApplications() {
        return locationsModel.getApplications();
    }

    public List<File> getValidApplications() {
        List<File> validApps = new ArrayList<>();
        List<File> invalidApps = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        for (String path : locationsModel.getApplications()) {
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                try {
                    mapper.readValue(file, ApplicationModel.class);
                    validApps.add(file);
                } catch (IOException e) {
                    System.err.println("Invalid application configuration: " + file.getAbsolutePath());
                }
            } else
                invalidApps.add(file);
        }
        invalidApps.forEach(e -> removeApplication(e.getAbsolutePath()));
        return validApps;
    }
}