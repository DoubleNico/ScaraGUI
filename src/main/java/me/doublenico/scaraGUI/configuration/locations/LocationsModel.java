package me.doublenico.scaraGUI.configuration.locations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LocationsModel {

    @JsonProperty("applications")
    private List<String> applications;

    public LocationsModel() {}

    public LocationsModel(List<String> applications) {
        this.applications = applications;
    }

    public List<String> getApplications() {
        return applications;
    }

    public void setApplications(List<String> applications) {
        this.applications = applications;
    }
}