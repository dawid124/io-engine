package pl.webd.dawid124.ioengine.utils;

import java.time.LocalTime;

public class TimeUtils {

    public static boolean isInRange(LocalTime time, LocalTime f, LocalTime t) {
        int hour, from, to;
        hour = 60 * time.getHour() + time.getMinute();
        from = 60 * f.getHour() + f.getMinute();
        to = 60 * t.getHour() + t.getMinute();

        if (from > to) {
            return hour >= from || hour <= to;
        } else {
            return hour >= from && hour < to;
        }
    }

    public static boolean isInRange(LocalTime f, LocalTime t) {
        return isInRange(LocalTime.now(), f, t);
    }

}
