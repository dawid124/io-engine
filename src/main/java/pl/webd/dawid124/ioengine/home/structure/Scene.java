package pl.webd.dawid124.ioengine.home.structure;

public class Scene {

    private final String id;
    private final String name;

    public Scene(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
