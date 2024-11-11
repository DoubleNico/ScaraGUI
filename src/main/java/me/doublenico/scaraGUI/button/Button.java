package me.doublenico.scaraGUI.button;

import javax.swing.*;
import java.awt.*;

public abstract class Button implements IButton {

    private final String name;
    private final JButton button;
    private final ButtonType type;

    public Button(ButtonManager manager, JButton button, String name, ButtonType type) {
        this.name = name;
        this.type = type;
        this.button = button;
        manager.addButton(this);
    }

    public String getName() {
        return name;
    }

    @Override
    public JButton getButton() {
        return button;
    }

    public ButtonType getType() {
        return type;
    }
}
