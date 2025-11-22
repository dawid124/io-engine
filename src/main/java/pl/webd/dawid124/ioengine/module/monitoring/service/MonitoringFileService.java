package pl.webd.dawid124.ioengine.module.monitoring.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.config.settings.DBSettings;
import pl.webd.dawid124.ioengine.module.monitoring.model.ChildMonitoringDay;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MonitoringFileService {

    private static final String MONITORING_DIR = "monitoring";
    private final Gson gson;
    private final String basePath;

    public MonitoringFileService(DBSettings dbSettings) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.basePath = dbSettings.getPath() + File.separator + MONITORING_DIR;
        ensureDirectoryExists();
    }

    private void ensureDirectoryExists() {
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void save(ChildMonitoringDay day) throws IOException {
        String fileName = getFileName(day.getDeviceId(), day.getDate());
        File file = new File(basePath, fileName);

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(day, writer);
        }
    }

    public ChildMonitoringDay load(String deviceId, String date) throws IOException {
        String fileName = getFileName(deviceId, date);
        File file = new File(basePath, fileName);

        if (!file.exists()) {
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, ChildMonitoringDay.class);
        }
    }

    public List<ChildMonitoringDay> loadRange(String deviceId, String dateFrom, String dateTo) throws IOException {
        List<ChildMonitoringDay> days = new ArrayList<>();
        File dir = new File(basePath);

        if (!dir.exists()) {
            return days;
        }

        // List all files matching the pattern
        File[] files = dir.listFiles((d, name) ->
            name.startsWith(deviceId + "_") && name.endsWith(".json")
        );

        if (files == null) {
            return days;
        }

        for (File file : files) {
            try (FileReader reader = new FileReader(file)) {
                ChildMonitoringDay day = gson.fromJson(reader, ChildMonitoringDay.class);

                // Filter by date range
                if (day.getDate() != null &&
                    day.getDate().compareTo(dateFrom) >= 0 &&
                    day.getDate().compareTo(dateTo) <= 0) {
                    days.add(day);
                }
            } catch (Exception e) {
                // Skip corrupted files
                e.printStackTrace();
            }
        }

        return days;
    }

    public List<String> listAllFiles() {
        File dir = new File(basePath);
        if (!dir.exists()) {
            return new ArrayList<>();
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (files == null) {
            return new ArrayList<>();
        }

        List<String> fileNames = new ArrayList<>();
        for (File file : files) {
            fileNames.add(file.getName());
        }
        return fileNames;
    }

    private String getFileName(String deviceId, String date) {
        return deviceId + "_" + date + ".json";
    }
}
