package pl.webd.dawid124.ioengine.model;

import pl.webd.dawid124.ioengine.home.state.device.EBlindDirection;

import java.io.Serializable;

public class BlindResponse implements Serializable {

    private EBlindDirection state;
    private int dimmerPercent;

    public BlindResponse(EBlindDirection state, int dimmerPercent) {
        this.state = state;
        this.dimmerPercent = dimmerPercent;
    }

    public EBlindDirection getState() {
        return state;
    }

    public void setState(EBlindDirection state) {
        this.state = state;
    }

    public int getDimmerPercent() {
        return dimmerPercent;
    }

    public void setDimmerPercent(int dimmerPercent) {
        this.dimmerPercent = dimmerPercent;
    }
}
