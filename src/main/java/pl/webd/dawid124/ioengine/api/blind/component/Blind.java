package pl.webd.dawid124.ioengine.api.blind.component;

import com.pi4j.io.gpio.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.webd.dawid124.ioengine.model.BlindResponse;
import pl.webd.dawid124.ioengine.model.EBlindDirection;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Blind {

    private static final Logger LOG = LogManager.getLogger( Blind.class );

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static final int FULL_CHANGE_TIME = 1300;
    public static final int FULL_DIMMER_TIME = 60 * 1000;
    public static final int MAX_PERCENT = 100;
    public static final int MIN_PERCENT = 0;

    private final GpioPinDigitalOutput up;
    private final GpioPinDigitalOutput down;

    private EBlindDirection state;
    private int dimmerPercent;
    private boolean lock;

    public Blind(String name, Pin up, Pin down) {
        GpioController GPIO = GpioFactory.getInstance();
        this.up = GPIO.provisionDigitalOutputPin(up, "blind-" + name + "-up", PinState.HIGH);
        this.down = GPIO.provisionDigitalOutputPin(down, "blind-" + name + "-down", PinState.HIGH);
        this.dimmerPercent = 0;

        move(EBlindDirection.UP);
    }

    public BlindResponse move(EBlindDirection direction) {
        if (lock) {
            return new BlindResponse(this.state, this.dimmerPercent);
        }

        GpioPinDigitalOutput directionPin = setRelayOnSafe(direction);
        if (directionPin == null) {
            return new BlindResponse(this.state, this.dimmerPercent);
        }

        scheduler.schedule(() -> {
            directionPin.setState(PinState.HIGH);
            lock = false;
        }, FULL_DIMMER_TIME, TimeUnit.MILLISECONDS);

        this.dimmerPercent = 0;

        return new BlindResponse(this.state, this.dimmerPercent);
    }

    public BlindResponse dim(EBlindDirection direction, int percent) {
        if (lock || EBlindDirection.UP.equals(state)) {
            return new BlindResponse(this.state, this.dimmerPercent);
        }

        if (EBlindDirection.UP.equals(direction) && this.dimmerPercent >= MAX_PERCENT) {
            return new BlindResponse(this.state, this.dimmerPercent);
        } else if (EBlindDirection.DOWN.equals(direction) && this.dimmerPercent <= MIN_PERCENT) {
            return new BlindResponse(this.state, this.dimmerPercent);
        }

        GpioPinDigitalOutput directionPin = setRelayOnSafe(direction);
        if (directionPin == null) {
            return new BlindResponse(this.state, this.dimmerPercent);
        }

        this.state = EBlindDirection.DIMMER;
        int time;

        if (EBlindDirection.UP.equals(direction)) {
            int newPercent = Math.min(this.dimmerPercent + percent, MAX_PERCENT);
            time = Math.round(FULL_CHANGE_TIME / 100 * (newPercent - this.dimmerPercent));
            this.dimmerPercent = newPercent;
        } else {
            int newPercent = Math.max(this.dimmerPercent - percent, MIN_PERCENT);
            time = Math.round(FULL_CHANGE_TIME / 100 * (this.dimmerPercent - newPercent));
            this.dimmerPercent = newPercent;
        }

        scheduler.schedule(() -> {
            directionPin.setState(PinState.HIGH);
            lock = false;
        }, time, TimeUnit.MILLISECONDS);

        return new BlindResponse(this.state, this.dimmerPercent);
    }


    private GpioPinDigitalOutput setRelayOnSafe(EBlindDirection direction) {
        GpioPinDigitalOutput directionPin;
        GpioPinDigitalOutput reversePin;
        if (EBlindDirection.UP.equals(direction)) {
            directionPin = up;
            reversePin = down;
        } else {
            directionPin = down;
            reversePin = up;
        }

        PinState reverseState = reversePin.getState();


        if (PinState.LOW.equals(reverseState)) {
            LOG.warn("Stop relay on!! pinToChange: [%], valueToChange: [%], reversePin: [], reverePinVal: [%]",
                    directionPin.getName(), PinState.LOW.getName(), reversePin.getName(), reversePin, reverseState.getName());
            return null;
        }

        lock = true;
        state = direction;
        directionPin.setState(PinState.LOW);
        return directionPin;
    }

    public EBlindDirection getState() {
        return state;
    }

    public int getDimmerPercent() {
        return dimmerPercent;
    }
}
