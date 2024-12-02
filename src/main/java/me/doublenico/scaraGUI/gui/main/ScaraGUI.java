package me.doublenico.scaraGUI.gui.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.SystemInfo;
import me.doublenico.scaraGUI.configuration.application.ApplicationConfiguration;
import me.doublenico.scaraGUI.configuration.application.ApplicationModel;
import me.doublenico.scaraGUI.configuration.locations.LocationsConfiguration;
import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;
import me.doublenico.scaraGUI.gui.settings.SettingsGui;
import me.doublenico.scaraGUI.utils.FileUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ScaraGUI extends JFrame {

    private JPanel appItemsPanel;
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

        JButton loadAppButton = new JButton("Load App");
        loadAppButton.setBackground(new Color(9, 125, 201));
        loadAppButton.setForeground(Color.WHITE);
        loadAppButton.setFont(new Font("Inter", Font.BOLD, 12));
        loadAppButton.setFocusPainted(false);
        loadAppButton.setOpaque(true);
        loadAppButton.setPreferredSize(new Dimension(105, 33));
        loadAppButton.setMinimumSize(new Dimension(105, 33));
        loadAppButton.setMaximumSize(new Dimension(105, 33));

        loadAppButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select an application file");
            String userDir = System.getProperty("user.dir");
            if (userDir != null) fileChooser.setCurrentDirectory(new File(userDir));
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".yml");
                }

                @Override
                public String getDescription() {
                    return "YAML Files (*.yml)";
                }
            });

            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

                if (selectedFile == null) {
                    JOptionPane.showMessageDialog(this, "No file selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (selectedFile.exists() && selectedFile.isDirectory()) {
                    JOptionPane.showMessageDialog(this, "No file selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (new FileUtils().handleSelectedFile(selectedFile))
                    locationsConfiguration.addApplication(selectedFile.getAbsolutePath());

                try {
                    ApplicationModel application = mapper.readValue(selectedFile, ApplicationModel.class);
                    if (application != null && application.getName() != null) {
                        ApplicationConfiguration configuration = new ApplicationConfiguration(selectedFile.getParentFile(), selectedFile.getName());
                        new AppCreationGUI(configuration).setVisible(true);
                    } else JOptionPane.showMessageDialog(this, "Invalid application file.", "Error", JOptionPane.ERROR_MESSAGE);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to load application: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        JButton settingsButton = new JButton("Settings");
        settingsButton.setBackground(new Color(37, 41, 45));
        settingsButton.setForeground(Color.WHITE);
        settingsButton.setFont(new Font("Inter", Font.BOLD, 12));
        settingsButton.setFocusPainted(false);
        settingsButton.setOpaque(true);
        settingsButton.setPreferredSize(new Dimension(105, 33));
        settingsButton.setMinimumSize(new Dimension(105, 33));
        settingsButton.setMaximumSize(new Dimension(105, 33));
        settingsButton.addActionListener(e -> new SettingsGui().setVisible(true));

        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(settingsButton);
        topPanel.add(loadAppButton);
        topPanel.add(Box.createRigidArea(new Dimension(15, 0)));

        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(140, 0, 0, 0));
        centerPanel.setPreferredSize(new Dimension(468, 400));
        centerPanel.setOpaque(false);

        JButton createNewAppButton = new JButton("Create new SCARA App");
        createNewAppButton.setBackground(new Color(42, 255, 13));
        createNewAppButton.setForeground(Color.BLACK);
        createNewAppButton.setFont(new Font("Inter", Font.BOLD, 16));
        createNewAppButton.setFocusPainted(false);
        createNewAppButton.setOpaque(true);
        createNewAppButton.setBorder(BorderFactory.createEmptyBorder());
        createNewAppButton.setPreferredSize(new Dimension(214, 49));

        createNewAppButton.addActionListener(e -> new CreateAppModal(this, appItemsPanel, "Create new SCARA App").createModal());

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
                AppItem appItem = new AppItem(application.getName(), file, appItemsPanel);
                appItem.setMaximumSize(new Dimension(468, 60));
                appItemsPanel.add(appItem);
            }
        } catch (IOException e) {
            System.err.println("Failed to load application from file: " + file.getName());
        }
    }

    public LocationsConfiguration getLocationsConfiguration() {
        return locationsConfiguration;
    }
}