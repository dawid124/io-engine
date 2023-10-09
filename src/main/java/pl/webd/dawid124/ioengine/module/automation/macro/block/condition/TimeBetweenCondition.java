package pl.webd.dawid124.ioengine.module.automation.macro.block.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.automation.macro.fetcher.IVariableFetcher;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;
import pl.webd.dawid124.ioengine.module.state.model.variable.TimeVariable;
import pl.webd.dawid124.ioengine.utils.TimeUtils;

import java.time.LocalTime;
import java.util.Map;

public class TimeBetweenCondition implements ICondition {

    private static final Logger LOG = LogManager.getLogger(TimeBetweenCondition.class);

    private final IVariableFetcher fetcher;
    private final IVariable fromTime;
    private final IVariable toTime;


    public TimeBetweenCondition(IVariableFetcher fetcher, IVariable fromTime, IVariable toTime) {
        this.fetcher = fetcher;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    @Override
    public boolean test(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        IVariable variable = fetcher.fetch(context, variables, zoneId);

        if (!(variable instanceof TimeVariable)
                || !(fromTime instanceof TimeVariable)
                || !(toTime instanceof TimeVariable)) {
            throw new RuntimeException("Time between use only time variable");
        }

        LocalTime currentLocalTime = ((TimeVariable) variable).getValue();
        LocalTime fromLocalTime = ((TimeVariable) fromTime).getValue();
        LocalTime toLocalTime = ((TimeVariable) toTime).getValue();

        return TimeUtils.isInRange(currentLocalTime, fromLocalTime, toLocalTime);
    }

    @Override
    public EConditionType getType() {
        return EConditionType.TIME_BETWEEN;
    }

    public IVariableFetcher getFetcher() {
        return fetcher;
    }

    public IVariable getFromTime() {
        return fromTime;
    }

    public IVariable getToTime() {
        return toTime;
    }
}
