package me.doublenico.scaraGUI.gui.creation.components.form;

import me.doublenico.scaraGUI.gui.creation.components.form.impl.NameHelpLabel;

public enum CreationLabel {
    NAME("Name", new NameHelpLabel("Help for Name"), "This will be the name of the operation", "Name", 0),
    JOINT1("Joint 1", new NameHelpLabel("Help for Name"), "Value of the first joint", "Angle", 1),
    JOINT2("Joint 2", new NameHelpLabel("Help for Name"), "Value of the second joint", "Angle", 2),
    Z("Z", new NameHelpLabel("Help for Name"), "This is the position in Z", "Position", 3),
    GRIPPER("Gripper", new NameHelpLabel("Help for Name"), "The gripper is the thing that holds the object, it can be open or closed", "Value", 4),
    SPEED("Speed", new NameHelpLabel("Help for Name"), "This is the speed of the motors", "Value", 5);

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
