package me.doublenico.scaraGUI.gui.creation.buttons;

import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.button.RoundedButton;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.creation.components.sidebar.CreationSidebar;

import javax.swing.*;
import java.awt.*;

public class SidebarButton extends RoundedButton {
    public SidebarButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name, 10);
        setFont(new Font("Inter", Font.BOLD, 12));
        setBackground(new Color(0, 122, 204));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setPreferredSize(new Dimension(100, 30));
    }

    public void loadEventListener(CreationSidebar sideBar, JPanel contentPane){
       addActionListener(e -> {
            sideBar.setVisible(true);
            setVisible(false);
            contentPane.revalidate();
            contentPane.repaint();
        });
    }

}
