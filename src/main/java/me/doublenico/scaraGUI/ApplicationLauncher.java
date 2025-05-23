package me.doublenico.scaraGUI;

import com.formdev.flatlaf.util.SystemInfo;
import me.doublenico.scaraGUI.gui.main.ScaraGUI;

import javax.swing.*;

public class ApplicationLauncher {

    public static final String JETBRAINS_AWT_WINDOW_DARK_APPEARANCE = "jetbrains.awt.windowDarkAppearance";

    public static void main(String[] args) {
        if(SystemInfo.isMacOS) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("apple.awt.application.name", "ScaraGUI");
            System.setProperty("apple.awt.application.appearance", "system");
            System.setProperty(JETBRAINS_AWT_WINDOW_DARK_APPEARANCE, "true");
        }
        // joint 1 pentru stepper 1
        // joint 2 pentru stepper 2
        // joint 3 pentru stepper 3
        // z pentru stepper 4

        SwingUtilities.invokeLater(ScaraGUI::new);
    }
}
