package pl.webd.dawid124.ioengine.module.share;

import java.io.Serializable;

public class Color implements Serializable {

    private int r = 0;
    private int g = 0;
    private int b = 0;
    private int w = 0;

    private int ww = 0;
    private int cw = 0;

    public Color() {}

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(int r, int g, int b, int w) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.w = w;
    }

    public Color(int ww, int cw) {
        this.ww = ww;
        this.cw = cw;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getWw() {
        return ww;
    }

    public void setWw(int ww) {
        this.ww = ww;
    }

    public int getCw() {
        return cw;
    }

    public void setCw(int cw) {
        this.cw = cw;
    }
}
