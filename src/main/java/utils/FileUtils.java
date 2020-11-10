package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    private static String getFile(String scrFilename, File directory) {
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory + scrFilename + ".png";
    }

    private static void writeFileToPath(byte[] byteArray, Path path) {
        try {
            Files.write(path, byteArray);
        } catch (IOException ioe) {
            System.out.println("Exception on copying screenshot file! "+ioe+"");
        }
    }

    private static String getScreenShotsDir(){
        Path currentRelativePath = Paths.get("src/main/resources/screenshots");
        return currentRelativePath.toAbsolutePath().toString();
    }

    public static void writeFile(String scrFilename, byte[] byteArray) {
        File directory = new File(getScreenShotsDir());
        String filePath = getFile(scrFilename, directory);
        Path path = Paths.get(filePath);
        writeFileToPath(byteArray, path);
    }
}
