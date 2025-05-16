package me.doublenico.scaraGUI.gui.main.buttons;

import me.doublenico.scaraGUI.button.ButtonManager;
import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.button.RoundedButton;
import me.doublenico.scaraGUI.frame.ApplicationFrame;
import me.doublenico.scaraGUI.gui.main.CreateAppModal;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;

import javax.swing.*;
import java.awt.*;

public class CreateNewAppButton extends RoundedButton {
    public CreateNewAppButton(ButtonManager manager, String name, ButtonType type, ApplicationFrame parent) {
        super(manager, type, parent, name, 15);
        setBackground(new Color(42, 255, 13));
        setForeground(Color.BLACK);
        setFont(new Font("Inter", Font.BOLD, 16));
        setFocusPainted(false);
        setPreferredSize(new Dimension(214, 49));
    }

    public void loadEventListener(ScaraGUI owner, JPanel panel){
        addActionListener(e -> new CreateAppModal(owner, panel, "Create new SCARA App").createModal());
    }
    
}
