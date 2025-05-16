package me.doublenico.scaraGUI.button;

import me.doublenico.scaraGUI.utils.RoundedBorder;
import me.doublenico.scaraGUI.frame.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public abstract class RoundedButton extends JButton implements IButton {

    private final String name;
    private final ApplicationFrame applicationFrame;
    private final ButtonType type;
    private final int radius;
    private boolean isHovered = false;

    public RoundedButton(ButtonManager manager, ButtonType type, ApplicationFrame applicationFrame, String name, int radius) {
        this.name = name;
        this.type = type;
        this.radius = radius;
        this.applicationFrame = applicationFrame;
        setName(name);
        setText(name);

        setBorder(new RoundedBorder(radius));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
            }
        });

        setContentAreaFilled(false);
        manager.addRoundedButton(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (isHovered)
            g.setColor(new Color(
                    getBackground().getRed(),
                    getBackground().getGreen(),
                    getBackground().getBlue(),
                    100
            ));
        else
            g.setColor(getBackground());

        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius * 2, radius * 2);

        super.paintComponent(g);
    }


    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius * 2, radius * 2);
    }

    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius * 2, radius * 2);
        return shape.contains(x, y);
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
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