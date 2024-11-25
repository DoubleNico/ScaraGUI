package me.doublenico.scaraGUI;

import com.formdev.flatlaf.extras.FlatDesktop;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.SystemInfo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppCreationGUI extends JFrame {

    private HashMap<CreationLabel, JTextField> textFields;
    private OperationItem selectedOperation;
    private JButton closeButton;
    private final OperationsHandler operationsHandler;
    private final String name;
    private final List<OperationItem> operations;
    private final JPanel operationsPanel;

    public AppCreationGUI(String name) {
        super("Editing " + name);
        this.name = name;
        FlatMacDarkLaf.setup();

        if(SystemInfo.isMacFullWindowContentSupported) getRootPane().putClientProperty( "apple.awt.transparentTitleBar", true );
        getRootPane().putClientProperty( "apple.awt.fullscreenable", true );

        FlatDesktop.setQuitHandler(response -> {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit ScaraGUI", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) response.performQuit();
            else response.cancelQuit();
        });

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(954, 600);
        setLocationRelativeTo(null);

        operationsHandler = new OperationsHandler();

        operations = new ArrayList<>();
        operationsPanel = new JPanel();
        operationsPanel.setLayout(new BoxLayout(operationsPanel, BoxLayout.Y_AXIS));
        operationsPanel.setOpaque(false);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(22, 22, 23));

        JPanel sideBar = createSidebar();
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
        closeButton.addActionListener(e -> {
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

        saveButton.addActionListener(e -> saveOperation());

        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(openSidebarButton);
        rightPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel formPanel = createFormPanel();
        rightPanel.add(formPanel, BorderLayout.CENTER);

        contentPane.add(rightPanel, BorderLayout.CENTER);

        setContentPane(contentPane);

        addOperation("Default");
        setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
        sideBar.setBackground(new Color(38, 38, 38));
        sideBar.setPreferredSize(new Dimension(250, 600));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        headerPanel.setOpaque(false);

        JLabel scaraName = new JLabel("ScaraGUI");
        scaraName.setFont(new Font("Inter", Font.BOLD, 24));
        scaraName.setForeground(Color.WHITE);
        scaraName.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        closeButton = new JButton("X");
        closeButton.setFont(new Font("Inter", Font.BOLD, 12));
        closeButton.setBackground(new Color(204, 0, 0));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setPreferredSize(new Dimension(40, 30));

        JButton goBackButton = new JButton("<");
        goBackButton.setFont(new Font("Inter", Font.BOLD, 12));
        goBackButton.setBackground(new Color(204, 0, 0));
        goBackButton.setForeground(Color.WHITE);
        goBackButton.setFocusPainted(false);
        goBackButton.setPreferredSize(new Dimension(40, 30));
        goBackButton.addActionListener(e -> {
            new ScaraGUI().setVisible(true);
            dispose();
        });

        headerPanel.add(scaraName);
        headerPanel.add(Box.createHorizontalGlue());
        headerPanel.add(closeButton);
        headerPanel.add(goBackButton);

        sideBar.add(headerPanel);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setBackground(Color.BLACK);
        separator.setForeground(Color.BLACK);
        sideBar.add(separator);

        JScrollPane operationsScrollPane = new JScrollPane(operationsPanel);
        operationsScrollPane.setOpaque(false);
        operationsScrollPane.setBorder(BorderFactory.createEmptyBorder());
        operationsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        operationsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        operationsScrollPane.getVerticalScrollBar().setBackground(new Color(38, 38, 38, 208));
        sideBar.add(operationsScrollPane);

        return sideBar;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(22, 22, 23));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        CreationLabel[] labels = {CreationLabel.NAME, CreationLabel.JOINT1, CreationLabel.JOINT2, CreationLabel.Z, CreationLabel.GRIPPER, CreationLabel.SPEED};
        textFields = new HashMap<>();
        for (int i = 0; i < labels.length; i++) {
            if (CreationLabel.getLabel(i) == null) continue;
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.4;
            JLabel label = createStyledLabel(labels[i].name);
            formPanel.add(label, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.6;
            JTextField textField = new JTextField(labels[i].fieldName);
            textField.setPreferredSize(new Dimension(150, 30));
            textField.setFont(new Font("Inter", Font.PLAIN, 14));
            textField.setBackground(new Color(50, 50, 50));
            textField.setForeground(Color.WHITE);
            textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            formPanel.add(textField, gbc);
            textFields.put(labels[i], textField);
            gbc.gridx = 2;
            gbc.weightx = 0.1;
            JLabel helpIcon = new JLabel(new ImageIcon("src/main/resources/help-icon.png"));
            helpIcon.setMaximumSize(new Dimension(40, 40));
            formPanel.add(helpIcon, gbc);
        }

        return formPanel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Inter", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        return label;
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

    public void loadOperation(OperationItem operationItem) {
        Operation operation = operationItem.getOperation();
        for (CreationLabel label : CreationLabel.values()) {
            JTextField textField = textFields.get(label);
            switch (label) {
                case NAME: {
                    textField.setText(operation.getName());
                    break;
                }
                case JOINT1: {
                    if (operation.getJoint1() != 0) textField.setText(String.valueOf(operation.getJoint1()));
                    else textField.setText(label.fieldName);
                    break;
                }
                case JOINT2: {
                    if (operation.getJoint2() != 0) textField.setText(String.valueOf(operation.getJoint2()));
                    else textField.setText(label.fieldName);
                    break;
                }
                case Z: {
                    if (operation.getZ() != 0) textField.setText(String.valueOf(operation.getZ()));
                    else textField.setText(label.fieldName);
                    break;
                }
                case GRIPPER: {
                    if (operation.getGripper() != 0) textField.setText(String.valueOf(operation.getGripper()));
                    else textField.setText(label.fieldName);
                    break;
                }
                case SPEED: {
                    if (operation.getSpeed() != 0) textField.setText(String.valueOf(operation.getSpeed()));
                    else textField.setText(label.fieldName);
                    break;
                }
            }
        }
        operationsHandler.addOperationItem(operationItem.getOperation().getName(), operationItem);
        selectedOperation = operationItem;
    }

    public void saveOperation() {
        if (operationsHandler == null) return;
        Operation operation = new Operation(
                textFields.get(CreationLabel.NAME).getText(),
                Integer.parseInt(textFields.get(CreationLabel.JOINT1).getText()),
                Integer.parseInt(textFields.get(CreationLabel.JOINT2).getText()),
                Integer.parseInt(textFields.get(CreationLabel.Z).getText()),
                Integer.parseInt(textFields.get(CreationLabel.GRIPPER).getText()),
                Integer.parseInt(textFields.get(CreationLabel.SPEED).getText())
        );
        operationsHandler.removeOperationItem(selectedOperation.getOperation().getName());
        selectedOperation.setOperation(operation);
        operationsHandler.addOperationItem(operation.getName(), selectedOperation);
    }

    public void addOperation(String name) {
        if (operationsHandler == null) return;
        OperationItem operation = new OperationItem(name,new Operation(name, 0,0, 0, 0, 0), this);
        operationsHandler.addOperationItem(name, operation);
        operations.add(operation);
        operation.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        operation.setMaximumSize(new Dimension(230, 58));
        operationsPanel.add(operation);
        operationsPanel.add(Box.createVerticalStrut(5));
        updateOperationStates();
        operationsPanel.revalidate();
        operationsPanel.repaint();
    }

    public void moveOperationUp(OperationItem item) {
        int index = operations.indexOf(item);
        if (index > 0) {
            operations.remove(index);
            operations.add(index - 1, item);
            updateOperationsPanel();
        }
    }

    public void moveOperationDown(OperationItem item) {
        int index = operations.indexOf(item);
        if (index < operations.size() - 1) {
            operations.remove(index);
            operations.add(index + 1, item);
            updateOperationsPanel();
        }
    }

    public void deleteOperation(OperationItem item) {
        if (operations.size() == 1) return;
        operationsHandler.removeOperationItem(item.getOperation().getName());
        operations.remove(item);
        operationsPanel.remove(item);
        updateOperationStates();
        operationsPanel.revalidate();
        operationsPanel.repaint();
    }

    private void updateOperationStates() {
        for (int i = 0; i < operations.size(); i++) {
            OperationItem item = operations.get(i);
            item.setMoveUpEnabled(i > 0);
            item.setMoveDownEnabled(i < operations.size() - 1);
            item.setDeleteEnabled(operations.size() > 1);
        }
    }

    private void updateOperationsPanel() {
        operationsPanel.removeAll();
        for (OperationItem item : operations) {
            operationsPanel.add(item);
        }
        updateOperationStates();
        operationsPanel.revalidate();
        operationsPanel.repaint();
    }

    public List<OperationItem> getOperations() {
        return operations;
    }
}
