package me.doublenico.scaraGUI.gui.creation.components.form.filters;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class CreationCharacterLimit extends DocumentFilter {

    private final int characterLimit;

    public CreationCharacterLimit(int characterLimit) {
        this.characterLimit = characterLimit;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws
        BadLocationException {
        if (fb.getDocument().getLength() + string.length() <= characterLimit)
            super.insertString(fb, offset, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (fb.getDocument().getLength() - length + text.length() <= characterLimit)
            super.replace(fb, offset, length, text, attrs);
    }
}
