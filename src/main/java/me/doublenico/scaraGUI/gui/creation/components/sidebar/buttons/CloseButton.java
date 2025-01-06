package me.doublenico.scaraGUI.gui.creation.components.sidebar.buttons;

import me.doublenico.scaraGUI.button.Button;
import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.creation.buttons.SidebarButton;
import me.doublenico.scaraGUI.gui.creation.components.sidebar.CreationSidebar;

import javax.swing.*;
import java.awt.*;

public class CloseButton extends Button {
    public CloseButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name);
        setToolTipText("Close the application");
        setFont(new Font("Inter", Font.BOLD, 12));
        setBackground(new Color(204, 0, 0));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setPreferredSize(new Dimension(40, 30));
    }

    public void loadEventListener(CreationSidebar sideBar, SidebarButton openSidebarButton, JPanel contentPane){
        addActionListener(e -> {
            sideBar.setVisible(false);
            openSidebarButton.setVisible(true);
            contentPane.revalidate();
            contentPane.repaint();
        });
    }
}
