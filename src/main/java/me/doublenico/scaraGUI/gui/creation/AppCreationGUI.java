package me.doublenico.scaraGUI.gui.creation;

import com.formdev.flatlaf.extras.FlatDesktop;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.SystemInfo;
import me.doublenico.scaraGUI.gui.creation.components.operation.OperationItem;
import me.doublenico.scaraGUI.gui.creation.components.operation.OperationsHandler;
import me.doublenico.scaraGUI.gui.creation.components.form.CreationForm;
import me.doublenico.scaraGUI.gui.creation.components.operation.CreationOperation;
import me.doublenico.scaraGUI.gui.creation.components.sidebar.CreationSidebar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AppCreationGUI extends JFrame {

    private final CreationOperation operationsPanel;

    public AppCreationGUI(String name) {
        super("Editing " + name);
        FlatMacDarkLaf.setup();

        if(SystemInfo.isMacFullWindowContentSupported) getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);
        getRootPane().putClientProperty("apple.awt.fullscreenable", true);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(954, 600);
        setLocationRelativeTo(null);

        OperationsHandler operationsHandler = new OperationsHandler();
        CreationForm formPanel = new CreationForm(this);
        operationsPanel = new CreationOperation(this, formPanel, operationsHandler);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(22, 22, 23));

        CreationSidebar sideBar = new CreationSidebar(this, operationsPanel);
        contentPane.add(sideBar, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(22, 22, 23));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(new Color(22, 22, 23));

        JButton deleteButton = createStyledButton("Delete", new Color(204, 0, 0));
        JButton saveButton = createStyledButton("Save", new Color(0, 122, 204));
        JButton openSidebarButton = createStyledButton(">", new Color(0, 122, 204));
        openSidebarButton.setVisible(false);
        sideBar.getCloseButton().addActionListener(e -> {
            sideBar.setVisible(false);
            openSidebarButton.setVisible(true);
            contentPane.revalidate();
            contentPane.repaint();
        });

        openSidebarButton.addActionListener(e -> {
            sideBar.setVisible(true);
            openSidebarButton.setVisible(false);
            contentPane.revalidate();
            contentPane.repaint();
        });

        saveButton.addActionListener(e -> operationsPanel.saveOperation());

        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(openSidebarButton);
        rightPanel.add(buttonPanel, BorderLayout.NORTH);

        rightPanel.add(formPanel, BorderLayout.CENTER);

        contentPane.add(rightPanel, BorderLayout.CENTER);

        operationsPanel.addOperation("Default");
        setContentPane(contentPane);
        setVisible(true);

        FlatDesktop.setQuitHandler(response -> {
            if (!operationsPanel.hasSaved()) {
                int option = JOptionPane.showConfirmDialog(this, "You have not saved your progress. Are you sure you want to quit?", "Quit ScaraGUI", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) response.performQuit();
                else response.cancelQuit();
                return;
            }
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit ScaraGUI", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) response.performQuit();
            else response.cancelQuit();
        });
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Inter", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(80, 30));
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    public CreationOperation getOperationsPanel() {
        return operationsPanel;
    }
}
