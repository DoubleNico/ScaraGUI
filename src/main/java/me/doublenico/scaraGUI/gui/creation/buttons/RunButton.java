package me.doublenico.scaraGUI.gui.creation.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.creation.components.operation.CreationOperation;

import javax.swing.*;
import java.awt.*;

public class RunButton extends Button {
    public RunButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name);
        setFont(new Font("Inter", Font.BOLD, 12));
        setBackground(new Color(32, 206, 35));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setPreferredSize(new Dimension(80, 30));
        setBorder(BorderFactory.createEmptyBorder());
    }

    public void loadEventListener(CreationOperation operationPanel) {
        addActionListener(e -> {
            JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.setBorder(BorderFactory.createLineBorder(new Color(22, 22, 23)));
            popupMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
            JMenuItem runEntireApplication = new JMenuItem("Run entire application");
            runEntireApplication.setForeground(Color.WHITE);
            JMenuItem runSelectedOperation = new JMenuItem("Run selected operation");
            runSelectedOperation.setForeground(Color.WHITE);

            runEntireApplication.addActionListener(event -> operationPanel.runApplication());

            runSelectedOperation.addActionListener(event -> operationPanel.runOperation());

            popupMenu.add(runEntireApplication);
            popupMenu.add(runSelectedOperation);

            popupMenu.show(this, 0, getHeight());
        });
    }
}
