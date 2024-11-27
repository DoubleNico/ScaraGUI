package me.doublenico.scaraGUI.utils;

public class ExtensionUtils {

    private String name;

    public ExtensionUtils(String name) {
        this.name = name;
    }

    public String getFileNameWithoutExtension() {
        int lastDotIndex = name.lastIndexOf('.');
        return (lastDotIndex == -1) ? name : name.substring(0, lastDotIndex);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
