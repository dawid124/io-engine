package pl.webd.dawid124.ioengine.module.action.model.rest;

public class Color implements IColor {

    private int r = 0;
    private int g = 0;
    private int b = 0;
    private int w = 0;

    private int ww = 0;

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

    public Color(int ww, int w) {
        this.ww = ww;
        this.w = w;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        Color color = (Color) o;

        if (r != color.getR()) return false;
        if (g != color.getG()) return false;
        if (b != color.getB()) return false;
        if (w != color.getW()) return false;
        return ww == color.getWw();
    }

    @Override
    public int hashCode() {
        int result = r;
        result = 31 * result + g;
        result = 31 * result + b;
        result = 31 * result + w;
        result = 31 * result + ww;
        return result;
    }

    public void update(IColor color) {
        this.r = color.getR();
        this.g = color.getG();
        this.b = color.getB();
        this.w = color.getW();
        this.ww = color.getWw();
    }
}
