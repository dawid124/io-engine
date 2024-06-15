package pl.webd.dawid124.ioengine.module.club.model;

import pl.webd.dawid124.ioengine.module.action.model.rest.Color;
import pl.webd.dawid124.ioengine.module.action.model.rest.IColor;

public class SoundLightDefinition {

    private boolean active;

    private String ioId;
    private String name;

    private EToneType toneType;
    private EEffectType effect;
    private int toneIndex;
    private double scale;

    private IColor color;

    private int threshold;
    private int minBrightness;
    private int initialBrightness;

    private int staticLeds;
    private int animationId;
    private int subAnimationId;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getIoId() {
        return ioId;
    }

    public void setIoId(String ioId) {
        this.ioId = ioId;
    }

    public EToneType getToneType() {
        return toneType;
    }

    public void setToneType(EToneType toneType) {
        this.toneType = toneType;
    }

    public int getToneIndex() {
        return toneIndex;
    }

    public void setToneIndex(int toneIndex) {
        this.toneIndex = toneIndex;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public IColor getColor() {
        return color;
    }

    public void setColor(IColor color) {
        this.color = color;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getMinBrightness() {
        return minBrightness;
    }

    public void setMinBrightness(int minBrightness) {
        this.minBrightness = minBrightness;
    }

    public int getInitialBrightness() {
        return initialBrightness;
    }

    public void setInitialBrightness(int initialBrightness) {
        this.initialBrightness = initialBrightness;
    }

    public int getStaticLeds() {
        return staticLeds;
    }

    public void setStaticLeds(int staticLeds) {
        this.staticLeds = staticLeds;
    }

    public int getAnimationId() {
        return animationId;
    }

    public void setAnimationId(int animationId) {
        this.animationId = animationId;
    }

    public int getSubAnimationId() {
        return subAnimationId;
    }

    public void setSubAnimationId(int subAnimationId) {
        this.subAnimationId = subAnimationId;
    }

    public EEffectType getEffect() {
        return effect;
    }

    public void setEffect(EEffectType effect) {
        this.effect = effect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SoundLightDefinition that = (SoundLightDefinition) o;

        if (active != that.active) return false;
        if (toneIndex != that.toneIndex) return false;
        if (Double.compare(that.scale, scale) != 0) return false;
        if (threshold != that.threshold) return false;
        if (minBrightness != that.minBrightness) return false;
        if (initialBrightness != that.initialBrightness) return false;
        if (staticLeds != that.staticLeds) return false;
        if (animationId != that.animationId) return false;
        if (subAnimationId != that.subAnimationId) return false;
        if (!ioId.equals(that.ioId)) return false;
        if (toneType != that.toneType) return false;
        if (effect != that.effect) return false;
        return color.equals(that.color);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (active ? 1 : 0);
        result = 31 * result + ioId.hashCode();
        result = 31 * result + toneType.hashCode();
        result = 31 * result + effect.hashCode();
        result = 31 * result + toneIndex;
        temp = Double.doubleToLongBits(scale);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + color.hashCode();
        result = 31 * result + threshold;
        result = 31 * result + minBrightness;
        result = 31 * result + initialBrightness;
        result = 31 * result + staticLeds;
        result = 31 * result + animationId;
        result = 31 * result + subAnimationId;
        return result;
    }

    public void update(SoundLightDefinition newDefinition) {
        this.active = newDefinition.isActive();
        this.ioId = newDefinition.getIoId();
        this.toneType = newDefinition.getToneType();
        this.effect = newDefinition.getEffect();
        this.toneIndex = newDefinition.getToneIndex();
        this.scale = newDefinition.getScale();
        this.color = newDefinition.getColor();
        this.threshold = newDefinition.getThreshold();
        this.minBrightness = newDefinition.getMinBrightness();
        this.initialBrightness = newDefinition.getInitialBrightness();
        this.staticLeds = newDefinition.getStaticLeds();
        this.animationId = newDefinition.getAnimationId();
        this.subAnimationId = newDefinition.getSubAnimationId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

