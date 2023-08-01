package pl.webd.dawid124.ioengine.module.device.model.output;

import com.pi4j.io.gpio.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.webd.dawid124.ioengine.module.device.model.driver.configuration.IDriverConfiguration;
import pl.webd.dawid124.ioengine.module.state.model.device.BlindDeviceState;
import pl.webd.dawid124.ioengine.module.state.model.device.DeviceState;
import pl.webd.dawid124.ioengine.module.action.model.rest.BlindResponse;
import pl.webd.dawid124.ioengine.module.state.model.device.EBlindDirection;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfig;
import pl.webd.dawid124.ioengine.mqtt.config.IoConfigBlind;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BlindDevice extends Device {

    private static final Logger LOG = LogManager.getLogger( BlindDevice.class );

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static final int FULL_CHANGE_TIME = 1300;
    public static final int FULL_DIMMER_TIME = 60 * 1000;

    private transient GpioPinDigitalOutput up;
    private transient GpioPinDigitalOutput down;

    private int pinUp;
    private int pinDown;

    private transient BlindDeviceState state;

    public BlindDevice(String id, String name, IDriverConfiguration driverConfiguration, int pinUp, int pinDown) {
        super(id, name, driverConfiguration);
        this.pinUp = pinUp;
        this.pinDown = pinDown;

        this.state = new BlindDeviceState(id, name);
    }

    public BlindDevice(String id, String name, IDriverConfiguration driverConfiguration, Pin up, Pin down) {
        super(id, name, driverConfiguration);
        try {
            GpioController GPIO = GpioFactory.getInstance();
            this.up = GPIO.provisionDigitalOutputPin(up, "blind-" + name + "-up", PinState.LOW);
            this.down = GPIO.provisionDigitalOutputPin(down, "blind-" + name + "-down", PinState.LOW);
            this.state = new BlindDeviceState(id, name);
//            moveLocal(EBlindDirection.UP);
        } catch (Exception | Error ex) {

        }

        this.state = new BlindDeviceState(id, name);


    }

    public void moveLocal(EBlindDirection direction, int time) {

        if (EBlindDirection.UP.equals(direction)) {
            up.setState(PinState.HIGH);
            down.setState(PinState.LOW);
        } else {
            down.setState(PinState.HIGH);
            up.setState(PinState.LOW);
        }

        scheduler.schedule(() -> {
            down.setState(PinState.LOW);
            up.setState(PinState.LOW);
        }, time, TimeUnit.MILLISECONDS);
    }

    @Override public EDeviceType getIoType() {
        return EDeviceType.BLIND;
    }

    @Override public DeviceState getInitialState() {
        return state;
    }

    @Override
    public IoConfig toIoConfig() {
        String location = getDriverConfiguration().getConfig().getLocation().toString();
        return new IoConfigBlind(id, getIoType(), location, pinUp, pinDown);
    }

    public int getPinUp() {
        return pinUp;
    }

    public int getPinDown() {
        return pinDown;
    }

    @PreDestroy
    public void destructor() {
        scheduler.shutdown();
    }
}
