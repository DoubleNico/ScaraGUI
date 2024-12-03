package me.doublenico.scaraGUI.button;

import javax.swing.*;
import java.awt.*;

public abstract class Button extends JButton implements IButton{

    private final String name;
    private final ButtonType type;

    public Button(ButtonManager manager, String name, ButtonType type) {
        this.name = name;
        this.type = type;
        setName(name);
        setText(name);
        manager.addButton(this);
    }

    public void setDefaultDesign(){
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setOpaque(true);
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
