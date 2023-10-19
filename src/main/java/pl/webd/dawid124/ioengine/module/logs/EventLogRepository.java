package pl.webd.dawid124.ioengine.module.logs;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.webd.dawid124.ioengine.database.DBEventLog;
import pl.webd.dawid124.ioengine.module.logs.model.EventLog;
import pl.webd.dawid124.ioengine.module.logs.model.LogSearch;

import java.util.List;
import java.util.Map;

@Service
public class EventLogRepository {
    private final Gson gson = new Gson();
    public static final String INSERT = "INSERT INTO EventLog(ioId, time, ioType, msg) VALUES (?, ?, ?, ?)";
    private final JdbcTemplate jdbcTemplate;


    public EventLogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(DBEventLog eventLog) {
        jdbcTemplate.update(INSERT, eventLog.getIoId(), eventLog.getTime(), eventLog.getIoType(), eventLog.getMsg());
    }

    public List<EventLog> find(LogSearch search) {
        String select = "SELECT id, ioId, time, ioType, msg FROM EventLog WHERE ";

        if (StringUtils.hasText(search.getIoId())) select += "ioId='" + search.getIoId() + "' and ";
        if (search.getIoType() != null) select += "ioType='" + search.getIoType() + "' and ";
        if (search.getDateFrom() > 0) select += "time>=" + search.getDateFrom()  + " and ";
        if (search.getDateTo() > 0) select += "time<=" + search.getDateTo()  + " and ";

        select += " 1 = 1 ORDER BY TIME DESC ";

        return jdbcTemplate.query(select,
                (resultSet, rowNum) -> new EventLog(
                        resultSet.getString("ioId"),
                        resultSet.getLong("time"),
                        resultSet.getString("ioType"),
                        (LinkedTreeMap<String, Object>) gson.fromJson(resultSet.getString("msg"), Map.class)));

    }
}
