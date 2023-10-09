package pl.webd.dawid124.ioengine.module.zigbee;

public class SonoffBtnMsg extends ZigbeeMessage {
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
