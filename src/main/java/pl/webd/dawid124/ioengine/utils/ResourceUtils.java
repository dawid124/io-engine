package pl.webd.dawid124.ioengine.utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceUtils {

    public static String getResourceFileAsString(String fileName) throws IOException {
        File file = org.springframework.util.ResourceUtils.getFile(fileName);
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }

    public static String getResourceYamlAsJson(String fileName) throws IOException {
        File file = org.springframework.util.ResourceUtils.getFile(fileName);
        String yaml = new String(Files.readAllBytes(Paths.get(file.toURI())));
        return convertYamlToJson(yaml);
    }

    public static String convertYamlToJson(String yaml) throws IOException {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        Object obj = yamlReader.readValue(yaml, Object.class);

        ObjectMapper jsonWriter = new ObjectMapper();
        return jsonWriter.writeValueAsString(obj);
    }
}
