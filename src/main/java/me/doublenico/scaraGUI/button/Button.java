package me.doublenico.scaraGUI.button;

import me.doublenico.scaraGUI.frame.ApplicationFrame;

import javax.swing.*;
import java.awt.*;

public abstract class Button extends JButton implements IButton{

    private final String name;
    private final ApplicationFrame  applicationFrame;
    private final ButtonType type;

    public Button(ButtonManager manager, ButtonType type, ApplicationFrame applicationFrame, String name){
        this.name = name;
        this.type = type;
        this.applicationFrame = applicationFrame;
        setName(name);
        setText(name);
        manager.addButton(this);
    }

    public void setDefaultDesign(){
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setOpaque(true);
    }

    public ApplicationFrame getApplicationFrame() {
        return applicationFrame;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ButtonType getButtonType() {
        return type;
    }
}
