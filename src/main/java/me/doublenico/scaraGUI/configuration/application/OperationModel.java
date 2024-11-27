package me.doublenico.scaraGUI.configuration.application;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OperationModel {
    @JsonProperty("position")
    private int position;

    @JsonProperty("joint1")
    private int joint1;

    @JsonProperty("joint2")
    private int joint2;

    @JsonProperty("z")
    private int z;

    @JsonProperty("gripper")
    private int gripper;

    @JsonProperty("speed")
    private int speed;

    public OperationModel() {}

    public OperationModel(int position, int joint1, int joint2, int z, int gripper, int speed) {
        this.position = position;
        this.joint1 = joint1;
        this.joint2 = joint2;
        this.z = z;
        this.gripper = gripper;
        this.speed = speed;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getJoint1() {
        return joint1;
    }

    public void setJoint1(int joint1) {
        this.joint1 = joint1;
    }

    public int getJoint2() {
        return joint2;
    }

    public void setJoint2(int joint2) {
        this.joint2 = joint2;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getGripper() {
        return gripper;
    }

    public void setGripper(int gripper) {
        this.gripper = gripper;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
