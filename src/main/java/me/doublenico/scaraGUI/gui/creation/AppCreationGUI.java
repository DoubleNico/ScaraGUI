package me.doublenico.scaraGUI.gui.creation;

import com.formdev.flatlaf.extras.FlatDesktop;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.SystemInfo;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.configuration.application.ApplicationConfiguration;
import me.doublenico.scaraGUI.configuration.application.ApplicationModel;
import me.doublenico.scaraGUI.configuration.application.OperationModel;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.frame.ApplicationFrameType;
import me.doublenico.scaraGUI.gui.creation.buttons.*;
import me.doublenico.scaraGUI.gui.creation.components.operation.Operation;
import me.doublenico.scaraGUI.gui.creation.components.operation.OperationItem;
import me.doublenico.scaraGUI.gui.creation.components.operation.OperationsHandler;
import me.doublenico.scaraGUI.gui.creation.components.form.CreationForm;
import me.doublenico.scaraGUI.gui.creation.components.operation.CreationOperation;
import me.doublenico.scaraGUI.gui.creation.components.sidebar.CreationSidebar;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class AppCreationGUI extends ApplicationFrame {

    private final CreationOperation operationsPanel;
    private final CreationForm formPanel;
    private final ApplicationConfiguration configuration;
    private final ScaraGUI owner;

    public AppCreationGUI(ApplicationConfiguration configuration, ScaraGUI owner) {
        super("Editing " + configuration.getFileName());
        this.owner = owner;
        this.configuration = configuration;
        FlatMacDarkLaf.setup();

        if(SystemInfo.isMacFullWindowContentSupported) getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        getRootPane().putClientProperty("apple.awt.fullscreenable", true);

        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(954, 600);
        setLocationRelativeTo(null);

        OperationsHandler operationsHandler = new OperationsHandler();
        formPanel = new CreationForm(this);
        operationsPanel = new CreationOperation(this, operationsHandler);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(22, 22, 23));

        CreationSidebar sideBar = new CreationSidebar(this, operationsPanel);
        contentPane.add(sideBar, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(22, 22, 23));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(new Color(22, 22, 23));

        SidebarButton openSidebarButton = new SidebarButton(owner.getButtonManager(), ">", ButtonType.SIDEBAR_BUTTON, this);
        openSidebarButton.loadEventListener(sideBar, contentPane);

        SettingsButton settingsButton = new SettingsButton(owner.getButtonManager(), "Settings", ButtonType.SETTINGS_BUTTON, this);
        settingsButton.loadEventListener(owner);

        DeleteButton deleteButton = new DeleteButton(owner.getButtonManager(), "Delete", ButtonType.DELETE_BUTTON, this);
        deleteButton.loadEventListener();

        SaveButton saveButton = new SaveButton(owner.getButtonManager(), "Save", ButtonType.SAVE_BUTTON, this);
        saveButton.loadEventListener(operationsPanel);

        RunButton runButton = new RunButton(owner.getButtonManager(), "Run", ButtonType.RUN_BUTTON, this);
        runButton.loadEventListener(operationsPanel);

        openSidebarButton.setVisible(false);

        sideBar.getCloseButton().loadEventListener(sideBar, openSidebarButton, contentPane);

        buttonPanel.add(settingsButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(runButton);
        buttonPanel.add(openSidebarButton);
        rightPanel.add(buttonPanel, BorderLayout.NORTH);

        rightPanel.add(formPanel, BorderLayout.CENTER);

        contentPane.add(rightPanel, BorderLayout.CENTER);
        ApplicationModel applicationModel = configuration.loadCurrentApplication();
        if (applicationModel != null && applicationModel.getOperations() != null && !applicationModel.getOperations().isEmpty()) {
            Map<String, OperationModel> sortedOperations = new TreeMap<>(Comparator.comparingInt(o -> applicationModel.getOperations().get(o).getPosition()));
            sortedOperations.putAll(applicationModel.getOperations());

            for (Map.Entry<String, OperationModel> entry : sortedOperations.entrySet()) {
                Operation operation = new Operation(
                    UUID.fromString(entry.getKey()),
                    entry.getValue().getName(),
                    entry.getValue().getJoint1(),
                    entry.getValue().getJoint2(),
                    entry.getValue().getJoint3(),
                    entry.getValue().getZ(),
                    entry.getValue().getGripper(),
                    entry.getValue().getSpeed(),
                    entry.getValue().getAcceleration()
                );
                OperationItem operationItem = new OperationItem(entry.getValue().getName(), operation, this);
                operationsPanel.addOperation(operationItem);
            }
        } else {
            operationsPanel.addOperation("Default");
        }
        operationsPanel.loadOperation(operationsPanel.getOperations().get(0));
        setContentPane(contentPane);
        setVisible(true);

        FlatDesktop.setQuitHandler(response -> {
            if (!operationsPanel.hasSaved()) {
                int option = JOptionPane.showConfirmDialog(this, "You have not saved your progress. Are you sure you want to quit?", "Quit ScaraGUI", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) response.performQuit();
                else response.cancelQuit();
                return;
            }
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit ScaraGUI", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) response.performQuit();
            else response.cancelQuit();
        });
    }

    public ApplicationConfiguration getConfiguration() {
        return configuration;
    }

    public CreationOperation getOperationsPanel() {
        return operationsPanel;
    }

    public CreationForm getFormPanel() {
        return formPanel;
    }

    @Override
    public ScaraGUI getOwner() {
        return owner;
    }

    @Override
    public ApplicationFrameType getFrameType() {
        return ApplicationFrameType.APP_CREATION;
    }
}
