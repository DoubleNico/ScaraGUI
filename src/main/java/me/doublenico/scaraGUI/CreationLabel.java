package me.doublenico.scaraGUI;

public enum CreationLabel {
    NAME("Name", "Name", 0),
    JOINT1("Joint 1", "Angle", 1),
    JOINT2("Joint 2","Angle", 2),
    Z("Z","Position", 3),
    GRIPPER("Gripper", "Value", 4),
    SPEED("Speed","Value", 5);

    public final String name;
    public final String fieldName;
    public final int position;

    CreationLabel(String name, String fieldName, int position) {
        this.name = name;
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
}
