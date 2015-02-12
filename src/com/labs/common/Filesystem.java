package com.labs.common;

import java.io.File;
import java.util.ArrayList;

/**
 * Adrian Cooney (12394581)
 * 12/02/15 com.labs.common
 */
public class Filesystem {

    /**
     * Recursively gather files withing a directory and output an arraylist of the paths.
     * @param files
     * @return
     */
    public static ArrayList<String> gatherFiles(File[] files) {
        ArrayList<String> paths = new ArrayList<>();
        gatherFiles(files, paths, "/");
        return paths;
    }

    /**
     * The recursive function to gather the files. See gatherFiles(File[])
     * @param files
     * @param list
     * @param currentPath
     */
    public static void gatherFiles(File[] files, ArrayList<String> list, String currentPath) {
        for (File file : files) {
            if (file.isDirectory()) {
                String path = currentPath + file.getName() + "/";
                list.add(path);
                gatherFiles(file.listFiles(), list, path); // Calls same method again.
            } else {
                list.add(currentPath + file.getName());
            }
        }
    }
}
