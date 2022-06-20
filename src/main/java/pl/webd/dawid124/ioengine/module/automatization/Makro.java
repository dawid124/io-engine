package pl.webd.dawid124.ioengine.module.automatization;

import pl.webd.dawid124.ioengine.module.automatization.block.IBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Makro {

    private String zoneId;

    private String id;
    private String name;
    private String description;

    private List<IBlock> blocks;

    public Makro() {
        blocks = new ArrayList<>();
    }

    public void run(Map <String, Object> variables) {
        blocks.forEach(b -> b.run(variables, zoneId));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<IBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<IBlock> blocks) {
        this.blocks = blocks;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}