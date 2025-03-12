package com.sastaybrands.mobiles.util;

import java.io.File;

public class ListFiles {

    public static void main(String[] args) {
        String folderPath = "./mobile-photos"; // Replace with the actual folder path

        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                System.out.println("Files in folder: " + folderPath);
                for (File file : files) {
                    if (file.isFile()) {
                        System.out.println(file.getName());
                    }
                }
            } else {
                System.out.println("Could not list files in folder: " + folderPath);
            }
        } else {
            System.out.println("Folder does not exist or is not a directory: " + folderPath);
        }
    }
}