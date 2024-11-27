package me.doublenico.scaraGUI.gui.creation.components.operation;

import me.doublenico.scaraGUI.configuration.application.ApplicationConfiguration;
import me.doublenico.scaraGUI.configuration.application.ApplicationModel;
import me.doublenico.scaraGUI.configuration.application.OperationModel;
import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;
import me.doublenico.scaraGUI.gui.creation.components.form.CreationLabel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class CreationOperation extends JPanel {

    private OperationItem selectedOperation;
    private boolean hasSaved = false;
    private final AppCreationGUI parent;
    private final OperationsHandler operationsHandler;
    private final List<OperationItem> operations;

    public CreationOperation(AppCreationGUI parent, OperationsHandler operationsHandler) {
        this.parent = parent;
        this.operationsHandler = operationsHandler;
        operations = new ArrayList<>();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
    }

    public void loadOperation(OperationItem operationItem) {
        Operation operation = operationItem.getOperation();
        for (CreationLabel label : CreationLabel.values()) {
            JTextField textField = parent.getFormPanel().getTextFields().get(label);
            switch (label) {
                case NAME: {
                    textField.setText(operation.getName());
                    break;
                }
                case JOINT1: {
                    if (operation.getJoint1() != 0) textField.setText(String.valueOf(operation.getJoint1()));
                    else textField.setText(label.fieldName);
                    break;
                }
                case JOINT2: {
                    if (operation.getJoint2() != 0) textField.setText(String.valueOf(operation.getJoint2()));
                    else textField.setText(label.fieldName);
                    break;
                }
                case Z: {
                    if (operation.getZ() != 0) textField.setText(String.valueOf(operation.getZ()));
                    else textField.setText(label.fieldName);
                    break;
                }
                case GRIPPER: {
                    if (operation.getGripper() != 0) textField.setText(String.valueOf(operation.getGripper()));
                    else textField.setText(label.fieldName);
                    break;
                }
                case SPEED: {
                    if (operation.getSpeed() != 0) textField.setText(String.valueOf(operation.getSpeed()));
                    else textField.setText(label.fieldName);
                    break;
                }
            }
        }
        operationsHandler.addOperationItem(operationItem.getOperation().getName(), operationItem);
        selectedOperation = operationItem;
        selectedOperation.setBorder(BorderFactory.createLineBorder(new Color(0, 122, 204), 2));
        hasSaved = true;
    }

    public void saveOperation() {
        if (operationsHandler == null) return;
        if (selectedOperation == null) return;
        if (!parent.getFormPanel().validateForm()) return;
        Operation operation = new Operation(
            selectedOperation.getOperation().getUuid(),
            parent.getFormPanel().getTextFields().get(CreationLabel.NAME).getText(),
            Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.JOINT1).getText()),
            Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.JOINT2).getText()),
            Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.Z).getText()),
            Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.GRIPPER).getText()),
            Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.SPEED).getText())
        );
        operationsHandler.removeOperationItem(selectedOperation.getOperation().getName());
        if (!selectedOperation.getOperation().getName().equals(operation.getName())) {
            operations.remove(selectedOperation);
            OperationItem newOperation = new OperationItem(operation.getName(), operation, parent);
            newOperation.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            newOperation.setMaximumSize(new Dimension(230, 58));
            operations.add(newOperation);
            updateOperationsPanel();
            revalidate();
            repaint();
        }
        selectedOperation.setOperation(operation);
        operationsHandler.addOperationItem(operation.getName(), selectedOperation);
        hasSaved = true;
        OperationModel operationModel = new OperationModel(
            operations.indexOf(selectedOperation),
            operation.getName(),
            operation.getJoint1(),
            operation.getJoint2(),
            operation.getZ(),
            operation.getGripper(),
            operation.getSpeed()
        );
        ApplicationConfiguration configuration = parent.getConfiguration();
        ApplicationModel applicationModel = configuration.loadCurrentApplication();
        if (applicationModel != null) {
            if (applicationModel.getOperations() == null) applicationModel.setOperations(new HashMap<>());

            applicationModel.getOperations().put(operation.getUuid().toString(), operationModel);
            configuration.saveConfiguration(applicationModel);
        } else System.err.println("Application model could not be loaded.");
    }

    public void addOperation(String name) {
        if (operationsHandler == null) return;
        OperationItem operation = new OperationItem(name,new Operation(UUID.randomUUID(), name, 0,0, 0, 0, 0), parent);
        operationsHandler.addOperationItem(name, operation);
        addOperationItem(operation);
    }

    public void addOperation(OperationItem operationItem) {
        addOperationItem(operationItem);
    }

    private void addOperationItem(OperationItem operationItem) {
        operations.add(operationItem);
        operationItem.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        operationItem.setMaximumSize(new Dimension(230, 58));
        add(operationItem);
        add(Box.createVerticalStrut(5));
        updateOperationStates();
        revalidate();
        repaint();
    }

    public void moveOperationUp(OperationItem item) {
        int index = operations.indexOf(item);
        if (index > 0) {
            operations.remove(index);
            operations.add(index - 1, item);
            updateOperationsPanel();
            updateOperationPositions();
        }
    }

    public void moveOperationDown(OperationItem item) {
        int index = operations.indexOf(item);
        if (index < operations.size() - 1) {
            operations.remove(index);
            operations.add(index + 1, item);
            updateOperationsPanel();
            updateOperationPositions();
        }
    }
    public void deleteOperation(OperationItem item) {
        operationsHandler.removeOperationItem(item.getOperation().getName());
        operations.remove(item);
        remove(item);

        ApplicationConfiguration configuration = parent.getConfiguration();
        ApplicationModel applicationModel = configuration.loadCurrentApplication();

        if (applicationModel != null) {
            Map<String, OperationModel> operationsMap = applicationModel.getOperations();
            if (operationsMap != null) {
                operationsMap.remove(item.getOperation().getUuid().toString());
            }

            configuration.saveConfiguration(applicationModel);
        } else {
            System.err.println("Application model could not be loaded.");
        }

        updateOperationStates();
        revalidate();
        repaint();
    }


    private void updateOperationStates() {
        for (int i = 0; i < operations.size(); i++) {
            OperationItem item = operations.get(i);
            item.setMoveUpEnabled(i > 0);
            item.setMoveDownEnabled(i < operations.size() - 1);
            item.setDeleteEnabled(operations.size() > 1);
        }
    }

    private void updateOperationsPanel() {
        removeAll();
        for (OperationItem item : operations) {
            add(item);
        }
        updateOperationStates();
        revalidate();
        repaint();
    }

    private void updateOperationPositions() {
        ApplicationConfiguration configuration = parent.getConfiguration();
        ApplicationModel applicationModel = configuration.loadCurrentApplication();
        if (applicationModel != null) {
            Map<String, OperationModel> operationsMap = applicationModel.getOperations();
            if (operationsMap != null) {
                for (int i = 0; i < operations.size(); i++) {
                    OperationItem item = operations.get(i);
                    OperationModel operationModel = operationsMap.get(item.getOperation().getUuid().toString());
                    if (operationModel != null) {
                        operationModel.setPosition(i);
                    }
                }
                configuration.saveConfiguration(applicationModel);
            }
        }
    }


    public List<OperationItem> getOperations() {
        return operations;
    }

    public OperationItem getSelectedOperation() {
        return selectedOperation;
    }

    public boolean hasSaved() {
        return hasSaved;
    }

    public void setSelectedOperation(OperationItem selectedOperation) {
        this.selectedOperation = selectedOperation;
    }

    public void setHasSaved(boolean hasSaved) {
        this.hasSaved = hasSaved;
    }

}

