package com.newpageaction;

import java.io.File;

public class DuplicateFiles {
    public static boolean fileExistsInDirectoryOrSubdirectories(File directory, String fileName) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // recursively check in subdirectories
                        if (fileExistsInDirectoryOrSubdirectories(file, fileName)) {
                            return true;
                        }
                    } else if (file.getName().equals(fileName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
