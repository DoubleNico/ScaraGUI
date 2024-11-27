package me.doublenico.scaraGUI.configuration.application;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ApplicationModel {

    @JsonProperty("name")
    private String name;

    @JsonProperty("operations")
    private Map<String, OperationModel> operations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, OperationModel> getOperations() {
        return operations;
    }

    public void setOperations(Map<String, OperationModel> operations) {
        this.operations = operations;
    }
}
