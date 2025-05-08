package me.doublenico.scaraGUI.gui.creation.components.operation;

import java.util.UUID;

public class Operation {

    private final UUID uuid;
    private final String name;
    private final int joint1;
    private final int joint2;
    private final int joint3;
    private final int z;
    private final int gripper;
    private final int speed;
    private final int acceleration;

    public Operation(UUID uuid, String name, int joint1, int joint2, int joint3, int z, int gripper, int speed, int acceleration) {
        this.uuid = uuid;
        this.name = name;
        this.joint1 = joint1;
        this.joint2 = joint2;
        this.joint3 = joint3;
        this.z = z;
        this.gripper = gripper;
        this.speed = speed;
        this.acceleration = acceleration;
    }

    public boolean isValidOperation(int defaultValue){
        return joint1 != defaultValue && joint2 != defaultValue && z != defaultValue && gripper != defaultValue && speed != defaultValue;
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

    public int getJoint3() {
        return joint3;
    }

    public int getAcceleration() {
        return acceleration;
    }

    @Override
    public String toString() {
        return "Operation{" +
            "uuid=" + uuid +
            ", name='" + name + '\'' +
            ", joint1=" + joint1 +
            ", joint2=" + joint2 +
            ", joint3=" + joint3 +
            ", z=" + z +
            ", gripper=" + gripper +
            ", speed=" + speed +
            ", acceleration=" + acceleration +
            '}';
    }
}
