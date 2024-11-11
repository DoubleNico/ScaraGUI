package me.doublenico.scaraGUI;

import java.awt.*;
import javax.swing.*;

public class ScaraGUI extends JFrame {

    private JButton loadAppButton;
    private JButton createNewAppButton;

    private JPanel appItemsPanel;

    public ScaraGUI() {
        super("ScaraGUI");
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
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        loadAppButton = new JButton("Load App");
        loadAppButton.setBackground(new Color(0, 122, 204));
        loadAppButton.setForeground(Color.BLACK);
        loadAppButton.setFont(new Font("Inter", Font.BOLD, 12));
        loadAppButton.setFocusPainted(false);

        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(loadAppButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(140, 0, 0, 0));
        centerPanel.setPreferredSize(new Dimension (468, 400));
        centerPanel.setOpaque(false);
        createNewAppButton = new JButton("Create new Scara App");
        createNewAppButton.setBackground(Color.GREEN);
        createNewAppButton.setForeground(Color.BLACK);
        createNewAppButton.setFont(new Font("Inter", Font.BOLD, 16));
        createNewAppButton.setFocusPainted(false);
        createNewAppButton.setPreferredSize(new Dimension(250, 60));
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
        scrollPane.setPreferredSize(new Dimension(498, 254));
        scrollPane.setOpaque(true);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBackground(new Color(50, 50, 50));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        AppItem grabBeerApp = new AppItem("Grab Beer App");
        appItemsPanel.add(grabBeerApp);
        AppItem grabBeerApp2 = new AppItem("Grab Beer App2");
        appItemsPanel.add(grabBeerApp2);
        AppItem grabBeerApp3 = new AppItem("Grab Beer App3");
        appItemsPanel.add(grabBeerApp3);
        AppItem grabBeerApp4 = new AppItem("Grab Beer App4");
        appItemsPanel.add(grabBeerApp4);

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
}