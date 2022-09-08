package pl.webd.dawid124.ioengine.module.club.fft;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BitValue {
    private List<Double> values;

    public BitValue() {
        this.values = new ArrayList<>();
    }

    public synchronized int readAverage() {
        if (values.size() == 0) {
            return 0;
        }
        double val = values.stream().mapToDouble(a -> a).average().getAsDouble();
        values = new ArrayList<>();
        return (int) val;
    }

    public synchronized int readMax() {
        if (values.size() == 0) {
            return 0;
        }
        double val = values.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
        values = new ArrayList<>();
        return (int) val;
    }


    public synchronized void addValue(double val) {
        values.add(val);
    }
}
