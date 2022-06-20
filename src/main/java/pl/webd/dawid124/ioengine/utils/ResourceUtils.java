package pl.webd.dawid124.ioengine.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceUtils {

    public static String getResourceFileAsString(String fileName) throws IOException {
        File file = org.springframework.util.ResourceUtils.getFile(fileName);
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }
}
