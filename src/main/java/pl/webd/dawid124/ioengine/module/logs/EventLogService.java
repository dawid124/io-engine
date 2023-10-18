package pl.webd.dawid124.ioengine.module.logs;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import io.jsondb.JsonDBTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.webd.dawid124.ioengine.database.Jsondb;
import pl.webd.dawid124.ioengine.module.automation.trigger.SensorTriggerMsg;
import pl.webd.dawid124.ioengine.module.device.model.output.BlindDevice;
import pl.webd.dawid124.ioengine.module.logs.model.EventLog;
import pl.webd.dawid124.ioengine.module.logs.model.EventLogResponse;
import pl.webd.dawid124.ioengine.module.logs.model.LogSearch;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventLogService {

    private static final Logger LOG = LogManager.getLogger(EventLogService.class );

    private final Gson gson = new Gson();
    private final JsonDBTemplate db;

    public EventLogService(Jsondb db) {
        this.db = db.instance();
    }

    @PostConstruct
    public void init() {
        if (!db.collectionExists(EventLog.class)) {
            db.createCollection(EventLog.class);
        }
    }

    public void insertLog(DeviceState state, String msg) {
        try {
            db.insert(new EventLog(state, (LinkedTreeMap<String, Object>) gson.fromJson(msg, Map.class)));
        } catch (Exception ex) {
            LOG.error("Error on inserting DB event log", ex);
        }
    }

    public EventLogResponse getLogs(LogSearch search) {
        String xpath = "/.[";

        if (StringUtils.hasText(search.getIoId())) xpath += "id='" + search.getIoId() + "' and ";
        if (search.getIoType() != null) xpath += "ioType='" + search.getIoType() + "' and ";
        if (search.getDateFrom() > 0) xpath += "time>=" + search.getDateFrom()  + " and ";
        if (search.getDateTo() > 0) xpath += "time<=" + search.getDateTo()  + " and ";


        xpath += "]";

        if (xpath.contains("and ]")) xpath = xpath.replace("and ]", "]");

        return new EventLogResponse(db.find(xpath, EventLog.class));
    }
}
