package me.doublenico.scaraGUI.frame;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.SystemInfo;

import javax.swing.*;

public abstract class ApplicationFrame extends JFrame implements IApplicationFrame {

    private final String title;
    private final ApplicationFrameType frameType;

    public ApplicationFrame(String title, ApplicationFrameType frameType) {
        super(title);
        this.title = title;
        this.frameType = frameType;
        FlatMacDarkLaf.setup();
        if(SystemInfo.isMacFullWindowContentSupported) getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);

        ApplicationFrameType.registerFrame(frameType, this);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public ApplicationFrameType getFrameType() {
        return frameType;
    }
}