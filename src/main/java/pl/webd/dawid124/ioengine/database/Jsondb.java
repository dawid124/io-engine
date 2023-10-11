package pl.webd.dawid124.ioengine.database;

import io.jsondb.JsonDBTemplate;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.config.settings.DBSettings;

import javax.annotation.PostConstruct;

@Service
public class Jsondb {

    private final DBSettings settings;
    private JsonDBTemplate jsonDBTemplate;

    public Jsondb(DBSettings settings) {
        this.settings = settings;

        String dbFilesLocation = settings.getPath();
        String baseScanPackage = "pl.webd.dawid124.ioengine";

        jsonDBTemplate = new JsonDBTemplate(dbFilesLocation, baseScanPackage);
    }

    public JsonDBTemplate instance() {
        return jsonDBTemplate;
    }
}
