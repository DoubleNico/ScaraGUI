package me.doublenico.scaraGUI.utils;

import java.io.File;

public class FileUtils {

    public boolean handleSelectedFile(File selectedFile) {
        File baseDir = new File(System.getProperty("user.dir"));
        File parentDir = selectedFile.getParentFile();

        return !baseDir.equals(parentDir);
    }
}
