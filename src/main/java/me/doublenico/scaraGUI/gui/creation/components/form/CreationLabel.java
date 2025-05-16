package me.doublenico.scaraGUI.gui.creation.components.form;

import me.doublenico.scaraGUI.gui.creation.components.form.impl.NameHelpLabel;

public enum CreationLabel {
    NAME("Name", new NameHelpLabel("Help for Name"), "This will be the name of the operation", "Name", 0),
    JOINT1("Joint 1", new NameHelpLabel("Help for Joint1"), "Value of the first joint", "Angle", 1),
    JOINT2("Joint 2", new NameHelpLabel("Help for Joint2"), "Value of the second joint", "Angle", 2),
    JOINT3("Joint 3", new NameHelpLabel("Help for Joint3"), "Value of the third joint", "Angle", 3),
    Z("Z", new NameHelpLabel("Help for Z"), "This is the position in Z", "Pos", 4),
    GRIPPER("Gripper", new NameHelpLabel("Help for Gripper"), "The gripper is the thing that holds the object, it can be open or closed", "Value", 5),
    SPEED("Speed", new NameHelpLabel("Help for Speed"), "This is the speed of the motors", "Value", 6),
    ACCELERATION("Acceleration", new NameHelpLabel("Help for Speed"), "This is the acceleration of the motors", "Value", 7);

    public final String name;
    public final CreationHelp help;
    public final String tooltip;
    public final String fieldName;
    public final int position;

    CreationLabel(String name, CreationHelp help, String tooltip, String fieldName, int position) {
        this.name = name;
        this.help = help;
        this.tooltip = tooltip;
        this.fieldName = fieldName;
        this.position = position;
    }

    public static CreationLabel getLabel(int position) {
        for(CreationLabel label : values()) {
            if(label.position == position) return label;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public String getTooltip() {
        return tooltip;
    }

    public String getFieldName() {
        return fieldName;
    }

    public CreationHelp getHelp() {
        return help;
    }
}
