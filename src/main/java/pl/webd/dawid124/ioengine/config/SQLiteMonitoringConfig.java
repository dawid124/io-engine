package pl.webd.dawid124.ioengine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import pl.webd.dawid124.ioengine.config.settings.DBSettings;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQLite configuration optimized for Raspberry Pi
 * - WAL mode for better concurrency
 * - NORMAL synchronous for faster writes
 * - Large cache for better performance
 */
@Configuration
public class SQLiteMonitoringConfig {

    private final DBSettings dbSettings;

    public SQLiteMonitoringConfig(DBSettings dbSettings) {
        this.dbSettings = dbSettings;
    }

    @Bean(name = "monitoringDataSource")
    public DataSource monitoringDataSource() throws SQLException {
        String dbPath = dbSettings.getPath() + File.separator + "monitoring.db";
        String url = "jdbc:sqlite:" + dbPath;

        org.sqlite.SQLiteDataSource dataSource = new org.sqlite.SQLiteDataSource();
        dataSource.setUrl(url);

        // Apply SQLite optimizations
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            // Write-Ahead Logging for better concurrency (CRITICAL!)
            stmt.execute("PRAGMA journal_mode=WAL;");

            // NORMAL synchronous = faster writes, still safe
            stmt.execute("PRAGMA synchronous=NORMAL;");

            // Larger cache = better performance (10MB)
            stmt.execute("PRAGMA cache_size=-10000;");

            // Temp store in memory
            stmt.execute("PRAGMA temp_store=MEMORY;");

            // Memory-mapped I/O (faster reads)
            stmt.execute("PRAGMA mmap_size=30000000000;");

            // Busy timeout for concurrent access
            stmt.execute("PRAGMA busy_timeout=5000;");

            System.out.println("SQLite configured with optimizations for monitoring at: " + dbPath);
        }

        return dataSource;
    }

    @Bean(name = "monitoringJdbcTemplate")
    public JdbcTemplate monitoringJdbcTemplate() throws SQLException {
        return new JdbcTemplate(monitoringDataSource());
    }

    @Bean(name = "monitoringTransactionManager")
    public PlatformTransactionManager monitoringTransactionManager() throws SQLException {
        return new DataSourceTransactionManager(monitoringDataSource());
    }
}
