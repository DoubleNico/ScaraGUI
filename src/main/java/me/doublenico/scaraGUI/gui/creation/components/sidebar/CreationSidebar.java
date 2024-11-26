package me.doublenico.scaraGUI.gui.creation.components.sidebar;

import me.doublenico.scaraGUI.gui.main.ScaraGUI;

import javax.swing.*;
import java.awt.*;

public class CreationSidebar extends JPanel {

    private final JButton closeButton;

    public CreationSidebar(JFrame parent, JPanel operationsPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(38, 38, 38));
        setPreferredSize(new Dimension(250, 600));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        headerPanel.setOpaque(false);

        JLabel scaraName = new JLabel("ScaraGUI");
        scaraName.setFont(new Font("Inter", Font.BOLD, 24));
        scaraName.setForeground(Color.WHITE);
        scaraName.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        closeButton = new JButton("X");
        closeButton.setFont(new Font("Inter", Font.BOLD, 12));
        closeButton.setBackground(new Color(204, 0, 0));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setPreferredSize(new Dimension(40, 30));

        JButton goBackButton = new JButton("<");
        goBackButton.setFont(new Font("Inter", Font.BOLD, 12));
        goBackButton.setBackground(new Color(204, 0, 0));
        goBackButton.setForeground(Color.WHITE);
        goBackButton.setFocusPainted(false);
        goBackButton.setPreferredSize(new Dimension(40, 30));
        goBackButton.addActionListener(e -> {
            new ScaraGUI().setVisible(true);
            parent.dispose();
        });

        headerPanel.add(scaraName);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(goBackButton);
        headerPanel.add(closeButton);

        add(headerPanel);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setBackground(Color.BLACK);
        separator.setForeground(Color.BLACK);
        add(separator);

        JScrollPane operationsScrollPane = new JScrollPane(operationsPanel);
        operationsScrollPane.setOpaque(false);
        operationsScrollPane.setBorder(BorderFactory.createEmptyBorder());
        operationsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        operationsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        operationsScrollPane.getVerticalScrollBar().setBackground(new Color(38, 38, 38, 208));

        add(operationsScrollPane);
    }

    public JButton getCloseButton() {
        return closeButton;
    }
}
