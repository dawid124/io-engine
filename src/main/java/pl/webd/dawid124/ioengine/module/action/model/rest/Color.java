package pl.webd.dawid124.ioengine.module.action.model.rest;

import java.io.Serializable;

public class Color implements Serializable {

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
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        if (r != color.r) return false;
        if (g != color.g) return false;
        if (b != color.b) return false;
        if (w != color.w) return false;
        return ww == color.ww;
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

    public void update(Color color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.w = color.w;
        this.ww = color.w;
    }
}
