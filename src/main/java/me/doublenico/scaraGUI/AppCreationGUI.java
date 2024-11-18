package me.doublenico.scaraGUI;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import java.awt.*;

public class AppCreationGUI extends JFrame {

    private final String name;

    public AppCreationGUI(String name) {
        super("Editing " + name);
        this.name = name;
        FlatMacDarkLaf.setup();

        System.setProperty("apple.awt.application.appearance", "system");
        System.setProperty(ScaraGUI.JETBRAINS_AWT_WINDOW_DARK_APPEARANCE, "true");

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(954, 600);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(22, 22, 23));

        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(new Color(38, 38, 38));
        sideBar.setPreferredSize(new Dimension(250, 600));

        JLabel scaraName = new JLabel("ScaraGUI");
        scaraName.setFont(new Font("Inter", Font.BOLD, 24));
        scaraName.setForeground(Color.WHITE);
        scaraName.setAlignmentX(Component.CENTER_ALIGNMENT);
        scaraName.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        sideBar.add(scaraName);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setBackground(Color.BLACK);
        separator.setForeground(Color.BLACK);
        sideBar.add(separator);

        for (int i = 0; i < 5; i++) {
            OperationItem operationItem = new OperationItem("Operation " + (i + 1));
            operationItem.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            operationItem.setMaximumSize(new Dimension(230, 58)); // Adjusted width
            sideBar.add(operationItem);
            sideBar.add(Box.createVerticalStrut(5));
        }
        sideBar.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(sideBar);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(true);
        scrollPane.getViewport().setOpaque(true);
        contentPane.add(scrollPane, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(22, 22, 23));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(new Color(22, 22, 23));

        JButton deleteButton = createStyledButton("Delete", new Color(204, 0, 0));
        JButton saveButton = createStyledButton("Save", new Color(0, 122, 204));

        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        rightPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel formPanel = createFormPanel();
        rightPanel.add(formPanel, BorderLayout.CENTER);

        contentPane.add(rightPanel, BorderLayout.CENTER);

        setContentPane(contentPane);
        setVisible(true);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(22, 22, 23));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Name:", "Joint 1 Angle:", "Joint 2 Angle:", "Z Position:", "Gripper Value:", "Speed Value:"};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.4;
            JLabel label = createStyledLabel(labels[i]);
            formPanel.add(label, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.6;
            JTextField textField = new JTextField(labels[i].equals("Name:") ? "Name" : labels[i].contains("Angle") ? "Angle" : "Value");
            textField.setPreferredSize(new Dimension(150, 30));
            textField.setFont(new Font("Inter", Font.PLAIN, 14));
            textField.setBackground(new Color(50, 50, 50));
            textField.setForeground(Color.WHITE);
            textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            formPanel.add(textField, gbc);
        }

        return formPanel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Inter", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Inter", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(80, 30));
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    @Override
    public String getName() {
        return name;
    }
}