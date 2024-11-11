package me.doublenico.scaraGUI;

import javax.swing.*;
import java.awt.*;

public class AppItem extends JPanel {
    private JButton deleteButton;
    private JButton modifyButton;

    public AppItem(String appName) {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(new Color(50, 50, 50));
        setPreferredSize(new Dimension(468, 60));

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        labelPanel.setOpaque(false);

        JLabel appLabel = new JLabel(appName);
        appLabel.setFont(new Font("Inter", Font.BOLD, 18));
        appLabel.setForeground(Color.WHITE);
        labelPanel.add(appLabel);

        deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(204, 0, 0));
        deleteButton.setFont(new Font("Inter", Font.BOLD, 12));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFocusPainted(false);

        modifyButton = new JButton("Modify");
        modifyButton.setBackground(new Color(0, 122, 204));
        modifyButton.setFont(new Font("Inter", Font.BOLD, 12));
        modifyButton.setForeground(Color.BLACK);
        modifyButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        buttonPanel.setOpaque(false);
        buttonPanel.add(deleteButton);
        buttonPanel.add(modifyButton);

        add(labelPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.EAST);
    }
}
