package me.doublenico.scaraGUI.gui.creation.components.form;

import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class CreationForm extends JPanel {

    private final HashMap<CreationLabel, JTextField> textFields;

    public CreationForm(AppCreationGUI parent) {
        setLayout(new GridBagLayout());
        setBackground(new Color(22, 22, 23));

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
            add(label, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.6;
            JTextField textField = new JTextField(labels[i].fieldName);
            textField.setPreferredSize(new Dimension(150, 30));
            textField.setFont(new Font("Inter", Font.PLAIN, 14));
            textField.setBackground(new Color(50, 50, 50));
            textField.setForeground(Color.WHITE);
            textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            textField.getDocument().addDocumentListener(new FormListener(parent));
            add(textField, gbc);
            textFields.put(labels[i], textField);
            gbc.gridx = 2;
            gbc.weightx = 0.1;
            JLabel helpIcon = new JLabel(new ImageIcon("src/main/resources/help-icon.png"));
            helpIcon.setMaximumSize(new Dimension(40, 40));
            add(helpIcon, gbc);
        }
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Inter", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    public HashMap<CreationLabel, JTextField> getTextFields() {
        return textFields;
    }
}
