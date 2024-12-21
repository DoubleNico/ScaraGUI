package me.doublenico.scaraGUI.gui.creation.components.form;

import javax.swing.*;

public abstract class CreationHelp extends JDialog implements HelpLabel {

    private final String title;

    public CreationHelp(String title){
        this.title = title;
        setModal(true);
        setTitle(title);
    }

    @Override
    public String getTitle() {
        return title;
    }
}
