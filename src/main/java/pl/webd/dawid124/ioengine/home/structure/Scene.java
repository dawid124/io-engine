package pl.webd.dawid124.ioengine.home.structure;

public class Scene {

    private final String id;
    private final String name;
    private final int order;

    public Scene(String id, String name, int order) {
        this.id = id;
        this.name = name;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }
}
