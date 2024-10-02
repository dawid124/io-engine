package pl.webd.dawid124.ioengine.module.action.model.server;

public class LedChangeData implements ILedChangeData {

    public static final int DEFAULT_STEP_TIME = 30;
    public static final int DEFAULT_TIME = 1000;
    private final int time;

    private final int stepTime;

    public LedChangeData() {
        this.time = DEFAULT_TIME;
        this.stepTime = DEFAULT_STEP_TIME;
    }

    public LedChangeData(int time, int stepTime) {
        this.time = time;
        this.stepTime = stepTime;
    }

    public int getTime() {
        return time;
    }

    public int getStepTime() {
        return stepTime;
    }
}
