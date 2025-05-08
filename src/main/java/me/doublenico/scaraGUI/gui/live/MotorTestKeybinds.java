package me.doublenico.scaraGUI.gui.live;

import me.doublenico.scaraGUI.frame.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MotorTestKeybinds {

    private final ApplicationFrame frame;

    public MotorTestKeybinds(ApplicationFrame frame){
        this.frame = frame;
    }

    public void flashFeedback(boolean isForward) {
        for (Component c : frame.getContentPane().getComponents()) {
            if (c instanceof JPanel) {
                flashButtonInContainer((Container) c, isForward);
            }
        }
    }

    private void flashButtonInContainer(Container container, boolean isForward) {
        for (Component c : container.getComponents()) {
            if (c instanceof JButton) {
                JButton btn = (JButton) c;
                String text = btn.getText().toLowerCase();
                if ((isForward && text.contains("forward")) ||
                    (!isForward && text.contains("backward"))) {
                    flashButton(btn);
                    return;
                }
            } else if (c instanceof Container) {
                flashButtonInContainer((Container) c, isForward);
            }
        }
    }

    private void flashButton(JButton button) {
        Color originalColor = button.getBackground();
        button.setBackground(Color.WHITE);

        Timer timer = new Timer(150, e -> {
            button.setBackground(originalColor);
        });
        timer.setRepeats(false);
        timer.start();
    }
}
