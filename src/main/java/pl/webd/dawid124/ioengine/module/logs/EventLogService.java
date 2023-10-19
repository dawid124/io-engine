package pl.webd.dawid124.ioengine.module.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.webd.dawid124.ioengine.database.DBEventLog;
import pl.webd.dawid124.ioengine.module.logs.model.EventLogResponse;
import pl.webd.dawid124.ioengine.module.logs.model.LogSearch;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;

@Service
public class EventLogService {

    private static final Logger LOG = LogManager.getLogger(EventLogService.class );

    private final EventLogRepository repository;

    public EventLogService(EventLogRepository repository) {
        this.repository = repository;
    }

    public void insertLog(DeviceState state, String msg) {
        try {
            repository.insert(new DBEventLog(state, msg));
        } catch (Exception ex) {
            LOG.error("Error on inserting DB event log", ex);
        }
    }

    public EventLogResponse getLogs(LogSearch search) {
        return new EventLogResponse(repository.find(search));
    }
}
