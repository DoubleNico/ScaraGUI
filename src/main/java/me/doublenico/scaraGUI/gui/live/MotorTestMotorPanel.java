package me.doublenico.scaraGUI.gui.live;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MotorTestMotorPanel extends JPanel {

    private String selectedMotor = "Joint1";
    private final List<Consumer<String>> selectionListeners = new ArrayList<>();

    public MotorTestMotorPanel(MotorTestGUI motorTestGUI) {
        setLayout(new GridLayout(5, 1, 0, 5));
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 50), 1),
            "Select Motor"
        ));
        ((TitledBorder) getBorder()).setTitleColor(Color.WHITE);

        ButtonGroup motorGroup = new ButtonGroup();

        String[] motors = {"Joint1", "Joint2", "Joint3", "Z", "Gripper"};
        for (String motor : motors) {
            JRadioButton radioButton = new JRadioButton(motor);
            radioButton.setForeground(Color.WHITE);
            radioButton.setOpaque(false);
            radioButton.addActionListener(e -> {
                selectedMotor = motor;
                motorTestGUI.setCurrentPosition(0);
                motorTestGUI.getCoordinateDisplay().setText("Position: " + motorTestGUI.getCurrentPosition());
                notifySelectionListeners(motor);
            });
            motorGroup.add(radioButton);
            add(radioButton);

            if (motor.equals("Joint1")) {
                radioButton.setSelected(true);
            }
        }
    }

    public String getSelectedMotor() {
        return selectedMotor;
    }

    public void addMotorSelectionListener(Consumer<String> listener) {
        selectionListeners.add(listener);
    }

    private void notifySelectionListeners(String motorType) {
        for (Consumer<String> listener : selectionListeners) {
            listener.accept(motorType);
        }
    }

    public void setSelectedMotor(String selectedMotor) {
        this.selectedMotor = selectedMotor;
    }
}