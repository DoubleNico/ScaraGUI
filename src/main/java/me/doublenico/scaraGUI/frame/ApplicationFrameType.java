package me.doublenico.scaraGUI.frame;

public enum ApplicationFrameType {
    SCARA_GUI(0),
    APP_CREATION(1),
    SETTINGS(2);

    public final int priority;

    ApplicationFrameType(int priority) {
        this.priority = priority;
    }

    public static ApplicationFrameType getFrameType(int priority) {
        for(ApplicationFrameType type : values()) {
            if(type.priority == priority) return type;
        }
        return null;
    }

    public int getPriority() {
        return priority;
    }
}
