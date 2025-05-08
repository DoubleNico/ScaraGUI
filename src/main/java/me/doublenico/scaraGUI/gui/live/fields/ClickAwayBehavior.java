package me.doublenico.scaraGUI.gui.live.fields;

import me.doublenico.scaraGUI.frame.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClickAwayBehavior {

    /**
     * Adds click-away behavior to a text field, so that clicking anywhere outside the field
     * will cause it to lose focus.
     *
     * @param textField The text field to add click-away behavior to
     */
    public void addClickAwayBehavior(ApplicationFrame frame, JTextField textField) {
        JRootPane rootPane = frame.getRootPane();

        JPanel glassPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Make it completely transparent
                g.setColor(new Color(0, 0, 0, 0));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        glassPane.setOpaque(false);
        glassPane.setLayout(null);

        glassPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                glassPane.setVisible(false);
                textField.transferFocus();
            }
        });

        rootPane.setGlassPane(glassPane);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                glassPane.setVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                glassPane.setVisible(false);
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component clickedComponent = SwingUtilities.getDeepestComponentAt(
                    e.getComponent(), e.getX(), e.getY());

                if (clickedComponent != textField) {
                    textField.transferFocus();
                }
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    textField.transferFocus();
                }
            }
        });
    }

}
