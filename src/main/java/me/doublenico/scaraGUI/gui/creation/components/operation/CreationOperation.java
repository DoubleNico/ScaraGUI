package me.doublenico.scaraGUI.gui.creation.components.operation;

import com.fazecast.jSerialComm.SerialPort;
import me.doublenico.scaraGUI.configuration.application.ApplicationConfiguration;
import me.doublenico.scaraGUI.configuration.application.ApplicationModel;
import me.doublenico.scaraGUI.configuration.application.OperationModel;
import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;
import me.doublenico.scaraGUI.gui.creation.components.form.CreationLabel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

public class CreationOperation extends JPanel {

    private OperationItem selectedOperation;
    private final int DEFAULT_VALUE = -999999;
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
                    if (operation.getJoint1() != DEFAULT_VALUE) textField.setText(String.valueOf(operation.getJoint1()));
                    else textField.setText(label.fieldName);
                    break;
                }
                case JOINT2: {
                    if (operation.getJoint2() != DEFAULT_VALUE) textField.setText(String.valueOf(operation.getJoint2()));
                    else textField.setText(label.fieldName);
                    break;
                }
                case JOINT3: {
                    if (operation.getJoint3() != DEFAULT_VALUE) textField.setText(String.valueOf(operation.getJoint3()));
                    else textField.setText(label.fieldName);
                    break;
                }
                case Z: {
                    if (operation.getZ() != DEFAULT_VALUE) textField.setText(String.valueOf(operation.getZ()));
                    else textField.setText(label.fieldName);
                    break;
                }
                case GRIPPER: {
                    if (operation.getGripper() != DEFAULT_VALUE) textField.setText(String.valueOf(operation.getGripper()));
                    else textField.setText(label.fieldName);
                    break;
                }
                case SPEED: {
                    if (operation.getSpeed() != DEFAULT_VALUE) textField.setText(String.valueOf(operation.getSpeed()));
                    else textField.setText(label.fieldName);
                    break;
                }
                case ACCELERATION: {
                    if (operation.getAcceleration() != DEFAULT_VALUE) textField.setText(String.valueOf(operation.getAcceleration()));
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
            Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.JOINT3).getText()),
            Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.Z).getText()),
            Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.GRIPPER).getText()),
            Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.SPEED).getText()),
            Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.ACCELERATION).getText())
            );
        System.out.println("Saving operation: " + operation);
        operationsHandler.removeOperationItem(selectedOperation.getOperation().getName());
        if (!selectedOperation.getOperation().getName().equals(operation.getName())) {
            int index = operations.indexOf(selectedOperation);
            operations.remove(selectedOperation);
            OperationItem newOperation = new OperationItem(operation.getName(), operation, parent);
            newOperation.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            newOperation.setMaximumSize(new Dimension(230, 58));
            operations.add(index, newOperation);
            remove(selectedOperation);
            loadOperation(newOperation);
            updateOperationsPanel();
            revalidate();
            repaint();
        }
        hasSaved = true;
        OperationModel operationModel = new OperationModel(
            operations.indexOf(selectedOperation),
            operation.getName(),
            operation.getJoint1(),
            operation.getJoint2(),
            operation.getJoint3(),
            operation.getZ(),
            operation.getGripper(),
            operation.getSpeed(),
            operation.getAcceleration()
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
        OperationItem operation = new OperationItem(name,new Operation(UUID.randomUUID(), name, DEFAULT_VALUE,DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE), parent);
        operationsHandler.addOperationItem(name, operation);
        addOperationItem(operation);
    }

    public void runOperation() {
        if (selectedOperation == null) {
            JOptionPane.showMessageDialog(parent,
                "No operation selected",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        runOperation(selectedOperation);
    }

    public void runOperation(OperationItem operationItem) {
        if (operationItem == selectedOperation) {
            if (parent.getFormPanel().validateForm()) {
                Operation updatedOperation = new Operation(
                    operationItem.getOperation().getUuid(),
                    parent.getFormPanel().getTextFields().get(CreationLabel.NAME).getText(),
                    Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.JOINT1).getText()),
                    Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.JOINT2).getText()),
                    Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.JOINT3).getText()),
                    Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.Z).getText()),
                    Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.GRIPPER).getText()),
                    Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.SPEED).getText()),
                    Integer.parseInt(parent.getFormPanel().getTextFields().get(CreationLabel.ACCELERATION).getText())
                );

                operationItem = new OperationItem(updatedOperation.getName(), updatedOperation, parent);
                System.out.println("Using updated values: " + updatedOperation);
            } else return;
        }

        SerialPort port = parent.getOwner().getArduinoManager().getSelectedPort();
        if (port == null || !port.isOpen()) {
            JOptionPane.showMessageDialog(parent,
                "No Arduino connected or port is not open",
                "Connection Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        Operation op = operationItem.getOperation();

        try {
            StringBuilder command = new StringBuilder("CMD:");

            command.append(op.getJoint1() != DEFAULT_VALUE ? op.getJoint1() : 0).append(",");
            command.append(op.getJoint2() != DEFAULT_VALUE ? op.getJoint2() : 0).append(",");
            command.append(op.getJoint3() != DEFAULT_VALUE ? op.getJoint3() : 0).append(",");
            command.append(op.getZ() != DEFAULT_VALUE ? op.getZ() : 100).append(",");
            command.append(op.getGripper() != DEFAULT_VALUE ? op.getGripper() : 180).append(",");
            command.append(op.getSpeed() != DEFAULT_VALUE ? op.getSpeed() : 2000).append(",");
            command.append(op.getAcceleration() != DEFAULT_VALUE ? op.getAcceleration() : 1000);
            command.append("\n");

            System.out.println("Sending command: " + command);
            OutputStream outputStream = port.getOutputStream();
            outputStream.write(command.toString().getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent,
                "Failed to send command to Arduino: " + ex.getMessage(),
                "Communication Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    public void runApplication() {
        if (parent.getOwner().getArduinoManager().getSelectedPort() == null || !parent.getOwner().getArduinoManager().isOpened()) {
            JOptionPane.showMessageDialog(parent,
                "No Arduino connected or port is not open.\nPlease connect to an Arduino first.",
                "Connection Required",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (operations.isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                "No operations to run.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        ArrayList<OperationItem> sortedOperations = new ArrayList<>(operations);
        sortedOperations.sort(Comparator.comparingInt(operations::indexOf));

        JDialog progressDialog = new JDialog(parent, "Running Application", true);
        progressDialog.setSize(300, 150);
        progressDialog.setLocationRelativeTo(parent);
        progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel progressPanel = new JPanel(new BorderLayout(10, 10));
        progressPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        progressPanel.setBackground(new Color(22, 22, 23));

        JLabel statusLabel = new JLabel("Running operations...", SwingConstants.CENTER);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Inter", Font.BOLD, 14));

        JProgressBar progressBar = new JProgressBar(0, sortedOperations.size());
        progressBar.setForeground(new Color(0, 122, 204));
        progressBar.setBackground(new Color(50, 50, 50));
        progressBar.setStringPainted(true);

        progressPanel.add(statusLabel, BorderLayout.NORTH);
        progressPanel.add(progressBar, BorderLayout.CENTER);

        progressDialog.setContentPane(progressPanel);

        Thread runThread = new Thread(() -> {
            try {
                for (int i = 0; i < sortedOperations.size(); i++) {
                    OperationItem item = sortedOperations.get(i);
                    final int currentIndex = i;

                    SwingUtilities.invokeLater(() -> {
                        progressBar.setValue(currentIndex + 1);
                        statusLabel.setText("Running: " + item.getOperation().getName() + " (" + (currentIndex + 1) + "/" + sortedOperations.size() + ")");
                    });

                    runOperation(item);

                    Thread.sleep(3500);
                }

                SwingUtilities.invokeLater(() -> {
                    progressDialog.dispose();
                    JOptionPane.showMessageDialog(parent,
                        "Application execution completed successfully.",
                        "Execution Complete",
                        JOptionPane.INFORMATION_MESSAGE);
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    progressDialog.dispose();
                    JOptionPane.showMessageDialog(parent,
                        "Error running application: " + e.getMessage(),
                        "Execution Error",
                        JOptionPane.ERROR_MESSAGE);
                });
            }
        });

        runThread.start();
        progressDialog.setVisible(true);
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

