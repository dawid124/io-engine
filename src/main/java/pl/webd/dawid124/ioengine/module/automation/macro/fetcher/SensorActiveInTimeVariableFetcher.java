package pl.webd.dawid124.ioengine.module.automation.macro.fetcher;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.device.MotionSensorState;
import pl.webd.dawid124.ioengine.module.state.model.variable.BooleanVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.time.LocalDateTime;
import java.util.Map;

public class SensorActiveInTimeVariableFetcher implements IVariableFetcher {

    private String sensorId;
    private int second;


    public SensorActiveInTimeVariableFetcher() {}

    @Override
    public EVariableFetcherType getFetcherType() {
        return EVariableFetcherType.SENSOR_ACTIVE_IN_TIME;
    }


    @Override
    public BooleanVariable fetch(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        MotionSensorState sensorState = (MotionSensorState) context.getStateService().getSensors().get(sensorId);
        LocalDateTime lastActiveDate = sensorState.getLastActiveDate();
        LocalDateTime lastInactiveDate = sensorState.getLastInactiveDate();
        if (lastActiveDate == null || lastInactiveDate == null) {
            return BooleanVariable.FALE;
        } else if (lastActiveDate.isAfter(lastInactiveDate)) {
            return BooleanVariable.TRUE;
        } else if (lastActiveDate.isAfter(LocalDateTime.now().minusSeconds(second))) {
            return BooleanVariable.TRUE;
        }

        return BooleanVariable.FALE;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
