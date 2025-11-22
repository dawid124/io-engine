package pl.webd.dawid124.ioengine.module.monitoring.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.webd.dawid124.ioengine.module.monitoring.model.MonitoringEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MonitoringRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_EVENT =
        "INSERT INTO monitoring_events (id, device_id, child_name, date, timestamp, type, app, app_name, sender, receiver, content, conversation_id) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_DEVICE_DATE =
        "SELECT * FROM monitoring_events WHERE device_id = ? AND date = ? ORDER BY timestamp ASC";

    private static final String SELECT_BY_RANGE =
        "SELECT * FROM monitoring_events WHERE device_id = ? AND date >= ? AND date <= ? ORDER BY timestamp DESC";

    private static final String COUNT_BY_DEVICE_DATE =
        "SELECT COUNT(*) FROM monitoring_events WHERE device_id = ? AND date = ?";

    public MonitoringRepository(@Qualifier("monitoringJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Batch insert events with metadata (optimized for performance)
     */
    @Transactional("monitoringTransactionManager")
    public void batchInsert(String deviceId, String childName, String date, List<MonitoringEvent> events) {
        if (events.isEmpty()) return;

        jdbcTemplate.batchUpdate(INSERT_EVENT, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                MonitoringEvent event = events.get(i);
                ps.setString(1, event.getId());
                ps.setString(2, deviceId);
                ps.setString(3, childName);
                ps.setString(4, date);
                ps.setLong(5, event.getTimestamp());
                ps.setString(6, event.getType());
                ps.setString(7, event.getApp());
                ps.setString(8, event.getAppName());
                ps.setString(9, event.getSender());
                ps.setString(10, event.getReceiver());
                ps.setString(11, event.getContent());
                ps.setString(12, event.getConversationId());
            }

            @Override
            public int getBatchSize() {
                return events.size();
            }
        });
    }

    /**
     * Get all events for a specific device and date
     */
    public List<MonitoringEvent> findByDeviceAndDate(String deviceId, String date) {
        return jdbcTemplate.query(SELECT_BY_DEVICE_DATE, eventRowMapper(), deviceId, date);
    }

    /**
     * Get events in date range
     */
    public List<MonitoringEvent> findByDeviceAndDateRange(String deviceId, String dateFrom, String dateTo) {
        return jdbcTemplate.query(SELECT_BY_RANGE, eventRowMapper(), deviceId, dateFrom, dateTo);
    }

    /**
     * Count events for a device and date
     */
    public int countByDeviceAndDate(String deviceId, String date) {
        Integer count = jdbcTemplate.queryForObject(COUNT_BY_DEVICE_DATE, Integer.class, deviceId, date);
        return count != null ? count : 0;
    }

    /**
     * Search with filters
     * Note: deviceId, dateFrom, and dateTo are now optional
     */
    public List<MonitoringEvent> search(String deviceId, String dateFrom, String dateTo,
                                       String searchText, String sender, String app, String type,
                                       int limit, int offset) {
        StringBuilder sql = new StringBuilder("SELECT * FROM monitoring_events WHERE 1=1");

        // Build parameter list
        java.util.List<Object> params = new java.util.ArrayList<>();

        // Optional filters
        if (deviceId != null && !deviceId.isEmpty()) {
            sql.append(" AND device_id = ?");
            params.add(deviceId);
        }
        if (dateFrom != null && !dateFrom.isEmpty()) {
            sql.append(" AND date >= ?");
            params.add(dateFrom);
        }
        if (dateTo != null && !dateTo.isEmpty()) {
            sql.append(" AND date <= ?");
            params.add(dateTo);
        }
        if (searchText != null && !searchText.isEmpty()) {
            sql.append(" AND (content LIKE ? OR sender LIKE ? OR receiver LIKE ?)");
            String pattern = "%" + searchText + "%";
            params.add(pattern);
            params.add(pattern);
            params.add(pattern);
        }
        if (sender != null && !sender.isEmpty()) {
            sql.append(" AND sender LIKE ?");
            params.add("%" + sender + "%");
        }
        if (app != null && !app.isEmpty()) {
            sql.append(" AND app = ?");
            params.add(app);
        }
        if (type != null && !type.isEmpty()) {
            sql.append(" AND type = ?");
            params.add(type);
        }

        sql.append(" ORDER BY timestamp DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        return jdbcTemplate.query(sql.toString(), eventRowMapper(), params.toArray());
    }

    /**
     * Count search results
     * Note: deviceId, dateFrom, and dateTo are now optional
     */
    public int countSearch(String deviceId, String dateFrom, String dateTo,
                          String searchText, String sender, String app, String type) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM monitoring_events WHERE 1=1");

        // Build parameter list
        java.util.List<Object> params = new java.util.ArrayList<>();

        // Optional filters
        if (deviceId != null && !deviceId.isEmpty()) {
            sql.append(" AND device_id = ?");
            params.add(deviceId);
        }
        if (dateFrom != null && !dateFrom.isEmpty()) {
            sql.append(" AND date >= ?");
            params.add(dateFrom);
        }
        if (dateTo != null && !dateTo.isEmpty()) {
            sql.append(" AND date <= ?");
            params.add(dateTo);
        }
        if (searchText != null && !searchText.isEmpty()) {
            sql.append(" AND (content LIKE ? OR sender LIKE ? OR receiver LIKE ?)");
            String pattern = "%" + searchText + "%";
            params.add(pattern);
            params.add(pattern);
            params.add(pattern);
        }
        if (sender != null && !sender.isEmpty()) {
            sql.append(" AND sender LIKE ?");
            params.add("%" + sender + "%");
        }
        if (app != null && !app.isEmpty()) {
            sql.append(" AND app = ?");
            params.add(app);
        }
        if (type != null && !type.isEmpty()) {
            sql.append(" AND type = ?");
            params.add(type);
        }

        Integer count = jdbcTemplate.queryForObject(sql.toString(), Integer.class, params.toArray());
        return count != null ? count : 0;
    }

    private RowMapper<MonitoringEvent> eventRowMapper() {
        return (rs, rowNum) -> {
            MonitoringEvent event = new MonitoringEvent();
            event.setId(rs.getString("id"));
            event.setDeviceId(rs.getString("device_id"));
            event.setTimestamp(rs.getLong("timestamp"));
            event.setType(rs.getString("type"));
            event.setApp(rs.getString("app"));
            event.setAppName(rs.getString("app_name"));
            event.setSender(rs.getString("sender"));
            event.setReceiver(rs.getString("receiver"));
            event.setContent(rs.getString("content"));
            event.setConversationId(rs.getString("conversation_id"));
            return event;
        };
    }

}
