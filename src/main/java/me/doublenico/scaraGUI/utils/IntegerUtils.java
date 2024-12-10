package me.doublenico.scaraGUI.utils;

public class IntegerUtils {

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
