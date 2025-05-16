package me.doublenico.scaraGUI.frame;

import java.util.HashMap;
import java.util.Map;

public enum ApplicationFrameType {
    SCARA_GUI(0),
    APP_CREATION(1),
    SETTINGS(2),
    MOTOR_TEST(3),
    LOG_VIEWER(4);

    public final int priority;
    private static final Map<ApplicationFrameType, ApplicationFrame> frameRegistry = new HashMap<>();

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

    public static void registerFrame(ApplicationFrameType type, ApplicationFrame frame) {
        frameRegistry.put(type, frame);
    }

    public ApplicationFrame getFrame() {
        return frameRegistry.get(this);
    }

    public static Map<ApplicationFrameType, ApplicationFrame> getAllFrames() {
        return frameRegistry;
    }
}