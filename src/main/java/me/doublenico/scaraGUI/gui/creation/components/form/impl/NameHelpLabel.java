package me.doublenico.scaraGUI.gui.creation.components.form.impl;

import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;
import me.doublenico.scaraGUI.gui.creation.components.form.CreationHelp;

import java.awt.*;

public class NameHelpLabel extends CreationHelp {

    public NameHelpLabel(String title) {
        super(title);
    }

    @Override
    public void showModal(AppCreationGUI parent) {
        setResizable(false);
        setSize(400, 300);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(this);

        setVisible(true);
    }
}
