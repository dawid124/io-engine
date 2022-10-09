package pl.webd.dawid124.ioengine.module.automation.macro.fetcher;

import org.springframework.stereotype.Component;
import pl.webd.dawid124.ioengine.module.state.model.device.MotionSensorState;
import pl.webd.dawid124.ioengine.module.state.model.variable.BooleanVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.StringVariable;
import pl.webd.dawid124.ioengine.module.state.service.StateService;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class SensorInactiveForTimeVariableFetcher implements IVariableFetcher {

    private StateService stateService;

    private String sensorId;
    private int second;


    public SensorInactiveForTimeVariableFetcher() {}

    @Override
    public EVariableFetcherType getFetcherType() {
        return EVariableFetcherType.SENSOR_INACTIVE_FOR_TIME;
    }

    @Override
    public BooleanVariable fetch(Map<String, IVariable> variables, String zoneId) {
        MotionSensorState sensorState = (MotionSensorState) stateService.getSensors().get(sensorId);
        LocalDateTime lastActiveDate = sensorState.getLastActiveDate();
        LocalDateTime lastInactiveDate = sensorState.getLastInactiveDate();
        if (lastActiveDate == null || lastInactiveDate == null) {
            return BooleanVariable.FALE;
        } else if (lastActiveDate.isAfter(lastInactiveDate)) {
            return BooleanVariable.FALE;
        } else if (LocalDateTime.now().isAfter(lastInactiveDate.plusSeconds(second))) {
            return BooleanVariable.TRUE;
        }

        return BooleanVariable.FALE;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
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
