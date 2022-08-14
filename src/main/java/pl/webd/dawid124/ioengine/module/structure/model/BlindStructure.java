package pl.webd.dawid124.ioengine.module.structure.model;

public class BlindStructure {

    private String ioId;
    private String shortName;

    public BlindStructure() {}

    public BlindStructure(String ioId) {
        this.ioId = ioId;
    }

    public String getIoId() {
        return ioId;
    }

    public void setIoId(String ioId) {
        this.ioId = ioId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
