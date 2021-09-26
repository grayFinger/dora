package com.dora.common.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Base64ImageUtils {
    public Base64ImageUtils() {
    }

    public static void generateImage(String imgStr, String path, String fileName) throws Exception {
        byte[] decodedImg = Base64.decode(imgStr);
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }

        Path destinationFile = Paths.get(path, fileName);
        Files.write(destinationFile, decodedImg, new OpenOption[0]);
    }
}
