package pl.webd.dawid124.ioengine.module.automation.macro.block.runner;

import pl.webd.dawid124.ioengine.module.automation.AutomationContext;
import pl.webd.dawid124.ioengine.module.state.model.variable.IVariable;

import java.util.Map;

public class ModbusTcpActionRunnerBlock extends RunnerBlock {

    private final int slaveId;
    private final int offset;
    private final int value;

    public ModbusTcpActionRunnerBlock(int slaveId, int offset, int value) {
        this.slaveId = slaveId;
        this.offset = offset;
        this.value = value;
    }

    @Override public ERunnerBlockType getRunnerType() {
        return ERunnerBlockType.MODBUS_TCP_ACTION;
    }

    @Override
    public void run(AutomationContext context, Map<String, IVariable> variables, String zoneId) {
        context.getModbusTcpService().writeRegister(slaveId, offset, value);
    }

    public int getSlaveId() {
        return slaveId;
    }

    public int getOffset() {
        return offset;
    }

    public int getValue() {
        return value;
    }
}
