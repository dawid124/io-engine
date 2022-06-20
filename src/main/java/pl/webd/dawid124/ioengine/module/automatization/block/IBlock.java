package pl.webd.dawid124.ioengine.module.automatization.block;

import java.util.Map;

public interface IBlock {

    String getName();

    Integer getOrder();

    EBlockType getBlockType();

    void run(Map<String, Object> variables, String zoneId);
}
