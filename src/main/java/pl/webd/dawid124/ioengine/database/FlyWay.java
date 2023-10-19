package pl.webd.dawid124.ioengine.database;

import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class FlyWay {

    private final Flyway flyway;


    public FlyWay(Flyway flyway) {
        this.flyway = flyway;
    }

    @PostConstruct
    public void migrateOnStart() {
        flyway.migrate();
    }
}
