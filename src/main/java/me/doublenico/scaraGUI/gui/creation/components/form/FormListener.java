package me.doublenico.scaraGUI.gui.creation.components.form;

import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FormListener implements DocumentListener {

    private final AppCreationGUI parent;

    public FormListener(AppCreationGUI parent) {
        this.parent = parent;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        parent.getOperationsPanel().setHasSaved(false);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        parent.getOperationsPanel().setHasSaved(false);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        parent.getOperationsPanel().setHasSaved(false);
    }
}
