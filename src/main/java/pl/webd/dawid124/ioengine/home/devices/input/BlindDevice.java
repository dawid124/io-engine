package pl.webd.dawid124.ioengine.home.devices.input;

import com.pi4j.io.gpio.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.webd.dawid124.ioengine.home.devices.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.home.devices.output.Device;
import pl.webd.dawid124.ioengine.home.devices.output.EDeviceType;
import pl.webd.dawid124.ioengine.home.state.device.BlindDeviceState;
import pl.webd.dawid124.ioengine.home.state.device.DeviceState;
import pl.webd.dawid124.ioengine.model.BlindResponse;
import pl.webd.dawid124.ioengine.home.state.device.EBlindDirection;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BlindDevice extends Device {

    private static final Logger LOG = LogManager.getLogger( BlindDevice.class );

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static final int FULL_CHANGE_TIME = 1300;
    public static final int FULL_DIMMER_TIME = 60 * 1000;
    public static final int MAX_PERCENT = 100;
    public static final int MIN_PERCENT = 0;

    private GpioPinDigitalOutput up;
    private GpioPinDigitalOutput down;

    private BlindDeviceState state;
    private boolean lock;

    public BlindDevice(String id, String name, IDriverConfiguration driverConfiguration, Pin up, Pin down) {
        super(id, name, driverConfiguration);
        try {
//            GpioController GPIO = GpioFactory.getInstance();
//            this.up = GPIO.provisionDigitalOutputPin(up, "blind-" + name + "-up", PinState.HIGH);
//            this.down = GPIO.provisionDigitalOutputPin(down, "blind-" + name + "-down", PinState.HIGH);
//            this.state = new BlindDeviceState(id, name);
//            move(EBlindDirection.UP);
        } catch (Exception ex) {

        }

        this.state = new BlindDeviceState(id);


    }

    public BlindResponse move(EBlindDirection direction) {
        if (lock) {
            return new BlindResponse(state.getPosition(), state.getDimmerPercent());
        }

        GpioPinDigitalOutput directionPin = setRelayOnSafe(direction);
        if (directionPin == null) {
            return new BlindResponse(state.getPosition(), state.getDimmerPercent());
        }

        scheduler.schedule(() -> {
            directionPin.setState(PinState.HIGH);
            lock = false;
        }, FULL_DIMMER_TIME, TimeUnit.MILLISECONDS);

        state.setDimmerPercent(0);

        return new BlindResponse(state.getPosition(), state.getDimmerPercent());
    }

    public BlindResponse dim(EBlindDirection direction, int percent) {
        if (lock) {
            return new BlindResponse(state.getPosition(), state.getDimmerPercent());
        }

        if (EBlindDirection.UP.equals(direction) && state.getDimmerPercent() >= MAX_PERCENT) {
            return new BlindResponse(state.getPosition(), state.getDimmerPercent());
        } else if (EBlindDirection.DOWN.equals(direction) && state.getDimmerPercent() <= MIN_PERCENT) {
            return new BlindResponse(state.getPosition(), state.getDimmerPercent());
        }

        GpioPinDigitalOutput directionPin = setRelayOnSafe(direction);
        if (directionPin == null) {
            return new BlindResponse(state.getPosition(), state.getDimmerPercent());
        }

        this.state.setPosition(EBlindDirection.DIMMER);
        int time;

        if (EBlindDirection.UP.equals(direction)) {
            int newPercent = Math.min(state.getDimmerPercent() + percent, MAX_PERCENT);
            time = Math.round(FULL_CHANGE_TIME / 100 * (newPercent - state.getDimmerPercent()));
            state.setDimmerPercent(newPercent);
        } else {
            int newPercent = Math.max(state.getDimmerPercent() - percent, MIN_PERCENT);
            time = Math.round(FULL_CHANGE_TIME / 100 * (state.getDimmerPercent() - newPercent));
            state.setDimmerPercent(newPercent);
        }

        scheduler.schedule(() -> {
            directionPin.setState(PinState.HIGH);
            lock = false;
        }, time, TimeUnit.MILLISECONDS);

        return new BlindResponse(state.getPosition(), state.getDimmerPercent());
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
        state.setPosition(direction);
        directionPin.setState(PinState.LOW);
        return directionPin;
    }

    @Override public EDeviceType getType() {
        return EDeviceType.BLIND;
    }

    @Override public DeviceState getInitialState() {
        return state;
    }
}
