package me.doublenico.scaraGUI;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.SystemInfo;

import javax.swing.*;
import java.awt.*;

public class ScaraGUI extends JFrame {

    private JButton loadAppButton;
    private JButton createNewAppButton;

    private JPanel appItemsPanel;
    public static final String JETBRAINS_AWT_WINDOW_DARK_APPEARANCE = "jetbrains.awt.windowDarkAppearance";

    public ScaraGUI() {
        super("ScaraGUI");
        FlatMacDarkLaf.setup();
        if(SystemInfo.isMacFullWindowContentSupported) getRootPane().putClientProperty( "apple.awt.transparentTitleBar", true );

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
        loadAppButton.addActionListener(e -> new AppCreationGUI("ScaraGUI").setVisible(true));

        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(loadAppButton);
        topPanel.add(Box.createRigidArea(new Dimension(15, 0)));

        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(140, 0, 0, 0));
        centerPanel.setPreferredSize(new Dimension(468, 400));
        centerPanel.setOpaque(false);

        createNewAppButton = new JButton("Create new SCARA App");
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
        setVisible(true);
    }

    public static void main(String[] args) {
        if(SystemInfo.isMacOS ) {
            System.setProperty( "apple.laf.useScreenMenuBar", "true" );
            System.setProperty( "apple.awt.application.name", "ScaraGUI" );
            System.setProperty( "apple.awt.application.appearance", "system" );
            System.setProperty(JETBRAINS_AWT_WINDOW_DARK_APPEARANCE, "true");
        }
        SwingUtilities.invokeLater(ScaraGUI::new);
    }
}