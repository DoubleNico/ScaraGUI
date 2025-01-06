package me.doublenico.scaraGUI.frame;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.SystemInfo;

import javax.swing.*;

public abstract class ApplicationFrame extends JFrame implements IApplicationFrame {

    private final String title;

    public ApplicationFrame(String title) {
        super(title);
        this.title = title;
        FlatMacDarkLaf.setup();
        if(SystemInfo.isMacFullWindowContentSupported) getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);

    }

    @Override
    public String getTitle() {
        return title;
    }
}
