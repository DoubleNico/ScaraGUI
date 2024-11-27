package me.doublenico.scaraGUI.gui.creation.components.operation;

import java.util.UUID;

public class Operation {

    private final UUID uuid;
    private final String name;
    private final int joint1;
    private final int joint2;
    private final int z;
    private final int gripper;
    private final int speed;

    public Operation(UUID uuid, String name, int joint1, int joint2, int z, int gripper, int speed) {
        this.uuid = uuid;
        this.name = name;
        this.joint1 = joint1;
        this.joint2 = joint2;
        this.z = z;
        this.gripper = gripper;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public int getJoint1() {
        return joint1;
    }

    public int getJoint2() {
        return joint2;
    }

    public int getZ() {
        return z;
    }

    public int getGripper() {
        return gripper;
    }

    public int getSpeed() {
        return speed;
    }

    public UUID getUuid() {
        return uuid;
    }
}
