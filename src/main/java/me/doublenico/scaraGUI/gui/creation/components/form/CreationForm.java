package me.doublenico.scaraGUI.gui.creation.components.form;

import me.doublenico.scaraGUI.gui.RoundedBorder;
import me.doublenico.scaraGUI.gui.creation.AppCreationGUI;
import me.doublenico.scaraGUI.utils.IntegerUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class CreationForm extends JPanel {

    private final HashMap<CreationLabel, JTextField> textFields;

    public CreationForm(AppCreationGUI parent) {
        setLayout(new GridBagLayout());
        setBackground(new Color(22, 22, 23));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        CreationLabel[] labels = {CreationLabel.NAME, CreationLabel.JOINT1, CreationLabel.JOINT2, CreationLabel.JOINT3, CreationLabel.Z, CreationLabel.GRIPPER, CreationLabel.SPEED, CreationLabel.ACCELERATION};
        textFields = new HashMap<>();
        for (int i = 0; i < labels.length; i++) {
            if (CreationLabel.getLabel(i) == null) continue;
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.4;
            JLabel label = createStyledLabel(labels[i].name);
            label.setToolTipText(labels[i].tooltip);
            add(label, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.6;
            JTextField textField = createStyledTextField(labels[i].fieldName, i == 0, i == labels.length - 1);
            textField.setToolTipText(labels[i].tooltip);
            textField.getDocument().addDocumentListener(new FormListener(parent));
            add(textField, gbc);
            textFields.put(labels[i], textField);
            gbc.gridx = 2;
            gbc.weightx = 0.1;
            JLabel helpIcon = new JLabel(new ImageIcon("src/main/resources/help-icon.png"));
            helpIcon.setToolTipText("Click here for help for " + labels[i].name);
            int finalI = i;
            helpIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    labels[finalI].getHelp().showModal(parent);
                }
            });
            helpIcon.setMaximumSize(new Dimension(40, 40));
            add(helpIcon, gbc);
        }
    }
    public boolean validateForm() {
        Map<Integer, String> invalidFields = new TreeMap<>();

        for (CreationLabel label : textFields.keySet()) {
            String text = textFields.get(label).getText();
            if (text.isEmpty()) {
                invalidFields.put(label.position, emptyFieldMessage(label.name));
            } else if (label != CreationLabel.NAME) {
                if (new IntegerUtils().isInteger(text)) {
                    if (Integer.parseInt(text) < 0) {
                        invalidFields.put(label.position, negativeFieldMessage(label.name));
                    }
                } else {
                    invalidFields.put(label.position, badInputMessage(label.name));
                }
            }
        }

        if (!invalidFields.isEmpty()) {
            JOptionPane.showMessageDialog(this, String.join("\n", invalidFields.values()), "Invalid Inputs", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    public String emptyFieldMessage(String field) {
        return field + " is empty";
    }

    public String negativeFieldMessage(String field) {
        return field + " contains a negative value";
    }

    public String badInputMessage(String field) {
        return field + " contains a bad input (not a number)";
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Inter", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField createStyledTextField(String text, boolean isFirst, boolean isLast) {
        JTextField textField = new JTextField(text);
        textField.setPreferredSize(new Dimension(150, 45));
        textField.setFont(new Font("Inter", Font.PLAIN, 14));
        textField.setBackground(new Color(50, 50, 50));
        textField.setForeground(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(isFirst || isLast ? 10 : 4),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        return textField;
    }
    public HashMap<CreationLabel, JTextField> getTextFields() {
        return textFields;
    }
}
