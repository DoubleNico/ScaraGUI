package me.doublenico.scaraGUI.gui.creation.components.sidebar;

import me.doublenico.scaraGUI.button.ButtonType;
import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;
import me.doublenico.scaraGUI.gui.creation.components.operation.CreationOperation;
import me.doublenico.scaraGUI.gui.creation.components.sidebar.buttons.CloseButton;

import javax.swing.*;
import java.awt.*;

public class CreationSidebar extends JPanel {

    private final CloseButton closeButton;

    public CreationSidebar(AppCreationGUI parent, CreationOperation operationsPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(38, 38, 38));
        setPreferredSize(new Dimension(250, 600));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        headerPanel.setOpaque(false);

        JLabel scaraName = new JLabel("ScaraGUI");
        scaraName.setFont(new Font("Inter", Font.BOLD, 24));
        scaraName.setForeground(Color.WHITE);
        scaraName.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        closeButton = new CloseButton(parent.getOwner().getButtonManager(), "x", ButtonType.CLOSE_BUTTON, parent);

        headerPanel.add(scaraName);
        headerPanel.add(new Box.Filler(new Dimension(65, 30), new Dimension(65, 30), new Dimension(65, 30)));
        headerPanel.add(closeButton);

        add(headerPanel);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setBackground(Color.BLACK);
        separator.setForeground(Color.BLACK);
        add(separator);

        JScrollPane operationsScrollPane = new JScrollPane(operationsPanel);
        operationsScrollPane.setOpaque(false);
        operationsScrollPane.setBorder(BorderFactory.createEmptyBorder());
        operationsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        operationsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        operationsScrollPane.getVerticalScrollBar().setBackground(new Color(38, 38, 38, 208));

        add(operationsScrollPane);
    }

    public CloseButton getCloseButton() {
        return closeButton;
    }
}
