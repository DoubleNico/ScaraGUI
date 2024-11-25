package me.doublenico.scaraGUI.gui.creation.components.operation;

import java.util.HashMap;

public class OperationsHandler {

    private final HashMap<String, OperationItem> operationItem;

    public OperationsHandler() {
        operationItem = new HashMap<>();
    }

    public void addOperationItem(String name, OperationItem operationItem) {
        this.operationItem.put(name, operationItem);
    }

    public OperationItem getOperationItem(String name) {
        return operationItem.get(name);
    }

    public void removeOperationItem(String name) {
        operationItem.remove(name);
    }

    public HashMap<String, OperationItem> getOperationItems() {
        return operationItem;
    }
}
