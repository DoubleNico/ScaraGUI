package me.doublenico.scaraGUI;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ScaraGUI extends JFrame {

    private JButton loadAppButton;
    private JButton createNewAppButton;

    private JPanel appItemsPanel;
    public static final String JETBRAINS_AWT_WINDOW_DARK_APPEARANCE = "jetbrains.awt.windowDarkAppearance";

    public ScaraGUI() {
        super("ScaraGUI");
        FlatMacDarkLaf.setup();

        System.setProperty("apple.awt.application.appearance", "system");
        System.setProperty(JETBRAINS_AWT_WINDOW_DARK_APPEARANCE, "true");

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

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

        loadAppButton = new JButton("Load App");
        loadAppButton.setBackground(new Color(9, 125, 201));
        loadAppButton.setForeground(Color.WHITE);
        loadAppButton.setFont(new Font("Inter", Font.BOLD, 12));
        loadAppButton.setFocusPainted(false);
        loadAppButton.setOpaque(true);
        loadAppButton.setPreferredSize(new Dimension(105, 33));
        loadAppButton.setMinimumSize(new Dimension(105, 33));
        loadAppButton.setMaximumSize(new Dimension(105, 33));

        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(loadAppButton);
        topPanel.add(Box.createRigidArea(new Dimension(15, 0)));

        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(140, 0, 0, 0));
        centerPanel.setPreferredSize(new Dimension(468, 400));
        centerPanel.setOpaque(false);

        createNewAppButton = new JButton("Create new Scara App");
        createNewAppButton.setBackground(new Color(42, 255, 13));
        createNewAppButton.setForeground(Color.BLACK);
        createNewAppButton.setFont(new Font("Inter", Font.BOLD, 16));
        createNewAppButton.setFocusPainted(false);
        createNewAppButton.setOpaque(true);
        createNewAppButton.setBorder(BorderFactory.createEmptyBorder());
        createNewAppButton.setPreferredSize(new Dimension(214, 49));

        createNewAppButton.addActionListener(e -> openCreateAppModal());

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

        AppItem grabBeerApp = new AppItem("Grab Beer App");
        grabBeerApp.setMaximumSize(new Dimension(468, 60));
        appItemsPanel.add(grabBeerApp);
        AppItem grabBeerApp2 = new AppItem("Grab Beer App2");
        grabBeerApp2.setMaximumSize(new Dimension(468, 60));
        appItemsPanel.add(grabBeerApp2);

        bottomPanel.add(scrollPane, BorderLayout.PAGE_START);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScaraGUI::new);
    }

    private void openCreateAppModal() {
        JDialog modalDialog = new JDialog(this, "Create New App", true);
        modalDialog.setResizable(false);
        modalDialog.setSize(400, 300);
        modalDialog.setLayout(new GridBagLayout());
        modalDialog.setLocationRelativeTo(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Inter", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        modalDialog.add(nameLabel, gbc);

        JTextField nameField = new JTextField("Insert Name");
        nameField.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        modalDialog.add(nameField, gbc);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(new Font("Inter", Font.BOLD, 16));
        locationLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        modalDialog.add(locationLabel, gbc);

        JTextField locationField = new JTextField();
        locationField.setPreferredSize(new Dimension(200, 30));
        locationField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;

        JButton chooseButton = new JButton("Browse");
        chooseButton.setBackground(new Color(9, 125, 201));
        chooseButton.setForeground(Color.WHITE);
        chooseButton.setFont(new Font("Inter", Font.BOLD, 12));
        chooseButton.setFocusPainted(false);

        chooseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(modalDialog);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                locationField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        JPanel locationPanel = new JPanel(new BorderLayout());
        locationPanel.add(locationField, BorderLayout.CENTER);
        locationPanel.add(chooseButton, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.gridy = 1;
        modalDialog.add(locationPanel, gbc);

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(42, 255, 13));
        saveButton.setForeground(Color.BLACK);
        saveButton.setFont(new Font("Inter", Font.BOLD, 14));
        saveButton.setFocusPainted(false);
        saveButton.setPreferredSize(new Dimension(100, 30));
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String location = locationField.getText();
            if (name == null || name.isEmpty() || location == null || location.isEmpty())
                JOptionPane.showMessageDialog(this, "Name and location cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            else {
                File file = new File(location, name);
                if (file.exists())
                    JOptionPane.showMessageDialog(this, "A file with the same name already exists at the specified location.", "Error", JOptionPane.ERROR_MESSAGE);
                else
                    modalDialog.dispose();
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        modalDialog.add(saveButton, gbc);

        modalDialog.getContentPane().setBackground(new Color(22, 22, 23));

        modalDialog.setVisible(true);
    }
}