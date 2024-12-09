package me.doublenico.scaraGUI.gui.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.SystemInfo;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.configuration.application.ApplicationModel;
import me.doublenico.scaraGUI.configuration.arduino.ArduinoConfiguration;
import me.doublenico.scaraGUI.configuration.locations.LocationsConfiguration;
import me.doublenico.scaraGUI.gui.main.buttons.CreateNewAppButton;
import me.doublenico.scaraGUI.gui.main.buttons.LoadAppButton;
import me.doublenico.scaraGUI.gui.main.buttons.SettingsButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ScaraGUI extends JFrame {

    private JPanel appItemsPanel;
    private final ButtonManager buttonManager;
    private final ArduinoConfiguration arduinoConfiguration;
    private final LocationsConfiguration locationsConfiguration;

    public ScaraGUI() {
        super("ScaraGUI");
        FlatMacDarkLaf.setup();
        if(SystemInfo.isMacFullWindowContentSupported) getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        if (System.getProperty("user.dir") == null) {
            JFileChooser directoryChooser = new JFileChooser();
            directoryChooser.setDialogTitle("Select the directory to save your applications");
            directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = directoryChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = directoryChooser.getCurrentDirectory();
                System.setProperty("user.dir", selectedDirectory.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(this, "No directory selected. Exiting application.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }

        locationsConfiguration = new LocationsConfiguration(new File(System.getProperty("user.dir") + File.separator + "config"));
        arduinoConfiguration = new ArduinoConfiguration(new File(System.getProperty("user.dir") + File.separator + "config"));
        buttonManager = new ButtonManager();

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.setBackground(new Color(22, 22, 23));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setPreferredSize(new Dimension(498, 74));
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("ScaraGUI");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        LoadAppButton loadAppButton = new LoadAppButton(buttonManager, "Load App", ButtonType.LOAD_APP);
        loadAppButton.loadEventListener(this);

        SettingsButton settingsButton = new SettingsButton(buttonManager, "Settings", ButtonType.LOAD_APP);
        settingsButton.loadEventListener(this);

        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(settingsButton);
        topPanel.add(loadAppButton);
        topPanel.add(Box.createRigidArea(new Dimension(15, 0)));

        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(140, 0, 0, 0));
        centerPanel.setPreferredSize(new Dimension(468, 400));
        centerPanel.setOpaque(false);

        CreateNewAppButton createNewAppButton = new CreateNewAppButton(buttonManager, "Create new Scara App", ButtonType.LOAD_APP);
        createNewAppButton.loadEventListener(this, appItemsPanel);

        centerPanel.add(createNewAppButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(498, 254));

        appItemsPanel = new JPanel();
        appItemsPanel.setLayout(new BoxLayout(appItemsPanel, BoxLayout.Y_AXIS));
        appItemsPanel.setOpaque(true);
        appItemsPanel.setBackground(new Color(50, 50, 50));

        JScrollPane scrollPane = new JScrollPane(appItemsPanel);
        scrollPane.setOpaque(true);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBackground(new Color(50, 50, 50));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setBackground(new Color(38, 38, 38, 208));

        bottomPanel.add(scrollPane, BorderLayout.PAGE_START);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
        loadApplications();
        setVisible(true);
    }

    private void loadApplications() {
        String userDir = System.getProperty("user.dir");
        if (userDir == null) return;
        File directory = new File(userDir);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".yml"));

        if (files != null) Arrays.stream(files).forEach(this::loadApplication);

        List<File> locationsFiles = locationsConfiguration.getValidApplications();
        if (locationsFiles == null) return;
        if (locationsFiles.isEmpty()) return;
        locationsFiles.forEach(this::loadApplication);
    }

    private void loadApplication(File file) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            ApplicationModel application = mapper.readValue(file, ApplicationModel.class);
            if (application != null && application.getName() != null) {
                AppItem appItem = new AppItem(application.getName(), file, appItemsPanel, this);
                appItem.setMaximumSize(new Dimension(468, 60));
                appItemsPanel.add(appItem);
            }
        } catch (IOException e) {
            System.err.println("Failed to load application from file: " + file.getName());
        }
    }

    public ArduinoConfiguration getArduinoConfiguration() {
        return arduinoConfiguration;
    }

    public LocationsConfiguration getLocationsConfiguration() {
        return locationsConfiguration;
    }

    public ButtonManager getButtonManager() {
        return buttonManager;
    }
}